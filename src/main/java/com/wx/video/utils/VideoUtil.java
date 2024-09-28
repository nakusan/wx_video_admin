package com.wx.video.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;


public final class VideoUtil {

    private static final String FFMPEG_PATH = "D:/ffmpeg/bin/ffmpeg";

//    /**
//     *
//     * @param filePath
//     * @return
//     */
//    public static String getVideoDuration(String filePath) throws Exception {
//        String duration = null;
//        try {
//            ProcessBuilder processBuilder = new ProcessBuilder(FFMPEG_PATH, "-i", filePath);
//            processBuilder.redirectErrorStream(true); // Merge stderr into stdout
//            Process process = processBuilder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            Pattern pattern = Pattern.compile("Duration: (\\d{2}):(\\d{2}):(\\d{2})");
//
//            while ((line = reader.readLine()) != null) {
//                Matcher matcher = pattern.matcher(line);
//                if (matcher.find()) {
//                    duration = matcher.group(1) + ":" + matcher.group(2) + ":" + matcher.group(3);
//                    break;
//                }
//            }
//
//            process.waitFor();
//        } catch (Exception e) {
//            System.out.println(e);
//            throw e;
//        }
//        return duration;
//    }

    public static String getVideoDuration(MultipartFile file) {
        String duration = null;
        File tempFile = null;
        try {
            // 将MultipartFile保存为临时文件
            tempFile = File.createTempFile("temp", file.getOriginalFilename());
            file.transferTo(tempFile);

            // 创建FFmpeg命令
            ProcessBuilder processBuilder = new ProcessBuilder(FFMPEG_PATH, "-i", tempFile.getAbsolutePath());
            processBuilder.redirectErrorStream(true); // Merge stderr into stdout
            Process process = processBuilder.start();

            // 读取FFmpeg的输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            Pattern pattern = Pattern.compile("Duration: (\\d{2}):(\\d{2}):(\\d{2})");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    duration = matcher.group(1) + ":" + matcher.group(2) + ":" + matcher.group(3);
                    break;
                }
            }

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // 转换失败时返回null
        } finally {
            // 删除临时文件
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
        return duration;
    }

//    /**
//     * 获取视频时长
//     *
//     * @param file
//     * @return
//     */
//    public static Map<String, String> getVideoDuration(MultipartFile file) throws Exception {
//        Map<String, String> returnMap = new HashMap<String, String>();
//        File tempFile = null;
//        String outputImagePath = String.format("%s.jpg", id);
//        try {
//            // 将MultipartFile保存为临时文件
//            tempFile = File.createTempFile("temp", file.getOriginalFilename());
//            file.transferTo(tempFile);
//
//            // 创建FFmpeg命令以获取视频信息和提取封面
//            ProcessBuilder processBuilder = new ProcessBuilder(
//                    FFMPEG_PATH, "-i", tempFile.getAbsolutePath(), "-ss", "00:00:01", "-vframes", "1", outputImagePath
//            );
//            processBuilder.redirectErrorStream(true); // 合并stderr到stdout
//            Process process = processBuilder.start();
//
//            // 读取FFmpeg的输出
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            Pattern pattern = Pattern.compile("Duration: (\\d{2}):(\\d{2}):(\\d{2})");
//
//            while ((line = reader.readLine()) != null) {
//                Matcher matcher = pattern.matcher(line);
//                if (matcher.find()) {
//                    String duration = matcher.group(1) + ":" + matcher.group(2) + ":" + matcher.group(3);
//                    returnMap.put("duration", duration);
//                    break;
//                }
//            }
//
//            int exitCode = process.waitFor();
//            if (exitCode != 0) {
//                throw new Exception("Failed to process video");
//            }
//
//            File outputFile = new File(outputImagePath);
//            if (!outputFile.exists()) {
//                throw new Exception("Failed to extract video cover");
//            }
//
//            returnMap.put("outputImagePath", outputImagePath);
//
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            // 删除临时文件
//            if (tempFile != null && tempFile.exists()) {
//                tempFile.delete();
//            }
//        }
//        return returnMap;
//    }

//    /**
//     *
//     * @param filePath
//     * @return
//     */
//    public static void extractVideoCover(String filePath, String outputImagePath) throws Exception {
//        try {
//            File outputFile = new File(outputImagePath);
//            ProcessBuilder processBuilder = new ProcessBuilder(
//                    FFMPEG_PATH, "-i", filePath, "-ss", "00:00:01", "-vframes", "1", outputFile.getAbsolutePath()
//            );
//            processBuilder.redirectErrorStream(true); // Merge stderr into stdout
//            Process process = processBuilder.start();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
////                System.out.println(line);
//            }
//
//            int exitCode = process.waitFor();
//            if (exitCode == 0 && outputFile.exists()) {
//                return;
//            } else {
//                throw new Exception("Extracted video cover failed");
//            }
//        } catch (Exception e) {
//            throw e;
//        }
//    }

    /**
     * 获取当前时间
     *
     * @return Date
     */
    public static Date getCurrentDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return Date.from(currentDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }



//    public static void main(String[] args) {
//        String filePath = "D:/AwesomeVideoUpload/video/2.mp4";
//        try {
//            String duration = getVideoDuration(filePath);
//            if (duration != null) {
//                System.out.println("Duration: " + duration);
//            } else {
//                System.out.println("Failed to retrieve video duration.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
