package com.gobestsoft.mamage.moudle.weak.service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gobestsoft.common.modular.system.mapper.DeptMapper;
import com.gobestsoft.common.modular.system.mapper.UserMapper;
import com.gobestsoft.common.modular.system.model.Dept;
import com.gobestsoft.common.modular.system.model.User;
import com.gobestsoft.core.util.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 弱口令管理
 */
@Service
public class WeakService {

    private static final String WEAK_FILE = "weak-word.json";

    @Resource
    private UserMapper userMapper;
    @Resource
    private DeptMapper deptMapper;

    public void getWeakInfo(HttpServletResponse response) {

        try {
            JSONArray weakList = getWeakList();

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            HSSFRow head = sheet.createRow(0);
            head.createCell(0).setCellValue("账户名");
            head.createCell(1).setCellValue("当前密码");
            head.createCell(2).setCellValue("所属工会");
            head.createCell(3).setCellValue("上级工会");
            head.createCell(4).setCellValue("上级工会");
            head.createCell(5).setCellValue("上级工会");
            head.createCell(6).setCellValue("上级工会");
            int rowNum = 1;

            List<User> users = userMapper.selectByMap(null);
            for (User user : users) {

                String password = user.getPassword();
                String salt = user.getSalt();
                for (int i = 0; i < weakList.size(); i++) {
                    String weakWord = weakList.getString(i);
                    String weakMd5 = MD5Util.encrypt(weakWord + salt);
                    if (weakMd5.equals(password)) {
                        HSSFRow row = sheet.createRow(rowNum);
                        row.createCell(0).setCellValue(user.getAccount());// 账户
                        row.createCell(1).setCellValue(weakWord);// 密码
                        rowNum++;

                        Dept dept = deptMapper.selectById(user.getDeptid());

                        if (ToolUtil.isNotEmpty(dept)) {
                            row.createCell(2).setCellValue(dept.getSimplename());// 所属工会
                            String pids = dept.getPids();
                            if (ToolUtil.isNotEmpty(pids)) {
                                Wrapper<Dept> wrapper = new EntityWrapper<>();
                                wrapper.in("id", pids).orderBy("id", false);
                                List<Dept> parents = deptMapper.selectList(wrapper);
                                for (int j = 0; j < parents.size(); j++) {
                                    Dept pDept = parents.get(j);
                                    if (ToolUtil.isNotEmpty(pDept)) {
                                        row.createCell(j + 3).setCellValue(pDept.getSimplename());// 所有上级工会
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
            ExcelExportUtil.exportExcel(response, "弱口令账户", "", wb);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONArray getWeakList() throws FileNotFoundException {
        File configFile = ResourceUtils.getFile("classpath:static" + File.separator + WEAK_FILE);
        String weakWords = FileUtil.getFileContent(configFile);
        return JSONArray.parseArray(weakWords);
    }

}
