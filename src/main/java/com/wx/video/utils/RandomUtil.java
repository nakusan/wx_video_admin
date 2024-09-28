package com.wx.video.utils;

import java.util.Random;

public final class RandomUtil {

    /**
     * 生成指定长度的随机字符串
     * 随机字符串由大小写字母和数字组成
     *
     * @param length 需要生成的随机字符串的长度
     * @return 生成的随机字符串
     */
    public static String generateRandomString(int length) {
        // 可用的字符集合，包含大小写字母和数字
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwsyz0123456789";
        // 使用StringBuilder来高效地构建字符串
        StringBuilder str = new StringBuilder();
        // 创建Random对象，用于后续生成随机数
        Random random = new Random();
        // 循环length次，每次添加一个随机字符
        for (int i = 0; i < length; i++) {
            // 添加随机字符到字符串中
            str.append(characters.charAt(random.nextInt(characters.length())));
        }
        // 返回构建好的随机字符串
        return str.toString();
    }
}
