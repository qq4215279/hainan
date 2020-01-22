package com.gobestsoft.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordUtil {

    public static final String DOC_SUFFIX = ".doc";
    public static final String DOCX_SUFFIX = ".docx";


    /**
     * 读取word模板 生成到目录
     * @param filePath 模板路径
     * @param fileUploadPath 目标路径
     * @param fileName 文件名(有后缀)
     * @param map 模板替换值
     */
    public static void saveWord(String filePath, String fileUploadPath, String fileName, Map<String, Object> map) {
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            is = new FileInputStream(new File(filePath));
            if(fileName.endsWith(DOC_SUFFIX)) {
                HWPFDocument doc = new HWPFDocument(is);
                replaceDoc(map, doc);
                if (!fileUploadPath.endsWith(File.separator))
                    fileUploadPath += File.separator;
                os = new FileOutputStream(fileUploadPath + fileName, true);
                doc.write(ostream);
            }else{
                XWPFDocument doc = new XWPFDocument(is);
                replaceDocxText(doc, map);
                replaceDocxTable(doc, map);
                os = new FileOutputStream(fileUploadPath+fileName);
                doc.write(os);
            }
            os.write(ostream.toByteArray());
        } catch (Exception e) {

        } finally {
            close(is);
            close(os);
        }
    }


    /**
     * 读取word模板 下载到前端
     * @param filePath 模板路径
     * @param fileName 文件名(有后缀)
     * @param map 模板替换值
     * @param response
     * @param request
     */
    public static void downloadWord(String filePath, String fileName, Map<String, Object> map, HttpServletResponse response, HttpServletRequest request) {
        FileInputStream in;
        OutputStream os = null;
        try {
            //设置文件名字符集
            String userAgent = request.getHeader("USER-AGENT");
            if (StringUtils.contains(userAgent, "Mozilla")) {
                fileName = new String(fileName.getBytes(), "ISO8859-1");
            } else {
                fileName = URLEncoder.encode(fileName, "utf8");
            }
            //输出word内容文件流，提供下载
            response.reset();
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.addHeader("Access-Control-Allow-Origin", "*");
            os = response.getOutputStream();
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();

            in = new FileInputStream(new File(filePath));
            if(fileName.endsWith(DOC_SUFFIX)) {
                HWPFDocument doc = new HWPFDocument(in);
                replaceDoc(map, doc);
                doc.write(ostream);
            }else{
                XWPFDocument doc = new XWPFDocument(in);
                replaceDocxText(doc, map);
                replaceDocxTable(doc, map);
                doc.write(ostream);
            }

            os.write(ostream.toByteArray());
            os.flush();
        } catch (Exception e) {

        } finally {
            close(os);
        }
    }


    /**
     * 关闭输出流
     * @param os
     */
    private static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {

            }
        }
    }

    /**
     * 关闭输入流
     * @param is
     */
    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {

            }
        }
    }

    /**
     * 空值或者 null 字符串 转 ""
     * @param obj
     * @return
     */
    private static String null2String(Object obj) {
        String valueText = "";
        if (obj != null && !"null".equals(String.valueOf(obj).toLowerCase()))
            valueText = obj.toString();
        return valueText;
    }



    /*--doc格式方法--*/
    private static void replaceDoc(Map<String, Object> map, HWPFDocument hdt) {
        Range range = hdt.getRange();
        //替换文本内容
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            range.replaceText("$" + entry.getKey() + "$", null2String(entry.getValue()));
        }
    }



    /*--docx格式方法--*/
    /**
     * 替换段落变量
     * @param doc 要替换的文档
     * @param params 参数
     */
    private static void replaceDocxText(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params);
        }
    }

    /**
     * 替换表格变量
     * @param doc 要替换的表格
     * @param params 参数
     */
    private static void replaceDocxTable(XWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params);
                    }
                }
            }
        }
    }

    /**
     * 替换逻辑
     * @param para 要替换的段落-
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i=0; i<runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                if (matcher.find()) {
                    while ((matcher = matcher(runText)).find()) {
                        Object obj = params.getOrDefault(matcher.group(1),"");
                        runText = matcher.replaceFirst(null2String(obj));
                    }
                    //不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    para.insertNewRun(i).setText(runText);
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     * @param str
     * @return
     */
    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str);
    }
}
