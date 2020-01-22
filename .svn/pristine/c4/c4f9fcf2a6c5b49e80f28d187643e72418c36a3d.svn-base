package com.gobestsoft.core.util;


import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * 字符串正则验证
 *
 * @author root
 */
public class StringRegularUtil {
    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^(1)\\d{10}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "^(?:https?://)?[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/?&=#%:]*$";

    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";


    public static boolean auth(String regex, String str) {
        return Pattern.matches(regex, str);
    }

    /**
     * 是否是URL
     *
     * @param url
     * @return
     */
    public static boolean isURL(String url) {
        if (StringUtils.isEmpty(url)) return false;
        if (url.startsWith("http://") || url.startsWith("https://")) {
            return true;
        }
        return false;
    }

    /**
     * 身份证验证
     *
     * @param num
     * @return
     */
    public static boolean isIdentificationCard(String num) {
        if (!num.matches(REGEX_ID_CARD)) {
            return false;
        }
        return true;
    }

    /**
     * 是否有效的图片
     *
     * @param imageUrl
     * @return
     */
    public static boolean isEffectiveImage(String imageUrl) {
        if (imageUrl.startsWith("http://") || imageUrl.startsWith("https://") || imageUrl.startsWith("ftp://")) {
            return true;
        }
        return false;
    }

    /**
     * 手机号验证
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) return false;
        return mobile.matches(REGEX_MOBILE);
    }


    public static class TimeRegular {
        /**
         * 日期1
         */
        public static final String DATE1 = "^\\d{4}/\\d{2}/\\d{2}$";

        /**
         * 日期2
         */
        public static final String DATE2 = "^\\d{4}-\\d{2}-\\d{2}$";

        /**
         * 验证日期
         *
         * @param date
         * @return
         */
        public static String validateWithFormatDays(String date, String format) {
            try {
                if (date.matches(StringRegularUtil.TimeRegular.DATE1)) {
                    return DateUtil.parseAndFormat(date, "yyyy/MM/dd", format);
                } else if (date.matches(StringRegularUtil.TimeRegular.DATE2)) {
                    return DateUtil.parseAndFormat(date, "yyyy-MM-dd", format);
                }
            } catch (Exception ex) {
                return null;
            }
            return null;
        }


    }
}
