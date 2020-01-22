package com.gobestsoft.core.util.ffmpeg;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ffmpeg 工具类
 */
public class FfmpegUtil {


    /**
     * 获取视频总时间
     *
     * @param filePath 媒体文件地址
     * @return
     */
    public static int getMediaDuration(String filePath) {
        List<String> commands = new java.util.ArrayList<>();
        commands.add("ffmpeg");
        commands.add("-i");
        commands.add(filePath);
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            final Process p = builder.start();

            //从输入流中读取视频信息
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            System.out.println(sb);

            //从视频信息中解析时长
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            Pattern pattern = Pattern.compile(regexDuration);
            Matcher m = pattern.matcher(sb.toString());
            if (m.find()) {
                int time = getPlaySeconds(m.group(1));
                System.out.println(filePath + ",视频时长：" + time + ", 开始时间：" + m.group(2) + ",比特率：" + m.group(3) + "kb/s");
                return time;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取视频播放时长
     *
     * @param duration 格式:"00:00:10.68"
     * @return
     */
    private static int getPlaySeconds(String duration) {
        int min = 0;
        String strs[] = duration.split(":");
        if (strs[0].compareTo("0") > 0) {
            min += Integer.valueOf(strs[0]) * 60 * 60;//秒
        }
        if (strs[1].compareTo("0") > 0) {
            min += Integer.valueOf(strs[1]) * 60;
        }
        if (strs[2].compareTo("0") > 0) {
            min += Math.round(Float.valueOf(strs[2]));
        }
        return min;
    }
}
