package com.gobestsoft.mamage.moudle.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.UploadConstants;
import com.gobestsoft.common.constant.model.OrganizationRankData;
import com.gobestsoft.common.modular.system.dao.BlackboardDao;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.UUIDUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.dept.model.ImportModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/overallpep")
public class OverAllPepController extends BaseController {

    @Autowired
    private BlackboardDao blackboardDao;

    private final String OverAllPep = "cnt_OverAllPep";

    @RequestMapping("")
    public String getOverAllPep(){

        return "overAllPep";
    }

    /**
     * 会员，认证，农民工人数统计
     *
     * @return
     */
    @RequestMapping("/getOverAllPep")
    @ResponseBody
    public Object getOverAllPepCnt(){

        Page<Map<String,Object>> page = defaultPage();

        List<Map<String, Object>> list = (List<Map<String, Object>>)redisUtils.get(OverAllPep);
        if(list == null){
            list = new ArrayList<>();
            List<OrganizationRankData> listInfo = blackboardDao.provincialUnionsTop10();

            for(OrganizationRankData orgInfo: listInfo){
                Map<String, Object> map = new HashMap<>();
                map.put("unionName", orgInfo.getName());
                map.put("allMemCnt", blackboardDao.getAllMemCnt(orgInfo.getId()));
                map.put("allAuthMemCnt", blackboardDao.getAllAuthMemCnt(orgInfo.getId()));
                map.put("allFarmerCnt", blackboardDao.getAllFarmerCnt(orgInfo.getId()));
                list.add(map);
            }

            redisUtils.set(OverAllPep, list,30*60l);
        }
        page.setRecords(list);

        return super.packForBT(page);
    }

    /**
     * 导出excel
     */
    @RequestMapping("/downExportExcel")
    @ResponseBody
    public Tip getExportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception{

            String excelJsonTxt = request.getParameter("excelJsonTxt");
            JSONArray jsonArray = JSON.parseArray(excelJsonTxt);

            ImportModel importInfo = getExportExcel(jsonArray);
            String pathName = uploadFile(importInfo, response);

            return new Tip(200,null,pathName);
    }

    /**
     * 数据作成
     */
    private ImportModel getExportExcel(JSONArray jsonArray){

        String unionName = "工会名称,总会员人数,已认证会员人数,农业户口人数";
        ImportModel importModel = new ImportModel();
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("省总下级工会人数统计");

        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);

        try{

            String[] strInfo = unionName.split(",");
            for(int i=0; i<strInfo.length; i++){
                titleCell.setCellValue(strInfo[i]);
                titleCell = titleRow.createCell(i+1);
            }

            HSSFRow rows;
            HSSFCell cells;
            for (int i = 0; i < jsonArray.size(); i++) {
                // 第三步：在这个sheet页里创建一行
                rows = sheet.createRow(i+1);
                // 第四步：在该行创建一个单元格
                cells = rows.createCell(0);
                // 第五步：在该单元格里设置值
                JSONArray tBody = (JSONArray) jsonArray.get(i);
                for(int j=0; j<tBody.size();j++){
                    cells.setCellValue(tBody.get(j).toString());
                    cells = rows.createCell(j+1);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }

        importModel.setWorkbook(wb);

        return importModel;
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
}
