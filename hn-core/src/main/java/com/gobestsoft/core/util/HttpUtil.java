package com.gobestsoft.core.util;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static final CloseableHttpClient httpclient = HttpClients.createDefault();

    /**
     * 发送HttpGet请求
     *
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        return sendGet(url, null);
    }


    /**
     * 发送HttpGet请求
     *
     * @param url
     * @return
     */
    public static String sendGet(String url, Map<String, String> headersParam) {
        HttpGet httpget = new HttpGet(url);
        addHeaders(httpget, headersParam);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        String result = null;
        try {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 发送HttpPost请求，参数为map
     *
     * @param url
     * @param map
     * @return
     */
    public static String sendPost(String url, Map<String, String> map) {
        return sendPost(url, map, null);
    }

    /**
     * 发送HttpPost请求，参数为map
     *
     * @param url
     * @param map
     * @return
     */
    public static String sendPost(String url, Map<String, String> map, Map<String, String> headersParam) {
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        if (map != null) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
        HttpPost httppost = new HttpPost(url);
        addHeaders(httppost, headersParam);
        httppost.setEntity(entity);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpclient.execute(httppost);
            HttpEntity entity1 = response.getEntity();
            result = EntityUtils.toString(entity1);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 添加header
     *
     * @param httpRequestBase
     * @param headersParam
     */
    private static void addHeaders(HttpRequestBase httpRequestBase, Map<String, String> headersParam) {
        if (headersParam != null && headersParam.size() > 0) {
            Header[] headers = new Header[headersParam.size()];
            int index = 0;
            Iterator<Map.Entry<String, String>> iterator = headersParam.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> h = iterator.next();
                headers[index] = new BasicHeader(h.getKey(), h.getValue());
                index++;
            }
            httpRequestBase.setHeaders(headers);
        }
    }


    /**
     * 获取网络返回资源
     *
     * @param target
     * @return
     */
    public static InputStream getSomeThing(String target) {
        try {
            //new一个URL对象
            URL url = new URL(target);
            //打开链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            return inStream;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}