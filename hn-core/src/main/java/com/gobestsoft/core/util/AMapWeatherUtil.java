package com.gobestsoft.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 高德获取天气
 *
 * @author caoy
 */
public class AMapWeatherUtil {

    private static final String amap_key = "1391e5a9067789f540a87b4336d57d9c";

    public static void main(String[] args) {

        Map<String, String> weather;
//        try {
//            weather = AMapWeatherUtil.getWeatherByIp("116.226.47.172");
//        } catch (Exception e) {
//            weather = AMapWeatherUtil.getWeatherByCityCode("110000");
//        }

        weather = getWeatherByCityCode("110000");
        System.out.println(weather);
    }

    /**
     * 根据IP获取天气情况
     *
     * @param ip
     * @return
     */
    public static Map<String, String> getWeatherByIp(String ip) {
        Map<String, String> result = getCityPositionByIp(ip);
        Map<String, String> weather = getWeatherByCityCode(result.get("adcode"));
        return weather;
    }

    /**
     * 获取实时天气<br>
     * 方 法 名： getWeather <br>
     *
     * @param cityCode 城市编码
     * @throws IOException,NullPointerException
     */
    public static Map<String, String> getWeather(String cityCode) throws IOException, NullPointerException {
        // 连接API
        URL url = new URL("http://restapi.amap.com/v3/weather/weatherInfo?extensions=all&key=" + amap_key + "&city=" + cityCode);
        URLConnection connectionData = url.openConnection();
        connectionData.setConnectTimeout(1000);
        Map<String, String> map = new HashMap<String, String>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String datas = sb.toString();
            JSONObject json = JSONObject.parseObject(datas);

            String forecasts = json.get("forecasts").toString();
            JSONObject forecast = JSONArray.parseArray(forecasts).getJSONObject(0);
            map.put("city", (String) forecast.get("city"));
            map.put("reportTime", (String) forecast.get("reporttime"));
            String casts = forecast.get("casts").toString();
            JSONObject jsonCasts = JSONArray.parseArray(casts).getJSONObject(0);

            Date nightTime = DateUtil.parseTime(jsonCasts.get("date").toString() + " 18:00:00");
            Date nowTime = new Date();
            if (nowTime.compareTo(nightTime) < 0) {
                map.put("weather", (String) jsonCasts.get("dayweather"));
            } else {
                map.put("weather", (String) jsonCasts.get("nightweather"));
            }
            map.put("icon", WebSiteUtil.getBrowseUrl(WEATHER_MAP.get(map.get("weather").toString())));

            map.put("temp1", (String) jsonCasts.get("nighttemp"));
            map.put("temp2", (String) jsonCasts.get("daytemp"));

        } catch (SocketTimeoutException e) {
            System.out.println("连接超时");
        } catch (FileNotFoundException e) {
            System.out.println("加载文件出错");
        }
        return map;
    }

    /**
     * 根据cityCode获取城市天气
     *
     * @param cityCode
     * @return
     */
    public static Map<String, String> getWeatherByCityCode(String cityCode) {
        String url = "http://restapi.amap.com/v3/weather/weatherInfo?extensions=all&key=" + amap_key + "&city=" + cityCode;
        JSONObject json = JSONObject.parseObject(HttpUtil.sendGet(url));


        Map<String, String> result = correctWeather(json);
        return result;
    }


    /**
     * 修改天气模板
     *
     * @return
     */
    private static Map<String, String> correctWeather(JSONObject json) {
        Map<String, String> result = new HashMap<>();
        String forecasts = json.get("forecasts").toString();
        JSONObject forecast = JSONArray.parseArray(forecasts).getJSONObject(0);
        result.put("city", (String) forecast.get("city"));
        result.put("reportTime", (String) forecast.get("reporttime"));
        String casts = forecast.get("casts").toString();
        JSONObject jsonCasts = JSONArray.parseArray(casts).getJSONObject(0);

        Date nightTime = DateUtil.parseTime(jsonCasts.get("date").toString() + " 18:00:00");
        Date nowTime = new Date();
        if (nowTime.compareTo(nightTime) < 0) {
            result.put("weather", (String) jsonCasts.get("dayweather"));
        } else {
            result.put("weather", (String) jsonCasts.get("nightweather"));
        }
        result.put("icon", WebSiteUtil.getBrowseUrl(WEATHER_MAP.get(result.get("weather").toString())));
        result.put("temp1", (String) jsonCasts.get("nighttemp"));
        result.put("temp2", (String) jsonCasts.get("daytemp"));
        return result;
    }

    /**
     * 获取实时天气<br>
     * 方 法 名： getWeather <br>
     *
     * @param cityCode 城市编码
     * @throws IOException,NullPointerException
     */
    public static Map<String, Object> getWeatherV2(String cityCode) throws IOException, NullPointerException {
        // 连接API
        URL url = new URL("https://restapi.amap.com/v3/weather/weatherInfo?key=" + amap_key + "&city=" + cityCode);
        URLConnection connectionData = url.openConnection();
        connectionData.setConnectTimeout(1000);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(connectionData.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String datas = sb.toString();
            JSONObject result = JSONObject.parseObject(datas);

            if ("1".equals(result.getString("status")) && "10000".equals(result.getString("infocode"))) {
                JSONArray lives = result.getJSONArray("lives");
                JSONObject l = lives.getJSONObject(0);
                map.put("city", l.get("city"));
                map.put("temp2", l.get("temperature") + "°C");
                map.put("temp1", l.get("temperature") + "°C");
                map.put("icon", WebSiteUtil.getBrowseUrl(WEATHER_MAP.get(l.get("weather"))));
                map.put("weather", l.get("weather"));
            }
        } catch (SocketTimeoutException e) {
            System.out.println("连接超时");
        } catch (FileNotFoundException e) {
            System.out.println("加载文件出错");
        }
        return map;
    }

    /**
     * 根据IP获取城市定位
     *
     * @param ip
     * @return
     */
    public static Map<String, String> getCityPositionByIp(String ip) {
        String url = "http://restapi.amap.com/v3/ip?ip=" + ip + "&output=jsons&key=" + amap_key;
        Map<String, String> result = (Map<String, String>) JSONObject.parseObject(HttpUtil.sendGet(url), HashMap.class);
        return result;
    }


    @SuppressWarnings({"unchecked", "rawtypes", "serial"})
    private final static Map<String, String> WEATHER_MAP = new HashMap() {
        {
            put("晴", "/weatherIcon/00.png");
            put("多云", "/weatherIcon/01.png");
            put("阴", "/weatherIcon/02.png");
            put("阵雨", "/weatherIcon/03.png");
            put("雷阵雨", "/weatherIcon/03.png");
            put("雷阵雨并伴有冰雹", "/weatherIcon/03.png");
            put("雨夹雪", "/weatherIcon/06.png");
            put("小雨", "/weatherIcon/07.png");
            put("中雨", "/weatherIcon/08.png");
            put("大雨", "/weatherIcon/09.png");
            put("暴雨", "/weatherIcon/10.png");
            put("大暴雨", "/weatherIcon/11.png");
            put("特大暴雨", "/weatherIcon/11.png");
            put("阵雪", "/weatherIcon/15.png");
            put("小雪", "/weatherIcon/14.png");
            put("中雪", "/weatherIcon/15.png");
            put("大雪", "/weatherIcon/16.png");
            put("暴雪", "/weatherIcon/17.png");
            put("雾", "/weatherIcon/18.png");
            put("冻雨", "/weatherIcon/19.png");
            put("沙尘暴", "/weatherIcon/20.png");
            put("小雨-中雨", "/weatherIcon/08.png");
            put("中雨-大雨", "/weatherIcon/09.png");
            put("大雨-暴雨", "/weatherIcon/10.png");
            put("暴雨-大暴雨", "/weatherIcon/11.png");
            put("大暴雨-特大暴雨", "/weatherIcon/11.png");
            put("小雪-中雪", "/weatherIcon/14.png");
            put("中雪-大雪", "/weatherIcon/15.png");
            put("大雪-暴雪", "/weatherIcon/16.png");
            put("浮尘", "/weatherIcon/20.png");
            put("扬沙", "/weatherIcon/20.png");
            put("强沙尘暴", "/weatherIcon/20.png");
            put("飑", "/weatherIcon/55.png");
            put("龙卷风", "/weatherIcon/33.png");
            put("弱高吹雪", "/weatherIcon/19.png");
            put("轻雾", "/weatherIcon/53.png");
            put("霾", "/weatherIcon/53.png");
        }
    };




}
