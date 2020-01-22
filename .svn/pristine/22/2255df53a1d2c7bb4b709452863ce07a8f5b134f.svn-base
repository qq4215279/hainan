package com.gobestsoft.mamage.moudle.weak.controller;

import com.gobestsoft.common.modular.dept.dao.MemberDao;
import com.gobestsoft.common.modular.system.dao.DeptDao;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.core.util.ExcelExportUtil;
import com.gobestsoft.core.util.ToolUtil;
import com.gobestsoft.mamage.basic.BaseController;
import com.gobestsoft.mamage.basic.tips.Tip;
import com.gobestsoft.mamage.moudle.weak.service.WeakService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/release")
public class ReleaseController extends BaseController {


    @Resource
    MemberDao memberDao;

    @RequestMapping("update/member/count")
    @ResponseBody
    public String updateMemberCount(){
        //更新各组织会员人数
        memberDao.updateMemberCountTable1();
        memberDao.updateBindMemberCountTable1();
        memberDao.updateMemberCountTable2();
        return "updateMemberCount over";
    }

    /**
     * 校验用户名是否存在
     */
    @RequestMapping(value = "/checkUsername")
    public Tip loginVail() {

        String username = getPara("username");
        int code = 500;
        int data = 0;
        String message = "用户名不存在";
        if (ToolUtil.isNotEmpty(username)) {
            data = userService.queryUsername(username);
            if (data > 0) {
                code = 200;
                message = "用户名正常";
            }
        }
        return new Tip(code, message, data);

    }


    /**
     * 弱口令管理
     */
    @Resource
    private WeakService weakService;

    @GetMapping("/weak/get")
    public void getWeakInfo(HttpServletResponse response) {
        weakService.getWeakInfo(response);
    }


    /**
     * 组织信息导出 会员数 绑定会员数
     */
    @Resource
    private DeptDao deptDao;

    @GetMapping("/org/member/get")
    public void getOrgMemberNum(String orgName, HttpServletResponse response) {

        Dept dept = deptDao.selectDeptsByName(orgName);

        try {
            if (ToolUtil.isNotEmpty(dept)) {
                Map<String, Object> deptMap = new HashMap<>();
                deptMap.put("simplaname", dept.getSimplename());
                deptMap.put("child", getNumsByPid(dept.getId()));

                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("总览");
                // 设置列宽
                for (int i = 0; i < 20; i++) {
                    sheet.setColumnWidth(i, 20 * 256);
                }
                sheet.createRow(0).createCell(0).setCellValue(orgName);
                // 数据填充
                setCellValue(sheet, deptMap);

                ExcelExportUtil.exportExcel(response, dept.getSimplename()+"_数据树", null, wb);
            } else {
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("未查到数据");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setCellValue(HSSFSheet sheet, Map<String, Object> node) {
        LinkedList<Map<String, Object>> childNodes = (LinkedList<Map<String, Object>>) node.get("child");
        int lastCellNum = sheet.getRow(sheet.getLastRowNum()).getLastCellNum();
        for (Map<String, Object> childNode : childNodes) {
            HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

            String value = childNode.get("simplename") + "(会员数: " + childNode.get("cnt") + ")" + "(绑定会员数: " + childNode.get("bnt") + ")";
            row.createCell(lastCellNum).setCellValue(value);

            this.setCellValue(sheet, childNode);
        }
    }

    private LinkedList<Map<String, Object>> getNumsByPid(Integer pid) {
        LinkedList<Map<String, Object>> childs = deptDao.selectByPid(pid);
        for (Map child : childs) {
            List<Map<String, Object>> sons = getNumsByPid((Integer) child.get("dept_id"));
            child.put("child", sons);
        }
        return childs;
    }
}
