package com.gobestsoft.mamage.moudle.dept.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gobestsoft.common.constant.DictCodeConstants;
import com.gobestsoft.common.constant.model.OrganizationRankData;
import com.gobestsoft.common.modular.dept.dao.MemberDao;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptMemberTransferMapper;
import com.gobestsoft.common.modular.dept.mapper.DeptOrganizationMapper;
import com.gobestsoft.common.modular.dept.mapper.PersonInfoMapper;
import com.gobestsoft.common.modular.dept.model.*;
import com.gobestsoft.common.modular.model.ObjectMap;
import com.gobestsoft.common.modular.system.dao.BlackboardDao;
import com.gobestsoft.common.modular.system.model.Dict;
import com.gobestsoft.core.util.*;
import com.gobestsoft.mamage.basic.ManageBasic;
import com.gobestsoft.mamage.config.member.MemberUtil;
import com.gobestsoft.mamage.constant.factory.IConstantFactory;
import com.gobestsoft.mamage.model.LoginUser;
import com.gobestsoft.mamage.moudle.dept.model.ImportModel;
import com.gobestsoft.rabbitmq.RabbitSender;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 会员信息业务层
 *
 * @author xss
 * @date 2018-08-16
 */
@Service
public class DeptMemberService extends ManageBasic {

    @Resource
    DeptMemberMapper memberMapper;

    @Resource
    PersonInfoMapper personInfoMapper;

    @Resource
    private DeptOtherService deptOtherService;

    @Resource
    BlackboardDao blackboardDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private DeptOrganizationMapper deptOrganizationMapper;

    @Autowired
    private DeptMemberTransferMapper deptMemberTransferMapper;

    @Autowired
    private IConstantFactory constantFactory;

    private final static String xls = ".xls";
    private final static String xlsx = ".xlsx";

    /**
     * 多条件分页查询
     *
     * @param page
     * @param name      姓名
     * @param unionName 所属工会组织名称
     * @param deptId    所属工会组织id
     * @return
     */
    public List<Map<String, Object>> selectByCondition(Page<Map<String, Object>> page, String name, String deptNo, String memberCard, String unionName, Integer deptId, String memberRange, ObjectMap map) {
        map.put("name", name);
        map.put("deptNo", deptNo);
        map.put("memberCard", memberCard);
        map.put("unionName", unionName);
        map.put("deptId", deptId);
        map.put("memberRange", memberRange);

        List<Map<String, Object>> list = memberMapper.selectByCondition(page, map);
        list.forEach( m -> {
            this.getDictName(m);
        });
        return list;
    }

    /**
     * 获取字典值
     */
    private void getDictName(Map<String, Object> m) {
        if(m.containsKey("sex")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_SEX,String.valueOf(m.get("sex")));
            m.put("sexName", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("nation")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_ETHNIC_GROUP,String.valueOf(m.get("nation")));
            m.put("nation", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("workSituation")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_WORK_SITUATION,String.valueOf(m.get("workSituation")));
            m.put("workSituation", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("certificateType")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_IDENTIFICATION_TYPE,String.valueOf(m.get("certificateType")));
            m.put("certificateType", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("education")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_EDUCATION_CODE,String.valueOf(m.get("education")));
            m.put("education", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("technologyLevel")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_TECHNOLOGY,String.valueOf(m.get("technologyLevel")));
            m.put("technologyLevel", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("household")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_HOUSEHOLD,String.valueOf(m.get("household")));
            m.put("household", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("politicalStatus")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_POLITICAL_STATUS,String.valueOf(m.get("politicalStatus")));
            m.put("politicalStatus", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("memberChange")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_MEMBER_CHANGE,String.valueOf(m.get("memberChange")));
            m.put("memberChange", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
        if(m.containsKey("member_change_reason")){
            Dict dict =  constantFactory.findInDictName(DictCodeConstants.LIB_MEMBER_CHANGE_REASON,String.valueOf(m.get("memberChangeReason")));
            m.put("memberChangeReason", ToolUtil.isNotEmpty(dict) ? dict.getName() : "-");
        }
    }

    public List<DeptMemberInfoEntity> getMemberList(String name, String deptNo, String memberCard, String unionName, Integer deptId, String memberRange, Map<String, Object> map) {
        map.put("name", name);
        map.put("deptNo", deptNo);
        map.put("memberCard", memberCard);
        map.put("unionName", unionName);
        map.put("deptId", deptId);
        map.put("memberRange", memberRange);
        return memberMapper.getMemberList(map);
    }

    /**
     * 查询当前登录用户所属组织下及子组织、子部门下的会员
     *
     * @param page
     * @param name
     * @param unionName
     * @return
     */
    public List<Map<String, Object>> selectByConditionByDeptId(Page<Map<String, Object>> page, String name, String deptNo, String memberCard, String unionName, Integer orgId) {
        return memberMapper.selectByConditionByDeptId(page, name, deptNo, memberCard, unionName, orgId);
    }

    public List<Map<String, Object>> selectByMemberId(Integer id) {
        return memberMapper.selectByMemberId(id);
    }

    public boolean hasMemberIn(String certificateNum) {
        return personInfoMapper.selectCount(new EntityWrapper().eq("certificate_num", certificateNum)) > 0;
    }

    /**
     * 根据所属工会id,获取会员id拼接的字符串,多个id以逗号分隔
     *
     * @param deptId
     * @return
     */
    public String getMemberIdsByUnionId(Integer deptId) {
        return memberMapper.getMemberIdsByUnionId(deptId);
    }

    /**
     * 新增一条会员信息
     *
     * @param model
     * @param deptId
     */
    public void insertMember(PersonInfo model, Integer deptId, LoginUser user) {
        personInfoMapper.insert(model);
        //组织公司信息
        DeptOrganization organization = deptOrganizationMapper.selectByDeptId(deptId);
        DeptMember member = new DeptMember();
        member.setDeptId(deptId);
        member.setPersonId(model.getId());
//        member.setStationCard(MemberUtil.getMemberCardNo(model.getSex(), organization.getUnionName(), organization.getUnitDistrict()));
        member.setMemberCard(null);
        member.setCreateUser(user.getId());
        member.setCreateTime(DateUtil.getAllTime());
        member.setUpdateUser(user.getId());
        member.setUpdateTime(DateUtil.getAllTime());
        member.setType("1");//普通会员
        memberMapper.insert(member);
        //更新各组织会员人数
        new Thread(new Runnable() {
            @Override
            public void run() {
                //更新各组织会员人数
                memberDao.updateMemberCountTable1();
                memberDao.updateBindMemberCountTable1();
                memberDao.updateMemberCountTable2();
            }
        }).start();
    }

    /**
     * 更新一条会员信息
     *
     * @param person
     * @param memberId
     */
    public void updateMemberById(PersonInfo person, Integer memberId, String uid) {
        personInfoMapper.updateById(person);
        DeptMember member = new DeptMember();
        member.setId(memberId);
        member.setUpdateUser(uid);
        member.setUpdateTime(DateUtil.getAllTime());
        memberMapper.updateById(member);
    }

    /**
     * 删除方法
     *
     * @param id
     */
    @Transactional
    public void remove(int id) {
        //执行删除会员信息操作
        memberMapper.deleteById(id);

    }

    /**
     * 删除会员表及关联表信息
     *
     * @param id
     * @param personId
     */
    @Transactional
    public void removeMemberAndPerson(Integer id, Integer personId) {
        //执行删除会员信息操作
        memberMapper.deleteById(id);
        //删除app用户的会员绑定
        memberMapper.clearAppuser(id);

        // 执行删除关联表会员信息操作
        personInfoMapper.deleteById(personId);

    }

    /**
     * 根据工会组织id,查询会员数量
     *
     * @param unionId
     * @return
     */
    public Integer selectCountByUnionId(Integer unionId) {
        Wrapper<DeptMember> wrapper = new EntityWrapper<>();
        wrapper.eq("union_id", unionId);
        return memberMapper.selectCount(wrapper);
    }

    /**
     * 根据工会组织id,查询会员的男的人数
     *
     * @param unionId
     * @param sex
     * @return
     */
    public Integer selectMemberMaleCountByUnionid(Integer unionId, Integer sex) {
        Wrapper<DeptMember> wrapper = new EntityWrapper<>();
        wrapper.eq("union_id", unionId);
        wrapper.eq("sex", sex);
        return memberMapper.selectCount(wrapper);
    }

    /**
     * 查询方法
     *
     * @param id 主键
     * @return
     */
    public DeptMember selectById(Integer id) {
        return memberMapper.selectById(id);
    }

    /**
     * 导入并导出文件
     *
     * @param uploadFileName 上传文件名
     * @param fileUploadPath 保存地址
     * @param is
     * @param createUid
     * @param deptId
     */
    public void importAndExport(String uploadFileName, String fileUploadPath, InputStream is, String createUid, Integer deptId) {
        try {
            Integer logId = deptOtherService.addImportLog(createUid,1);
            ImportModel importModel = null;
            //组织公司信息
            DeptOrganization organization = deptOrganizationMapper.selectByDeptId(deptId);
            if (StringUtils.isNotEmpty(organization.getUnitDistrict())) {
                synchronized (this){
                    if (uploadFileName.toLowerCase().endsWith(xls)) {
                        importModel = uploadMember(xls, is, createUid, deptId, organization.getUnionName(), organization.getUnitDistrict());
                    } else if (uploadFileName.toLowerCase().endsWith(xlsx)) {
                        importModel = uploadMember(xlsx, is, createUid, deptId, organization.getUnionName(), organization.getUnitDistrict());
                    }
                }
            } else {
                importModel = new ImportModel();
                importModel.setHasError(true);
                importModel.setRemark("当前导入的会员组织，行政区错误请修改后重试!");
            }
            deptOtherService.uploadSaveFile(importModel, fileUploadPath, deptOtherService, logId);
            //更新各组织会员人数
            memberDao.updateMemberCountTable1();
            memberDao.updateBindMemberCountTable1();
            memberDao.updateMemberCountTable2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量导入会员信息
     *
     * @param type
     * @param is
     * @return
     * @throws Exception
     */
    private ImportModel uploadMember(String type, InputStream is, String uid, Integer deptId, String unionName, String districtCode) throws Exception {
        ImportModel importModel = new ImportModel();
        List<Map<String, Object>> logs = readXls(type, is, uid, deptId, importModel, unionName, districtCode);
        if (!importModel.isHasError()) {
            exportMember(logs, importModel);
        }
        return importModel;
    }

    /**
     * 数据导出excel
     *
     * @param logs
     * @return
     */
    private void exportMember(List<Map<String, Object>> logs, ImportModel importModel) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //生成一个表格
        HSSFSheet sheet = wb.createSheet("导入会员明细");
        // 第三步，在sheet中添加表头第0行
        HSSFRow titleRow = createSheetTitle(sheet);

        try {
            Integer indexRow = titleRow.getRowNum() + 1;
            int errorCount = 0;// 统计未成功的数据
            for (Map<String, Object> record : logs) {
                // 设置单元格的值
                buildCell(sheet, indexRow, record);
                indexRow += 1;

                // 统计未成功的数据
                if (!"1".equals(record.get("if_success"))) {
                    errorCount += 1;
                }
            }

            if (errorCount > 0) {
                importModel.setRemark("当前共导入" + logs.size() + "条数据，其中：" + errorCount + "条导入失败,请点击查看原因!");
            } else {
                importModel.setRemark("当前共导入" + logs.size() + "条数据,无导入失败数据!");
            }
            HSSFRow tipRow = sheet.createRow(indexRow);
            HSSFCell tipCell = tipRow.createCell(0);
            tipCell.setCellValue("当前共导入" + logs.size() + "条数据，其中：" + errorCount + "条导入失败");

        } catch (Exception e) {
            e.printStackTrace();
        }

        importModel.setWorkbook(wb);
    }

    private void buildCell(HSSFSheet sheet, Integer indexRow, Map<String, Object> record) {
        HSSFRow newRow = sheet.createRow(indexRow);
        HSSFCell cell = newRow.createCell(0);
        cell.setCellValue(convertToStr(record.get("index")));
        cell = newRow.createCell(1);
        cell.setCellValue(convertToStr(record.get("name")));
        cell = newRow.createCell(2);
        cell.setCellValue(convertToStr(record.get("sex")));
        cell = newRow.createCell(3);
        cell.setCellValue(convertToStr(record.get("birthday")));
        cell = newRow.createCell(4);
        cell.setCellValue(convertToStr(record.get("nation")));
        cell = newRow.createCell(5);
        cell.setCellValue(convertToStr(record.get("work_situation")));
        cell = newRow.createCell(6);
        cell.setCellValue(convertToStr(record.get("certificate_type")));
        cell = newRow.createCell(7);
        cell.setCellValue(convertToStr(record.get("certificate_num")));
        cell = newRow.createCell(8);
        cell.setCellValue(convertToStr(record.get("education")));
        cell = newRow.createCell(9);
        cell.setCellValue(convertToStr(record.get("technology_level")));
        cell = newRow.createCell(10);
        cell.setCellValue(convertToStr(record.get("mobile")));
        cell = newRow.createCell(11);
        cell.setCellValue(convertToStr(record.get("household")));
        cell = newRow.createCell(12);
        cell.setCellValue(convertToStr(record.get("domicile")));
        cell = newRow.createCell(13);
        cell.setCellValue(convertToStr(record.get("member_change")));
        cell = newRow.createCell(14);
        cell.setCellValue(convertToStr(record.get("member_change_date")));
        cell = newRow.createCell(15);
        cell.setCellValue(convertToStr(record.get("member_change_reason")));
        cell = newRow.createCell(16);
        cell.setCellValue(convertToStr(record.get("political_status")));
        cell = newRow.createCell(17);
        cell.setCellValue(convertToStr(record.get("work_unit")));
        cell = newRow.createCell(18);
        cell.setCellValue(convertToStr(record.get("native_place")));
        cell = newRow.createCell(19);
        cell.setCellValue(convertToStr(record.get("is_farmer")));
        cell = newRow.createCell(20);
        cell.setCellValue("1".equals(record.get("if_success")) ? "是" : "否");
        cell = newRow.createCell(21);
        cell.setCellValue(convertToStr(record.get("errors_message")));
    }

    private HSSFRow createSheetTitle(HSSFSheet sheet) {
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("序号");
        titleCell = titleRow.createCell(1);
        titleCell.setCellValue("姓名");
        titleCell = titleRow.createCell(2);
        titleCell.setCellValue("性别");
        titleCell = titleRow.createCell(3);
        titleCell.setCellValue("出生日期");
        titleCell = titleRow.createCell(4);
        titleCell.setCellValue("民族");
        titleCell = titleRow.createCell(5);
        titleCell.setCellValue("就业状况");
        titleCell = titleRow.createCell(6);
        titleCell.setCellValue("有效证件类别");
        titleCell = titleRow.createCell(7);
        titleCell.setCellValue("证件号码");
        titleCell = titleRow.createCell(8);
        titleCell.setCellValue("学历");
        titleCell = titleRow.createCell(9);
        titleCell.setCellValue("技能等级");
        titleCell = titleRow.createCell(10);
        titleCell.setCellValue("移动电话");
        titleCell = titleRow.createCell(11);
        titleCell.setCellValue("户籍类型");
        titleCell = titleRow.createCell(12);
        titleCell.setCellValue("户籍所在地");
        titleCell = titleRow.createCell(13);
        titleCell.setCellValue("会籍变化类型");
        titleCell = titleRow.createCell(14);
        titleCell.setCellValue("会籍变化日期");
        titleCell = titleRow.createCell(15);
        titleCell.setCellValue("会籍变化原因");
        titleCell = titleRow.createCell(16);
        titleCell.setCellValue("政治面貌");
        titleCell = titleRow.createCell(17);
        titleCell.setCellValue("工作单位");
        titleCell = titleRow.createCell(18);
        titleCell.setCellValue("籍贯");
        titleCell = titleRow.createCell(19);
        titleCell.setCellValue("是否农民工");
        titleCell = titleRow.createCell(20);
        titleCell.setCellValue("是否导入成功");
        titleCell = titleRow.createCell(21);
        titleCell.setCellValue("错误原因");
        return titleRow;
    }

    /**
     * 转字符串
     *
     * @param o
     * @return
     */
    private String convertToStr(Object o) {
        if (o == null) {return "";}
        return String.valueOf(o).trim();//单元格中的数据去除两边的空格
    }

    /**
     * 非干部会员入会实行
     *
     * @param personInfo
     * @param user
     */
    public void addMember(PersonInfo personInfo, LoginUser user) {
        personInfoMapper.insert(personInfo);
        DeptMember deptMember = new DeptMember();
        deptMember.setPersonId(personInfo.getId());
        deptMember.setCreateUser(user.getId());
        deptMember.setCreateTime(DateUtil.getAllTime());
        deptMember.setUpdateUser(user.getId());
        deptMember.setUpdateTime(DateUtil.getAllTime());
        memberMapper.insert(deptMember);
    }

    /**
     * 读取Excel数据
     *
     * @param type
     * @param is
     * @return
     * @throws IOException
     */
    private List<Map<String, Object>> readXls(String type, InputStream is, String uid, Integer deptId, ImportModel importModel, String unionName, String districtCode) throws IOException {
        Workbook workbook = null;
        if (type.equals(xls)) {
            workbook = new HSSFWorkbook(is);
        } else if (type.equals(xlsx)) {
            workbook = new XSSFWorkbook(is);
        }

        // 获得工作表Sheet
        Sheet sheet = workbook.getSheetAt(0);
        int lastRow = sheet.getLastRowNum();

        List<CellRangeAddress> cellRangeAddresses = sheet.getMergedRegions();
        //判断是否含有其他的和并单元格，含有就不导入
        for (CellRangeAddress address : cellRangeAddresses) {
            //最后5行排除
            if (address.getFirstRow() != lastRow && address.getFirstRow() > 3) {
                importModel.setHasError(true);
                importModel.setRemark("模板最后一行为" + (lastRow + 1) + "行，但是第" + (address.getFirstRow() + 1) + "行到第" + (address.getLastRow() + 1) + "行含有合并单元格，不符合导入模板规则，请下载模板参照格式后重试!若最后一行是填表说明，请将后一行至" + (lastRow + 1) + "行选中右击删除行！");
            }
        }
        // 第5行为第一条数据 进行校验并封装校验结果
        List<Map<String, String>> parameters = new ArrayList<>();
        for (int rowNum = 4; rowNum <= lastRow - 1; rowNum++) {
            Map<String, String> memberMap = new HashMap<>();
            Row row = sheet.getRow(rowNum);
            if (!ExcelReaderUtil.isRowEmpty(row)) {
                checkCellData(row, memberMap, unionName, districtCode);
                parameters.add(memberMap);
            }
        }

        List<Map<String, Object>> export = null;

        if (!importModel.isHasError() && parameters.size() > 0) {
            String tableName = "dept_member_" + DateUtil.getAllTime() +  NumUtil.nextInt(100, 1000);
            String procedureName = "import_member_" + DateUtil.getAllTime() +  NumUtil.nextInt(100, 1000);
            try {
                //建临时表
                memberMapper.createTemporaryTable(tableName);
                //数据插入临时表
                memberMapper.insertList(tableName, parameters);
                //创建存储过程
                memberMapper.createImportMemberProcedure(procedureName, tableName, deptId, uid, DateUtil.getAllTime());
                //调用存储过程
                export = memberMapper.callImportMemberProcedure(procedureName);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //删除存储过程
                memberMapper.dropImportMemberProcedure(procedureName);
                //drop table
                memberMapper.dropTemporaryTable(tableName);
            }
        }

        return export;
    }

    /**
     * 检查单元格数据是否正确
     *
     * @param hssfRow
     * @param memberMap
     * @return
     */
    private void checkCellData(Row hssfRow, Map<String, String> memberMap, String unionName, String districtCode) {
        if (hssfRow == null) {
            return;
        }
        StringBuilder errorMessage = new StringBuilder("错误信息：");
        Integer ifSuccess = null;

        //序号
        Cell cell = hssfRow.getCell(0);
        memberMap.put("index", ExcelReaderUtil.cellData(cell));

        // 姓名
        cell = hssfRow.getCell(1);
        String name = ExcelReaderUtil.cellData(cell);
        if (StringUtils.isEmpty(name)) {
            errorMessage.append("-姓名不能为空");
            ifSuccess = 0;
        }
        memberMap.put("name", name);

        // 性别
        cell = hssfRow.getCell(2);
        String sex = ExcelReaderUtil.cellData(cell);
        memberMap.put("sex", sex);

        // 证件号码
        cell = hssfRow.getCell(7);
        String certificateNum = ExcelReaderUtil.cellData(cell);
        if (!StringRegularUtil.isIdentificationCard(certificateNum)) {
            errorMessage.append("-身份证号输入有误");
            ifSuccess = 0;
        }

        memberMap.put("certificate_num", certificateNum);

        // 出生日期
        cell = hssfRow.getCell(3);
        String birthday = ExcelReaderUtil.cellData(cell);
        try {

            if (StringUtils.isEmpty(birthday)) {
                birthday = certificateNum.substring(6, 14);
            } else {
                birthday = StringRegularUtil.TimeRegular.validateWithFormatDays(birthday, "yyyyMMdd");
                if (StringUtils.isEmpty(birthday)) {
                    if (StringRegularUtil.isIdentificationCard(certificateNum)) {
                        birthday = certificateNum.substring(6, 14);
                    } else {
                        errorMessage.append("-生日输入错误");
                        ifSuccess = 0;
                    }
                }
            }

        } catch (Exception ex) {
            if (StringRegularUtil.isIdentificationCard(certificateNum)) {
                birthday = certificateNum.substring(6, 14);
            } else {
                errorMessage.append("-生日输入错误");
                ifSuccess = 0;
            }
        }
        memberMap.put("birthday", birthday);

        // 民族
        cell = hssfRow.getCell(4);
        memberMap.put("nation", ExcelReaderUtil.cellData(cell));

        // 就业情况
        cell = hssfRow.getCell(5);
        String situation = ExcelReaderUtil.cellData(cell);
        memberMap.put("work_situation", situation);

        // 证件类型
        cell = hssfRow.getCell(6);
        memberMap.put("certificate_type", ExcelReaderUtil.cellData(cell));

        // 学历
        cell = hssfRow.getCell(8);
        memberMap.put("education", ExcelReaderUtil.cellData(cell));

        // 技术等级
        cell = hssfRow.getCell(9);
        memberMap.put("technology_level", ExcelReaderUtil.cellData(cell));

        // 移动电话
        cell = hssfRow.getCell(10);
        String mobile = ExcelReaderUtil.cellData(cell);
        if (StringUtils.isEmpty(mobile) || mobile.length() > 11) {
            errorMessage.append("-手机号格式错误");
            ifSuccess = 0;
        }
        memberMap.put("mobile", ExcelReaderUtil.cellData(cell));

        // 户籍类型
        cell = hssfRow.getCell(11);
        memberMap.put("household", ExcelReaderUtil.cellData(cell));

        // 户籍所在地
        cell = hssfRow.getCell(12);
        memberMap.put("domicile", ExcelReaderUtil.cellData(cell));

        // 会籍变化类型
        cell = hssfRow.getCell(13);
        memberMap.put("member_change", ExcelReaderUtil.cellData(cell));

        // 会籍变化日期
        cell = hssfRow.getCell(14);
        String memberChangeDate = ExcelReaderUtil.cellData(cell);
        try {
            memberChangeDate = StringRegularUtil.TimeRegular.validateWithFormatDays(memberChangeDate, "yyyyMMdd");
            if (StringUtils.isEmpty(memberChangeDate)) {
                memberChangeDate = DateUtil.getDays();
            }

        } catch (Exception ex) {
            memberChangeDate = DateUtil.getDays();
        }
        memberMap.put("member_change_date", memberChangeDate);

        // 会籍变化原因
        cell = hssfRow.getCell(15);
        memberMap.put("member_change_reason", ExcelReaderUtil.cellData(cell));

        // 政治面貌
        cell = hssfRow.getCell(16);
        memberMap.put("political_status", ExcelReaderUtil.cellData(cell));

        // 工作单位
        cell = hssfRow.getCell(17);
        memberMap.put("work_unit", ExcelReaderUtil.cellData(cell));

        // 籍贯
        cell = hssfRow.getCell(18);
        memberMap.put("native_place", ExcelReaderUtil.cellData(cell));

        // 是否农民工会员 LIB_IS_NOT
        Cell isFarmer = hssfRow.getCell(19);
        memberMap.put("is_farmer", ExcelReaderUtil.cellData(isFarmer));

        // 会员卡号改成定时任务自动生成，避免错误数据占用卡号
//        memberMap.put("station_card", MemberUtil.getMemberCardNo(sex, unionName, districtCode));

        if ("退休".equals(situation)) {
            //退休禁止入会
            Calendar birthdayCalendar = Calendar.getInstance();
            birthdayCalendar.setTime(DateUtil.parse(birthday, "yyyyMMdd"));
            Calendar now = Calendar.getInstance();

            //获取退休年纪
            int retireAge = 0;
            if ("男".equals(sex)) {
                retireAge = (int) getSetting("manRetireAge");
            } else if ("女".equals(sex)) {
                retireAge = (int) getSetting("womanRetireAge");
            }
            birthdayCalendar.add(Calendar.YEAR, retireAge);
            //判断是否已经退休
            if (birthdayCalendar.getTimeInMillis() < now.getTimeInMillis()) {
                errorMessage.append("-会员已退休，不能导入");
                ifSuccess = 0;
            }
        }
        if (ifSuccess != null && ifSuccess == 0) {
            memberMap.put("if_success", "0");
            memberMap.put("errors_message", errorMessage.toString());
        }
    }

    /**
     * 通过/不通过
     */
    public void updateMemDeptById(Integer id, String checkText, String status) {

        //更新dept_member_tranfer表
        DeptMemberTransfer deptMemberTransfer = deptMemberTransferMapper.selectById(id);
        deptMemberTransfer.setStatus(status);
        if (StringUtils.equals(status, "1")) {
            deptMemberTransfer.setTurnOutDeptId(deptMemberTransfer.getTransferDeptId());
            //更新dept_member表
            Integer memberId = deptMemberTransfer.getMemberId();
            DeptMember deptMember = memberMapper.selectById(memberId);
            deptMember.setDeptId(deptMemberTransfer.getTransferDeptId());
            memberMapper.updateById(deptMember);
        }
        deptMemberTransferMapper.updateById(deptMemberTransfer);


    }

    /**
     * 各市县产业系统工会实名制数据
     */
    private List<Map<String, Object>> getMemberRealNameData() {
        List<Map<String, Object>> list = new LinkedList<>();

        List<OrganizationRankData> orgs = blackboardDao.provincialUnionsTop10();
        Integer orgNumAll = 0;
        Integer memNumAll = 0;
        Integer farNumAll = 0;
        Integer verNumAll = 0;
        for(OrganizationRankData org : orgs){
            Map<String, Object> map  = new LinkedHashMap<>();
            map.put("orgId", org.getId());
            map.put("orgName", org.getName());

            List<Integer> realNameData = blackboardDao.getRealNameData(org.getId());
            if(realNameData.size()>0){
                map.put("orgNum",  realNameData.get(0));
                orgNumAll += realNameData.get(0);
            }
            if(realNameData.size()>1) {
                map.put("memNum", realNameData.get(1));
                memNumAll += realNameData.get(1);
            }
            if(realNameData.size()>2) {
                map.put("farNum", realNameData.get(2));
                farNumAll += realNameData.get(2);
            }
            if(realNameData.size()>3) {
                map.put("verNum", realNameData.get(3));
                verNumAll += realNameData.get(3);
            }
            if(map.containsKey("verNum") && map.containsKey("memNum")){
                int verNum = (int) map.getOrDefault("verNum", 0);
                int menNum = (int) map.getOrDefault("memNum", 0);
                double ret = 0.0;
                if(menNum != 0){
                    ret = verNum*100.0 / menNum;
                }
                map.put("perNum", new BigDecimal(ret).setScale(2, BigDecimal.ROUND_UP).doubleValue()+"%");
            }
            list.add(map);
        }

        Map<String, Object> allMap = new LinkedHashMap<>();
        allMap.put("orgId", 999999);
        allMap.put("orgName", "合计");
        allMap.put("orgNum", orgNumAll);
        allMap.put("memNum", memNumAll);
        allMap.put("farNum", farNumAll);
        allMap.put("verNum", verNumAll);
        double perNumAll = verNumAll*100.0 / memNumAll;
        allMap.put("perNum", new BigDecimal(perNumAll).setScale(2, BigDecimal.ROUND_UP).doubleValue()+"%");
        list.add(allMap);

        return list;
    }

    /**
     * 缓存 各市县产业系统工会实名制数据
     */
    public void cacheRealData() {
        List<Map<String, Object>> list = this.getMemberRealNameData();
        for(Iterator<Map<String, Object>> iterator = list.iterator();iterator.hasNext();){
            Map<String, Object> obj = iterator.next();
            int num = blackboardDao.getRealDataCount((Integer)obj.get("orgId"));
            if (num > 0){
                blackboardDao.updateRealData(obj);
                iterator.remove();
            }
        }
        if(list.size() > 0){
            blackboardDao.insertRealNameData(list);
        }
    }
}
