package com.gobestsoft.core.util;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * 用于导出excel
 */
@Data
public class ExcelExportUtil {


    /**
     * 所有要查询字段的列的英文名和中文名,需要与字段一一对应，默认用冒号连接：
     */
    private String excelHeader [];
    /**
     * 导出文件名
     */
    private String fileName;


    /**
     *
     * @param excelHeader excel表头的英文和中文对应，默认使用冒号分割
     * @param fileName 导出文件的文件名,不要加后缀名
     */
    public ExcelExportUtil(String excelHeader[], String fileName) {
        this.setExcelHeader(excelHeader);
        this.setFileName(fileName);
    }

    /**
     *
     * @param response http响应
     * @param dataList 查询出的对象集合
     * @param split    excel头部中文名与字段的连接符，默认是冒号:
     * @return
     * @throws Exception
     */
    public  XSSFWorkbook export(HttpServletRequest request, HttpServletResponse response, List dataList, String split) throws Exception {
        // 设置请求 
        if(StringUtils.isEmpty(split)){
            split=":";
        }
        if(fileName==null||fileName.trim().length()==0){
            fileName="未定义文件名";
        }
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String agent = (String)request.getHeader("USER-AGENT");
        XSSFWorkbook wb = null;
        wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(fileName);
        if(agent != null && agent.indexOf("Firefox") != -1) {
// firefox
            fileName = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
        }else{
// others
            fileName = URLEncoder.encode(fileName, "UTF-8");
        }
        response.setContentType("application/application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName+".xlsx");
        // 创建一个Workbook，对应一个Excel文件 

        // 设置标题样式 
        XSSFCellStyle titleStyle = wb.createCellStyle();
        // 设置字体样式 
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 15);
        // 字体高度 
        titleFont.setFontName("黑体");
        // 字体样式 
        titleStyle.setFont(titleFont);
        // 在Workbook中添加一个sheet,对应Excel文件中的sheet 

        // 标题数组 
        String[] titleArray = new String[excelHeader.length];
        // 字段名数组 
        String[] fieldArray = new String[excelHeader.length];
        for (int i = 0; i < excelHeader.length; i++) {
            String[] tempArray = excelHeader[i].split(split);
            // 临时数组 分割 `
            if(excelHeader==null||excelHeader.length==0||tempArray==null||tempArray.length!=2){
                throw new Exception("请检查excel头部设置excelHeader");
            }

            fieldArray[i] = tempArray[0];
            titleArray[i] = tempArray[1];
        }
        // 在sheet中添加标题行 
        XSSFRow row = sheet.createRow((int) 0);
        // 行数从0开始 
        XSSFCell sequenceCell = row.createCell(0);
        // cell列 从0开始 第一列添加序号 
        sequenceCell.setCellValue("序号");
        sequenceCell.setCellStyle(titleStyle);
        // 为标题行赋值 
        for (int i = 0; i < titleArray.length; i++) {
            XSSFCell titleCell = row.createCell(i + 1);
            // 0号位被序号占用，所以需+1   
            titleCell.setCellValue(titleArray[i]);
            titleCell.setCellStyle(titleStyle);
            sheet.autoSizeColumn(i + 1);
            // 0号位被序号占用，所以需+1 
        }
        // 数据样式 因为标题和数据样式不同 需要分开设置 不然会覆盖 
        XSSFCellStyle dataStyle = wb.createCellStyle();
        // 设置数据字体 
        Font dataFont = wb.createFont();
        dataFont.setFontHeightInPoints((short) 12);
        // 字体高度 
        dataFont.setFontName("宋体");
        // 字体 
        dataStyle.setFont(dataFont);
        // 遍历集合数据，产生数据行

        int index = 0;
        for (Object t:dataList){
            index++;
            // 0号位被占用 所以+1   
            row = sheet.createRow(index);
            // 为序号赋值   
            XSSFCell sequenceCellValue = row.createCell(0);
            // 序号值永远是第0列   
            sequenceCellValue.setCellValue(index);
            sequenceCellValue.setCellStyle(dataStyle);
//            sheet.autoSizeColumn(0);
            // 利用反射，根据传过来的字段名、数组，动态调用对应的getXxx()方法得到属性值   
            for (int j = 0; j < fieldArray.length; j++) {
                XSSFCell dataCell  = row.createCell(j+ 1);
                dataCell.setCellStyle(dataStyle);
                String fieldName = fieldArray[j];

                if(t instanceof Map){
                    Map m=(Map) t;
                    dataCell.setCellValue(m.get(fieldName)==null?"":m.get(fieldName).toString());
                }else{
                    // 取得对应getXxx()方法   
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    // 泛型为Object以及所有Object的子类   
                    Class<? extends Object> tCls = t.getClass();
                    // 通过方法名得到对应的方法
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});

                    Object value = getMethod.invoke(t, new Object[]{});
                    if (value != null) {
                        // 为当前列赋值   
                        dataCell.setCellValue(value.toString());
                    }
                }



            }
        }
        OutputStream outputStream = response.getOutputStream();
        // 打开流 

        wb.write(outputStream);
        // HSSFWorkbook写入流 

        outputStream.flush();
        wb.close();
        // HSSFWorkbook关闭 
        // 刷新流 
        outputStream.close();
        // 关闭流 
        return wb;

    }

    /**
     * 导出
     */
    public static void exportExcel(HttpServletResponse response, String fileName, String now, HSSFWorkbook wb) throws IOException {
        if(ToolUtil.isNotEmpty(now)){
            fileName += "_" + now;
        }
        fileName += ".xls";

        response.reset();
        response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");

        wb.write(response.getOutputStream());
    }

}
