package com.gobestsoft.api.modular.appbus.port;

import com.gobestsoft.api.modular.basic.BaseController;
import com.gobestsoft.api.modular.basic.BaseResult;
import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.MD5Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RequestMapping("/bus")
@RestController
public class BusPortController extends BaseController {

    @RequestMapping("/getSignatureCode")
    public BaseResult getSignatureCode(@RequestParam("channelId") String channelId, @RequestParam("requestKey") String requestKey){
        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        String reqNo= DateUtil.getAllTime();
        //String channelId="10006";
        String userId=getUserDto().getAuid();
        //String requestKey="123456789ABCDEFFEDCBA9876543210";
        parameters.put("reqNo", reqNo);
        parameters.put("channelId", channelId);
        parameters.put("userId",userId);
        //指定字符集UTF-8
        String characterEncoding = "UTF-8";
        Map<String,Object> mySign = createSign(characterEncoding,parameters,requestKey);
        return baseResult(200,"成功",mySign);
    }

    @RequestMapping("/getSkipParam")
    public BaseResult getParams(@RequestParam("targetId") String targetId){
        if(targetId.equals("1")){
            String path="pages/home/home";
            String requsetKey="123456789ABCDEFFEDCBA9876543210";
            String channelId="10006";
            String appId="gh_73b4ac52dbe8";

            SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
            String reqNo= DateUtil.getAllTime();
            String userId=getUserDto().getAuid();
            parameters.put("platformReqNo", reqNo);
            parameters.put("platformChannelId", channelId);
            parameters.put("platformUserId",userId);
            //指定字符集UTF-8
            String characterEncoding = "UTF-8";
            Map<String,Object> mySign = createSign(characterEncoding,parameters,requsetKey);

            String sbkey=mySign.get("sbkey").toString();
            String sign=mySign.get("sign").toString();

            Map<String,Object> map=new HashMap<>();
            map.put("path",path+"?"+sbkey+"platformSign="+sign);
            map.put("appId",appId);
            /**
             * miniProgramType 0 正式版
             * miniProgramType 1 开发版
             * miniProgramType 2 体验版
             */
            map.put("miniProgramType","0");
            return baseResult(200,"成功",map);
        }else{
            return null;
        }
    }
    /**
     * 签名值算法：获取签名值
     * @param characterEncoding
     * @param parameters
     * @param key
     * @return
     */
    private Map<String,Object> createSign(String characterEncoding, SortedMap<Object,Object> parameters, String key){
        StringBuffer sb = new StringBuffer();
        StringBuffer sbkey = new StringBuffer();
        //所有参与传参的参数按照accsii排序（升序）
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            //空值不传递，不参与签名组串
            if(null != v && !"".equals(v)) {
                sb.append(k + "=" + v + "&");
                sbkey.append(k + "=" + v + "&");
            }
        }
        sbkey.append("key="+key);
        //System.out.println(sbkey.toString());
        //System.out.println(sb.toString());
        //MD5加密,结果转换为大写字符
        String sign= MD5Util.MD5Encode(sbkey.toString(),characterEncoding).toUpperCase();
        Map<String,Object> map=new HashMap<>();
        map.put("sbkey",sb.toString());
        map.put("sign",sign);
        return map;
    }
}
