package com.wx.video.enums;

/**
 * @description 视频状态枚举
 */
public enum VideoStatusEnum {
    ACTIVE(0),  // 可用
    DELETED(1), // 已删除
    SUCCESS(2), // 发布成功
    FORBID(3);  // 禁止播放

    public final int value;

    VideoStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}


