package com.gobestsoft.admin.modular.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.CacheConstant;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.common.modular.system.dao.BlackboardDao;
import com.gobestsoft.core.reids.RedisCacheModel;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.dept.model.ImportModel;
import com.gobestsoft.mamage.moudle.system.service.BlackboardService;
import com.gobestsoft.mamage.moudle.system.service.MemberStatisticsService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 总览信息
 *
 * @author gobestsoft
 * @Date 2017年3月4日23:05:54
 */
@Controller
@RequestMapping("/blackboard")
public class BlackboardController extends BaseController {


    @Autowired
    private BlackboardService blackboardService;

    @Autowired
    private BlackboardDao blackboardDao;


    private final String FarmerByTimeRange = "const_FarmerByTimeRange";
    private final String FarmerNotByTimeRange = "const_NotFarmerByTimeRange";

    /**
     * 跳转到首页黑板
     */
    @RequestMapping("")
    public String blackboard(Model model) {
        List<Integer> list = getLoginUser().getRoleIds();
        //权限控制
        String roldetype = "0";
        if (list != null) {
            for (Integer roleId : list) {
                if (roleId == 1) {
                    roldetype = "1";
                    break;
                }
            }
        }
        model.addAttribute("roldetype", roldetype);
        return "blackboard";
    }


    /**
     * 首页统计
     *
     * @return
     */
    @RequestMapping("/getStatistics")
    @ResponseBody
    public Tip getStatistics() {
        RedisCacheModel statistics;
        try {
            statistics = (RedisCacheModel) redisUtils.get(CacheConstant.ZWPT_BLACKBOARD);
        } catch (Exception e) {
            return fail("缓存服务未开启，请联系管理员");
        }
        if (statistics == null) {
            statistics();
        }
        //超时则偷偷更新
        else if (statistics.isExpired()) {

            boolean blackboard_lock = redisUtils.tryGetDistributedLock("blackboard_lock", UUIDUtil.getUUID(), 10);

            if (blackboard_lock) {
                new Thread(() -> statistics()).start();
            }

        }
        return new Tip(200, null, statistics.getData());
    }

    @Autowired
    private MemberStatisticsService memberStatisticsService;


    private Map<String, Object> statistics() {
        Map<String, Object> result = new HashMap<>();
        //会员统计
        result.put("memberStatistics", memberStatisticsService.summarySingle());
        //近一年各月注册和入会人员统计
        result.put("lastYearMonthStatistics", blackboardService.lastYearMonthStatistics());
        //会员总量排名前十工会统计
        result.put("memberTop10Statistics", blackboardService.memberTop10Statistics());
        //近一个月入会人数排名前五工会统计
        result.put("joinOrganizationTop5", blackboardService.joinOrganizationTop5());
        //省总工会直属前十
        result.put("provincialUnionsTop10", blackboardService.provincialUnionsTop10());
        //省总工会直属更多
        result.put("provincialUnionsMore", blackboardService.provincialUnionsMore());//省总更多
        //基层企业工会排名前十工会统计
        result.put("grassrootsEnterprisesTop10", blackboardService.grassrootsEnterprisesTop10());
        //result.put("orgnationTop10Statistics",blackboardService.orgnationTop10Statistics);
        // result.put("organizationalRank",blackboardService.organizationalRank());//组织数
        //近一年各月会员统计
        result.put("memmberRank", blackboardService.memmberRank());

        redisUtils.set(CacheConstant.ZWPT_BLACKBOARD, new RedisCacheModel(result, 60));
        return result;
    }

    //每月新增组织数排行
    @RequestMapping("/lastYearMonthStatistics")
    @ResponseBody
    public Object lastYearMonthStatistics() {
        return blackboardService.lastYearMonthStatistics();
    }


    //每月新增组织数排行
    @RequestMapping("/getCountStatistics")
    @ResponseBody
    public Object list() {
        Page<Map<String, Object>> page = defaultPage();
        List<Map<String, Object>> result = blackboardService.organizationalRank();
        page.setRecords(result);
        return super.packForBT(page);
    }


    //每月组织数排行
    @RequestMapping("/getCountStatistics2")
    @ResponseBody
    public Object list2() {
        Page<Map<String, Object>> page = defaultPage();
        List<Map<String, Object>> result = blackboardService.organizationalRank2();
        page.setRecords(result);
        return super.packForBT(page);
    }


    /**
     * excel数据作成
     */
    private ImportModel getEchartInfo(String type, Map<String, Map<String, Object>> maps) {
        ImportModel importInfo = new ImportModel();
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        List<String> strType = Arrays.asList(type.split(","));

        for (String str : strType) {
            //工会会员统计
            if (ToolUtil.equals("1", str)) {
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("工会会员人数统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("男");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("女");

                try {
                    Integer indexRow = titleRow.getRowNum() + 1;
                    Map<String, Object> map1 = maps.get("map1");
                    HSSFRow newRow = sheet.createRow(indexRow);
                    HSSFCell cell = newRow.createCell(0);
                    cell.setCellValue(getChangeStr(map1.get("men")));
                    cell = newRow.createCell(1);
                    cell.setCellValue(getChangeStr(map1.get("women")));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //注册和入会会员统计
            if (ToolUtil.equals("2", str)) {
                Map<String, Object> map2 = maps.get("map2");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("注册和入会会员统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("月份");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("注册人员");
                titleCell = titleRow.createCell(2);
                titleCell.setCellValue("入会人员");
                titleCell = titleRow.createCell(3);
                titleCell.setCellValue("绑定会员");

                try {
                    int indexMon = 1;
                    List<Object> strMon = (List<Object>) map2.get("months");
                    for (Object obj : strMon) {
                        HSSFRow monRow = sheet.createRow(indexMon);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(obj));
                        indexMon++;
                    }

                    int indexReg = 1;
                    List<Object> strReg = (List<Object>) map2.get("register");
                    for (Object obj : strReg) {
                        HSSFRow regRow = sheet.getRow(indexReg);
                        HSSFCell cell = regRow.createCell(1);
                        cell.setCellValue(getChangeStr(obj));
                        indexReg++;
                    }


                    int indexMem = 1;
                    List<Object> strMem = (List<Object>) map2.get("member");
                    for (Object obj : strMem) {
                        HSSFRow regMem = sheet.getRow(indexMem);
                        HSSFCell cell = regMem.createCell(2);
                        cell.setCellValue(getChangeStr(obj));
                        indexMem++;
                    }

                    int indexBind = 1;
                    List<Object> strBind = (List<Object>) map2.get("bindUser");
                    for (Object obj : strBind) {
                        HSSFRow regBind = sheet.getRow(indexBind);
                        HSSFCell cell = regBind.createCell(3);
                        cell.setCellValue(getChangeStr(obj));
                        indexBind++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //会员总量排名前十
            if (ToolUtil.equals("3", str)) {
                Map<String, Object> map3 = maps.get("map3");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("会员总量排名前十");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("工会名称");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("会员总量");
                titleCell = titleRow.createCell(2);
                titleCell.setCellValue("男");
                titleCell = titleRow.createCell(3);
                titleCell.setCellValue("女");

                try {
                    Integer indexRow = 1;
                    List<List<String>> stringList = (List<List<String>>) map3.get("listInfo");
                    for (List<String> list : stringList) {
                        HSSFRow monRow = null;
                        Integer indexCell = 0;
                        for (int i = 0; i < list.size(); i++) {
                            if (i == 0) {
                                monRow = sheet.createRow(indexRow);
                            } else {
                                monRow = sheet.getRow(indexRow);
                            }
                            HSSFCell cell = monRow.createCell(indexCell);
                            cell.setCellValue(list.get(i));
                            indexCell++;
                        }
                        indexRow++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //省总工会直属前十
            if (ToolUtil.equals("4", str)) {
                Map<String, Object> map4 = maps.get("map4");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("省总工会直属前十统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("工会名称");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("会员总量");

                try {
                    int indexOrg = 1;
                    List<Object> strOrg = (List<Object>) map4.get("organization");
                    for (Object obj : strOrg) {
                        HSSFRow monRow = sheet.createRow(indexOrg);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(obj));
                        indexOrg++;
                    }

                    int indexCnt = 1;
                    List<Object> strReg = (List<Object>) map4.get("count");
                    for (Object obj : strReg) {
                        HSSFRow regRow = sheet.getRow(indexCnt);
                        HSSFCell cell = regRow.createCell(1);
                        cell.setCellValue(getChangeStr(obj));
                        indexCnt++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //基层企业工会排名前十
            if (ToolUtil.equals("5", str)) {
                Map<String, Object> map5 = maps.get("map5");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("基层企业工会排名前十");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("工会名称");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("会员总量");
                titleCell = titleRow.createCell(2);
                titleCell.setCellValue("男");
                titleCell = titleRow.createCell(3);
                titleCell.setCellValue("女");

                try {
                    Integer indexRow = 1;
                    List<List<String>> stringList = (List<List<String>>) map5.get("grassroots");
                    for (List<String> list : stringList) {
                        HSSFRow monRow = null;
                        Integer indexCell = 0;
                        for (int i = 0; i < list.size(); i++) {
                            if (i == 0) {
                                monRow = sheet.createRow(indexRow);
                            } else {
                                monRow = sheet.getRow(indexRow);
                            }
                            HSSFCell cell = monRow.createCell(indexCell);
                            cell.setCellValue(list.get(i));
                            indexCell++;
                        }
                        indexRow++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //近一年各月会员统计
            if (ToolUtil.equals("6", str)) {
                Map<String, Object> map6 = maps.get("map6");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("近一年各月会员统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("月份");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("新增会员人数");

                try {
                    int indexOrg = 1;
                    List<Object> strOrg = (List<Object>) map6.get("months");
                    for (Object obj : strOrg) {
                        HSSFRow monRow = sheet.createRow(indexOrg);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(obj));
                        indexOrg++;
                    }

                    int indexCnt = 1;
                    List<Object> strReg = (List<Object>) map6.get("memberCount");
                    for (Object obj : strReg) {
                        HSSFRow regRow = sheet.getRow(indexCnt);
                        HSSFCell cell = regRow.createCell(1);
                        cell.setCellValue(getChangeStr(obj));
                        indexCnt++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //近一年入会人数前五工会统计
            if (ToolUtil.equals("7", str)) {
                Map<String, Object> map7 = maps.get("map7");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("近一年入会人数前五统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("工会名称");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("新增入会人数");

                try {
                    int indexOrg = 1;
                    List<Object> strOrg = (List<Object>) map7.get("organization");
                    for (Object obj : strOrg) {
                        HSSFRow monRow = sheet.createRow(indexOrg);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(obj));
                        indexOrg++;
                    }

                    int indexCnt = 1;
                    List<Integer> strReg = (List<Integer>) map7.get("member");
                    for (Integer obj : strReg) {
                        HSSFRow regRow = sheet.getRow(indexCnt);
                        HSSFCell cell = regRow.createCell(1);
                        cell.setCellValue(getChangeStr(obj));
                        indexCnt++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //会员名族分布
            if (ToolUtil.equals("8", str)) {
                Map<String, Object> map8 = maps.get("map8");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("会员名族分布统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("名族");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("会员人数");

                try {
                    int indexOrg = 1;
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map8.get("map");
                    for (Map<String, Object> mapNat : list) {
                        HSSFRow monRow = sheet.createRow(indexOrg);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(mapNat.get("name")));
                        HSSFRow monRow1 = sheet.getRow(indexOrg);
                        HSSFCell cell1 = monRow1.createCell(1);
                        cell1.setCellValue(getChangeStr(mapNat.get("value")));
                        indexOrg++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //会员年龄分布
            if (ToolUtil.equals("9", str)) {
                Map<String, Object> map9 = maps.get("map9");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("会员年龄分布统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("年龄段");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("会员人数");

                try {
                    int indexOrg = 1;
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map9.get("map");
                    for (Map<String, Object> mapNat : list) {
                        HSSFRow monRow = sheet.createRow(indexOrg);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(mapNat.get("name")));
                        HSSFRow monRow1 = sheet.getRow(indexOrg);
                        HSSFCell cell1 = monRow1.createCell(1);
                        cell1.setCellValue(getChangeStr(mapNat.get("value")));
                        indexOrg++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //会员学历分布
            if (ToolUtil.equals("10", str)) {
                Map<String, Object> map10 = maps.get("map10");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("会员学历分布统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("学历名称");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("会员人数");

                try {
                    int indexOrg = 1;
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map10.get("map");
                    for (Map<String, Object> mapNat : list) {
                        HSSFRow monRow = sheet.createRow(indexOrg);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(mapNat.get("name")));
                        HSSFRow monRow1 = sheet.getRow(indexOrg);
                        HSSFCell cell1 = monRow1.createCell(1);
                        cell1.setCellValue(getChangeStr(mapNat.get("value")));
                        indexOrg++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //农名工职工比例
            if (ToolUtil.equals("11", str)) {
                Map<String, Object> map11 = maps.get("map11");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("农民工职工比例");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("名称");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("人数");

                try {
                    int indexOrg = 1;
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map11.get("map");
                    for (Map<String, Object> mapNat : list) {
                        HSSFRow monRow = sheet.createRow(indexOrg);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(mapNat.get("name")));
                        HSSFRow monRow1 = sheet.getRow(indexOrg);
                        HSSFCell cell1 = monRow1.createCell(1);
                        cell1.setCellValue(getChangeStr(mapNat.get("value")));
                        indexOrg++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

            //会员户籍分布统计
            if (ToolUtil.equals("12", str)) {
                Map<String, Object> map12 = maps.get("map12");
                //生成一个表格
                HSSFSheet sheet = wb.createSheet("会员户籍分布统计");
                // 第三步，在sheet中添加表头第0行
                HSSFRow titleRow = sheet.createRow(0);
                HSSFCell titleCell = titleRow.createCell(0);
                titleCell.setCellValue("户籍所在地");
                titleCell = titleRow.createCell(1);
                titleCell.setCellValue("会员人数");

                try {
                    int indexOrg = 1;
                    List<Map<String, Object>> list = (List<Map<String, Object>>) map12.get("map");
                    for (Map<String, Object> mapNat : list) {
                        HSSFRow monRow = sheet.createRow(indexOrg);
                        HSSFCell cell = monRow.createCell(0);
                        cell.setCellValue(getChangeStr(mapNat.get("name")));
                        HSSFRow monRow1 = sheet.getRow(indexOrg);
                        HSSFCell cell1 = monRow1.createCell(1);
                        cell1.setCellValue(getChangeStr(mapNat.get("value")));
                        indexOrg++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                continue;
            }

        }

        importInfo.setWorkbook(wb);

        return importInfo;
    }


    //转成String
    private String getChangeStr(Object o) {
        if (o == null) {
            return "";
        } else {
            return String.valueOf(o);
        }
    }

    /**
     * 上传文件
     */
    public String uploadFile(ImportModel importModel, HttpServletResponse response) throws IOException, FileNotFoundException {
        String fileUploadPath = manageProperties.getFileUploadPath();
        String pathName = "";
        String fileName = "";
        if (!importModel.isHasError()) {
            fileName = UploadConstants.ECHART_EXCEL + DateUtil.getDays() + "/" + new String(UUIDUtil.getUUID().getBytes("utf-8"), "utf-8") + ".xls";
            pathName = fileUploadPath + fileName;
            System.out.println("保存路径：" + pathName);
            File file = new File(pathName);
            file.getParentFile().mkdirs();
            OutputStream output = new FileOutputStream(pathName);
            try {
                importModel.getWorkbook().write(output);
                output.flush();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        pathName = WebSiteUtil.getBrowseUrl(fileName);
        return pathName;
    }

    /**
     * 下载excel
     */
    @RequestMapping("/downLoadExcel")
    @ResponseBody
    public Tip downLoadExcel(@RequestParam("type") String type, HttpServletResponse response) throws Exception {
        String pathName = "";
        List<String> list = Arrays.asList(type.split(","));

        final RedisCacheModel redisCacheModel;
        try {
            redisCacheModel = (RedisCacheModel) redisUtils.get("LOAD_BLACKBOARD");
        } catch (Exception e) {
            return fail("缓存服务未开启，请联系管理员");
        }

        if (redisCacheModel == null) {
            Map<String, Object> mapLoad = new HashMap<>();
            mapLoad.put("memMap", blackboardService.memberStatistics());
            mapLoad.put("lastYear", blackboardService.lastYearMonthStatistics());
            mapLoad.put("memTop10", blackboardDao.memberTop10Statistics());
            mapLoad.put("unionsTop10", blackboardService.provincialUnionsTop10());
            mapLoad.put("enterprisesTop10", blackboardDao.grassrootsEnterprisesTop10());
            mapLoad.put("memRank", blackboardService.memmberRank());
            mapLoad.put("orgTop5", blackboardService.joinOrganizationTop5());
            mapLoad.put("infoByNation", blackboardService.getMemberInfoByNation());
            mapLoad.put("infoByAge", blackboardService.getMemberInfoByAge());
            mapLoad.put("infoByEdu", blackboardService.getMemberInfoByEducation());
            mapLoad.put("infoByFar", blackboardService.getMemberInfoByFarmer());
            mapLoad.put("infoByDoc", blackboardService.getMemberInfoByDoclie());
            redisUtils.set("LOAD_BLACKBOARD", new RedisCacheModel(mapLoad, 1200));
        }

        Map<String, Map<String, Object>> map = new HashMap<>();
        list.forEach(s -> {
            if (ToolUtil.equals("1", s)) {
                Map<String, Object> map1 = (Map<String, Object>) ((Map<String, Object>) redisCacheModel.getData()).get("memMap");
                map.put("map1", map1);
            }

            if (ToolUtil.equals("2", s)) {
                Map<String, Object> map2 = (Map<String, Object>) ((Map<String, Object>) redisCacheModel.getData()).get("lastYear");
                map.put("map2", map2);
            }

            if (ToolUtil.equals("3", s)) {
                List<Map<String, Object>> memberTop10Statistics = (List<Map<String, Object>>) ((Map<String, Object>) redisCacheModel.getData()).get("memTop10");
                List<List<String>> listInfo = new ArrayList<List<String>>();
                for (Map<String, Object> mapMem : memberTop10Statistics) {
                    List<String> str = new ArrayList<String>();
                    str.add(mapMem.get("fullname").toString());
                    str.add(mapMem.get("memberCount").toString());
                    str.add(mapMem.get("men").toString());
                    str.add(mapMem.get("women").toString());
                    listInfo.add(str);
                }
                Map<String, Object> map1 = new HashMap<>();
                map1.put("listInfo", listInfo);
                map.put("map3", map1);
            }

            if (ToolUtil.equals("4", s)) {
                Map<String, Object> map4 = (Map<String, Object>) ((Map<String, Object>) redisCacheModel.getData()).get("unionsTop10");
                map.put("map4", map4);
            }

            if (ToolUtil.equals("5", s)) {
                List<Map<String, Object>> grassrootsEnterprisesTop10 = (List<Map<String, Object>>) ((Map<String, Object>) redisCacheModel.getData()).get("enterprisesTop10");
                List<List<String>> listInfo = new ArrayList<List<String>>();
                for (Map<String, Object> mapMem : grassrootsEnterprisesTop10) {
                    List<String> str = new ArrayList<String>();
                    str.add(mapMem.get("fullname").toString());
                    str.add(mapMem.get("memberCount").toString());
                    str.add(mapMem.get("men").toString());
                    str.add(mapMem.get("women").toString());
                    listInfo.add(str);
                }
                Map<String, Object> map1 = new HashMap<>();
                map1.put("grassroots", listInfo);
                map.put("map5", map1);
            }

            if (ToolUtil.equals("6", s)) {
                Map<String, Object> map6 = (Map<String, Object>) ((Map<String, Object>) redisCacheModel.getData()).get("memRank");
                map.put("map6", map6);
            }

            if (ToolUtil.equals("7", s)) {
                Map<String, Object> map7 = (Map<String, Object>) ((Map<String, Object>) redisCacheModel.getData()).get("orgTop5");
                map.put("map7", map7);
            }

            if (ToolUtil.equals("8", s)) {
                List<Map<String, Object>> map8 = (List<Map<String, Object>>) ((Map<String, Object>) redisCacheModel.getData()).get("infoByNation");
                Map<String, Object> mapInfo = new HashMap<>();
                mapInfo.put("map", map8);
                map.put("map8", mapInfo);
            }

            if (ToolUtil.equals("9", s)) {
                ObjectMap map9 = (ObjectMap) ((Map<String, Object>) redisCacheModel.getData()).get("infoByAge");
                List<Map<String, Object>> mapAge = (List<Map<String, Object>>) map9.get("list");
                Map<String, Object> mapInfo = new HashMap<>();
                mapInfo.put("map", mapAge);
                map.put("map9", mapInfo);
            }

            if (ToolUtil.equals("10", s)) {
                List<Map<String, Object>> map10 = (List<Map<String, Object>>) ((Map<String, Object>) redisCacheModel.getData()).get("infoByEdu");
                Map<String, Object> mapInfo = new HashMap<>();
                mapInfo.put("map", map10);
                map.put("map10", mapInfo);
            }

            if (ToolUtil.equals("11", s)) {
                List<Map<String, Object>> map11 = (List<Map<String, Object>>) ((Map<String, Object>) redisCacheModel.getData()).get("infoByFar");
                Map<String, Object> mapInfo = new HashMap<>();
                mapInfo.put("map", map11);
                map.put("map11", mapInfo);
            }

            if (ToolUtil.equals("12", s)) {
                ObjectMap objectMap = (ObjectMap) ((Map<String, Object>) redisCacheModel.getData()).get("infoByDoc");
                List<Map<String, Object>> map12 = (List<Map<String, Object>>) objectMap.get("list");
                Map<String, Object> mapInfo = new HashMap<>();
                mapInfo.put("map", map12);
                map.put("map12", mapInfo);
            }

        });

        ImportModel importModel = getEchartInfo(type, map);
        pathName = uploadFile(importModel, response);

        return new Tip(200, null, pathName);
    }

    /**
     * 获取民族人数
     *
     * @return
     */
    @RequestMapping("/getMemberInfoByNation")
    @ResponseBody
    public Object getMemberInfoByNation() {

        ObjectMap map = (ObjectMap) redisUtils.get("meberInfo_1");
        if (map != null) {
            return map;
        }
        List<Map<String, Object>> list;
        list = blackboardService.getMemberInfoByNation();
        if (list == null) return null;

        map = ObjectMap.getInstance();

        map.put("list", list);

        List<String> legend = new ArrayList<>();

        list.forEach(m -> {
            legend.add(m.get("name") + "");

        });

        map.put("legend", legend);
        redisUtils.set("meberInfo_1", map, 120l);
        return map;
    }


    /**
     * 根据身份证中的年龄统计人员
     *
     * @return
     */
    @RequestMapping("/getMemberInfoByAge")
    @ResponseBody
    public Object getMemberInfoByAge() {
        ObjectMap map = (ObjectMap) redisUtils.get("meberInfo_2");
        if (map != null) {
            return map;
        }
        map = blackboardService.getMemberInfoByAge();
        redisUtils.set("meberInfo_2", map, 480l);
        return map;
    }

    /**
     * 根据户籍统计人员
     *
     * @return
     */
    @RequestMapping("/getMemberInfoByDocile")
    @ResponseBody
    public Object getMemberInfoByDocile() {
        ObjectMap map = (ObjectMap) redisUtils.get("meberInfo_4");
        if (map != null) {
            return map;
        }
        map = blackboardService.getMemberInfoByDoclie();
        redisUtils.set("meberInfo_4", map, 120l);
        return map;
    }

    /**
     * 获取学历人数
     *
     * @return
     */
    @RequestMapping("/getMemberInfoByEducation")
    @ResponseBody
    public Object getMemberInfoByEducation() {

        ObjectMap map = (ObjectMap) redisUtils.get("meberInfo_3");
        if (map != null) {
            return map;
        }
        List<Map<String, Object>> list;
        list = blackboardService.getMemberInfoByEducation();
        if (list == null) return null;

        map = ObjectMap.getInstance();

        map.put("list", list);

        List<String> legend = new ArrayList<>();

        list.forEach(m -> {
            legend.add(m.get("name") + "");

        });

        map.put("legend", legend);
        redisUtils.set("meberInfo_3", map, 120l);
        return map;
    }

    /**
     * 农民工比例
     *
     * @return
     */
    @RequestMapping("/getMemberInfoByFarmer")
    @ResponseBody
    public Object getMemberInfoByFarmer() {

        ObjectMap map = (ObjectMap) redisUtils.get("memberInfo_5");
        if (map != null) {
            return map;
        }
        List<Map<String, Object>> list;
        list = blackboardService.getMemberInfoByFarmer();
        if (list == null) return null;

        map = ObjectMap.getInstance();

        map.put("list", list);

        List<String> legend = new ArrayList<>();

        list.forEach(m -> {
            legend.add(m.get("name") + "");

        });

        map.put("legend", legend);
        redisUtils.set("memberInfo_5", map, 120l);
        return map;
    }


    /**
     * 单位会员人数前10
     *
     * @return
     */
    private Map<String, Object> getmemberTop10Statistics() {
        Map<String, Object> memberTop10Statistics = (Map<String, Object>) redisUtils.get("memberTop10Statistics");
        if (memberTop10Statistics == null) {
            memberTop10Statistics = blackboardService.memberTop10Statistics();
        }
        return memberTop10Statistics;


    }

    /**
     * 全年各月份会员趋势
     *
     * @return
     */
    private Map<String, Object> getMonthMemberStatistics() {
        Map<String, Object> monthMemberStatistics = (Map<String, Object>) redisUtils.get("monthMemberStatistics");
        if (monthMemberStatistics == null) {
            Map m = blackboardService.lastYearMonthStatistics();
            if (m != null) {
                monthMemberStatistics = new HashMap<>();
                monthMemberStatistics.put("memberCount", m.get("memberCount"));
                monthMemberStatistics.put("member", m.get("member"));

            }
        }
        return monthMemberStatistics;


    }


    @RequestMapping("/getFarmerByTimeRange")
    @ResponseBody
    public Object getFarmerByTimeRange(@RequestParam(required = true) String start, @RequestParam(required = true) String end) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) redisUtils.get(FarmerByTimeRange);
        if (list == null) {

            list = new ArrayList<>();
            List<String> dateList = new ArrayList<>();
            Calendar instance = Calendar.getInstance();

            while (true) {

                String days = DateUtil.getDays(instance.getTime());
                Integer s = Integer.valueOf(start);
                Integer d = Integer.valueOf(days);
                if (d < s) {
                    break;
                }
                instance.add(Calendar.DAY_OF_MONTH, -1);
                dateList.add(days);
            }


            for (String date : dateList) {
                Map<String, Object> map = new HashMap<>();
                map.put("createTime", date);
                map.put("value", blackboardDao.selectHouseHoldMemberByDate(date, "00"));
                list.add(map);
            }

            redisUtils.set(FarmerByTimeRange, list, 30 * 60l);
        }

        return list;
    }


    @RequestMapping("/getNotFarmerByTimeRange")
    @ResponseBody
    public Object getNotFarmerByTimeRange(@RequestParam(required = true) String start, @RequestParam(required = true) String end) {
        List<Map<String, Object>> list = (List<Map<String, Object>>) redisUtils.get(FarmerNotByTimeRange);
        if (list == null) {

            list = new ArrayList<>();
            List<String> dateList = new ArrayList<>();
            Calendar instance = Calendar.getInstance();

            while (true) {

                String days = DateUtil.getDays(instance.getTime());
                Integer s = Integer.valueOf(start);
                Integer d = Integer.valueOf(days);
                if (d < s) {
                    break;
                }
                instance.add(Calendar.DAY_OF_MONTH, -1);
                dateList.add(days);
            }


            for (String date : dateList) {
                Map<String, Object> map = new HashMap<>();
                map.put("createTime", date);
                map.put("value", blackboardDao.selectHouseHoldMemberByDate(date, "01"));
                list.add(map);
            }

            redisUtils.set(FarmerNotByTimeRange, list, 30 * 60l);
        }

        return list;
    }
}