package com.gobestsoft.core.moblie;

import com.gobestsoft.core.util.HttpUtil;
import com.gobestsoft.core.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信平台工具
 * <p>
 * 此短信为[希奥信息]短信平台
 * 文档地址：http://www.sioo.com.cn/APIdoc/smssend.html
 * 后台管理地址：http://www.10690221.com/
 * 企业代码：hnsz
 * 用户ID：81317
 * 密 码：8HSW9rR#
 *
 * @author Administrator
 */
public class SMSUtil {

    private final static Logger logger = LoggerFactory.getLogger(SMSUtil.class);

    private static final String AUTH_CODE_TEMPLATE = "尊敬的用户，您正在进行对海南工会云的操作,验证码为{0},请不要将验证码告诉任何人哦。回复T退订";

    /**
     * 发送地址
     */
    private static final String SEND_URL = "http://sms.10690221.com:9011/hy/";

    /**
     * 用户ID
     */
    private static final String SMS_UID = "81317";
    /**
     * 企业代码
     */
    private static final String SMS_COMPANY_CDOE = "hnsz";
    /**
     * 帐号密码
     */
    private static final String SMS_PASSWORD = "8HSW9rR#";

    /**
     * 转译类型
     */
    private static final String ENCODE = "utf-8";

    /**
     * 发送验证嘛
     *
     * @param mobile
     * @param code
     * @return
     */
    public static boolean sendAuthCode(String mobile, String code) {
        String message = MessageFormat.format(AUTH_CODE_TEMPLATE, code);
        return sendMobileMessage(mobile, message);
    }


    /**
     * 发送手机短信
     *
     * @param mobile
     * @param message
     * @return
     */
    public static boolean sendSMS(String mobile, String message) {
        return sendMobileMessage(mobile, message);
    }

    /**
     * 将字符串使用urlEncode
     *
     * @param msg
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String encodeMessage(String msg) throws UnsupportedEncodingException {
        if (msg.indexOf("回复T退订") < 0) {
            if (msg.endsWith("。") || msg.endsWith(".")) {
                msg += "回复T退订";
            } else {
                msg += "。回复T退订";
            }
        }
        return URLEncoder.encode(msg, ENCODE);
    }

    /**
     * 发送短信
     *
     * @param mobile  手机号
     * @param message 发送消息
     * @return
     */
    private static boolean sendMobileMessage(String mobile, String message) {
        logger.info("短信发送,手机号：" + mobile + ",内容:" + message);
        try {
            message = encodeMessage(message);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("uid", SMS_UID);
        parameters.put("auth", MD5Util.encrypt(SMS_COMPANY_CDOE + SMS_PASSWORD));
        parameters.put("mobile", mobile);
        parameters.put("msg", message);
        parameters.put("expid", "0");
        parameters.put("encode", ENCODE);
        String result = HttpUtil.sendPost(SEND_URL, parameters);
        logger.info("短信发送回复：" + result);
        if (StringUtils.isEmpty(result)) {
            return false;
        }
        try {
            if ("0".equals(result.split(",")[0])) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    public static void main(String[] args) {
        sendAuthCode("15901847435", "223451");
    }

}
