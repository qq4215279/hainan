package com.gobestsoft.core.util;

import com.gobestsoft.core.util.model.RowCellRangeAddressesModel;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Excel导入工具类
 *
 * @author Sally
 * @date 2018/05/23
 */
public class ExcelReaderUtil {

    /**
     * 过滤字符串中的特殊字符
     * 注：\n 回车; \t 水平制表符; \s 空格;  \r 换行
     *
     * @param str
     * @return
     */
    public static String replaceSpecial(String str) {
        String dest = "";
        if (str != null) {
            String regEx = "[\\s*|\t|\r|\n|`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_ -]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("").trim();
        }
        return dest;
    }

    /**
     * 过滤字符串中的空格、回车、换行符、制表符
     * 注：\n 回车; \t 水平制表符; \s 空格;  \r 换行
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("").trim();
        }
        return dest;
    }

    /**
     * 过滤字母和中文
     *
     * @param str
     * @return
     */
    public static String filter(String str) {
        String dest = "";
        if (str != null) {
            String regEx = "[a-zA-Z\\u4e00-\\u9fa5]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 判断单元格是否为空
     *
     * @param cell
     * @return
     */
    public static boolean CheckRowError(Cell cell) {
        if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否是空行
     *
     * @param row
     * @return
     */
    public static boolean isRowEmpty(Row row) {
        if (row == null) {
            return false;
        }
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }

    /**
     * DATE转yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    /**
     * 数字check
     *
     * @param str
     * @return
     */
    public static boolean checkNum(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 邮箱check
     *
     * @param str
     * @return
     */
    public static boolean checkEmail(String str) {
        Pattern pattern = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher isEmail = pattern.matcher(str);
        return isEmail.matches();

    }


    /**
     * 日期，电话，数量check
     * 过滤特殊字符及空格，换行符等过滤字母和数字
     *
     * @param checkChange
     * @return
     */
    public static String checkChange(Cell checkChange) {
        DecimalFormat df = new DecimalFormat("#");
        String checkChangeStr = "";
        switch (checkChange.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC://如果是数值类型
                if (HSSFDateUtil.isCellDateFormatted(checkChange)) { //判断是不是时间格式
                    checkChangeStr = formatDate(checkChange.getDateCellValue());
                } else {
                    checkChangeStr = filter(replaceSpecial(df.format(checkChange.getNumericCellValue())));
                }
                break;
            case Cell.CELL_TYPE_STRING://如果是字符串类型
                checkChangeStr = filter(replaceSpecial(checkChange.getStringCellValue()));
                break;
            case Cell.CELL_TYPE_FORMULA://公式型
                checkChangeStr = filter(replaceSpecial(checkChange.getCellFormula()));
                break;
            default:
                checkChangeStr = null;
                break;
        }
        return checkChangeStr;
    }

    /**
     * 数据各种格式处理
     * 并过滤字符串中的空格、回车、换行符、制表符
     *
     * @param data
     * @return
     */
    public static String cellData(Cell data) {
        if (data == null) {
            return "";
        }

        DecimalFormat df = new DecimalFormat("#");
        String dataStr = "";
        switch (data.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:// 数值类型
                if (HSSFDateUtil.isCellDateFormatted(data)) { //判断是不是时间格式
                    dataStr = formatDate(data.getDateCellValue());
                } else {
                    dataStr = replaceBlank(df.format(data.getNumericCellValue()));
                }
                break;
            case Cell.CELL_TYPE_STRING:// 字符串类型
                dataStr = replaceBlank(data.getStringCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:// 公式型
                dataStr = replaceBlank(data.getCellFormula());
                break;
            case Cell.CELL_TYPE_BOOLEAN:// 布尔型
                dataStr = data.getBooleanCellValue() + "";
                break;
            default:
                dataStr = "";
        }
        return dataStr;
    }

    /**
     * 判断传入行是否在合并单元格中
     *
     * @param cellRangeAddresses
     * @param row
     * @return
     */
    public static RowCellRangeAddressesModel rowInCellRangeAddress(List<CellRangeAddress> cellRangeAddresses, Row row) {
        if (cellRangeAddresses == null ||
                cellRangeAddresses.size() == 0 ||
                row == null) {
            return null;
        }
        for (CellRangeAddress address : cellRangeAddresses) {
            if (address.getFirstRow() == row.getRowNum()) {
                return new RowCellRangeAddressesModel(address.getFirstRow(), address.getLastRow(), address.getFirstColumn(), address.getLastColumn());
            }
        }
        return null;
    }

}
