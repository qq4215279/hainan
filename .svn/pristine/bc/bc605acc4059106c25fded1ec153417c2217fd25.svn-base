package com.gobestsoft.api.modular.configurer;

import com.gobestsoft.api.config.properties.ApiProperties;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.MD5Util;
import com.gobestsoft.core.util.NumUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by gutongwei on 2018/1/9.
 */
@Slf4j
public class SignInterceptor extends BaseHandlerInterceptor {

    private long time_out = 1000 * 60 * 5;


    private final String LOG_TAG_NAME = "接口验证：";

    @Autowired
    private ApiProperties apiProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!apiProperties.isOpenSign()) return true;
        if (super.preHandle(request, response, handler)) {
            log.debug(LOG_TAG_NAME + "调用ServletPath：" + request.getServletPath());
            log.debug(LOG_TAG_NAME + "Header:signStr:" + request.getHeader("signStr"));
            if (StringUtils.isEmpty(request.getHeader("sign"))) {
                writeResultCode(response, BaseResult.ResultCode.ERROR403);
                log.error(LOG_TAG_NAME + "sign未传递");
                return false;
            }
            if (StringUtils.isEmpty(request.getHeader("timestamp")) || !NumUtil.isNumeric(request.getHeader
                    ("timestamp"))) {
                writeResultCode(response, BaseResult.ResultCode.ERROR401);
                log.error(LOG_TAG_NAME + "timestamp未传递");
                return false;
            }
            Date timestamp = DateUtil.parse(request.getHeader("timestamp"), "yyyyMMddHHmmss");
            long systemTimeMillis = Calendar.getInstance().getTimeInMillis();
            long timestampMills = timestamp.getTime();
            if (systemTimeMillis - time_out > timestampMills) {
                writeResultCode(response, BaseResult.ResultCode.ERROR401);
                log.error(LOG_TAG_NAME + "传递timestamp:" + timestampMills + "与服务器timestamp：" + systemTimeMillis + "相差超过" + time_out
                        + "毫秒");
                return false;
            }
            SortedMap<String, String> parameters = getSorted(request);
            if (validateSign(parameters, request.getHeader("sign"))) {
                log.info(LOG_TAG_NAME + "验证成功");
                return true;
            }
            writeResultCode(response, BaseResult.ResultCode.ERROR402);
            log.error(LOG_TAG_NAME + "验证失败，服务器与调用值的2个签名值不同");
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    /**
     * 将Request过来的参数添加进入SortedMap
     *
     * @param request
     * @return
     */
    public SortedMap<String, String> getSorted(HttpServletRequest request) {
        SortedMap<String, String> parameters = new TreeMap<>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            parameters.put(name, value);
        }
        parameters.put("timestamp", request.getHeader("timestamp"));
        return parameters;
    }

    /**
     * 验证签名是否正确
     *
     * @param parameters
     * @param sign
     * @return
     */
    private boolean validateSign(SortedMap<String, String> parameters, String sign) {
        if (StringUtils.isEmpty(sign)) return false;
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entrySet = parameters.entrySet();
        Iterator<Map.Entry<String, String>> it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            sb.append(entry.getKey() + "=" + entry.getValue());
        }
        String waitValidateStr = getWaitValidateStr(sb);
        log.debug(LOG_TAG_NAME + "调用者传递签名值：" + sign);
        if (waitValidateStr.equals(sign)) return true;
        return false;
    }

    /**
     * sign等于三步最后所得
     * <p>
     * 第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），
     * 使用URL键值对的格式（即key1=value1key2=value2…），最后面添加'signKey'拼接成字符串stringA。
     * 第二步，将第一步所得的字符串经过MD5加密
     * 第三步，将第二布所得的字符串转换大写
     *
     * @param sb
     * @return
     */
    private String getWaitValidateStr(StringBuilder sb) {
        log.debug(LOG_TAG_NAME + "接口参数拼接值：" + sb.toString());
        String appendKey = sb.toString() + apiProperties.getSalt();
        log.debug(LOG_TAG_NAME + "接口参数拼接值添加Salt后：" + appendKey);
        String md5Str = null;
        try {
            md5Str = MD5Util.encryptByUtf8(appendKey);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.debug(LOG_TAG_NAME + "接口参数拼接值添加Salt后经过MD5加密后：" + md5Str);
        String upperCaseStr = md5Str.toUpperCase();
        log.debug(LOG_TAG_NAME + "服务器生成调用接口的签名：" + upperCaseStr);
        return upperCaseStr;
    }
}
