package com.wx.video.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

import java.util.Date;

@Data
@TableName("videos")
public class Video implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /*** 视频ID ***/
    private String videoId;
    /*** 视频描述 ***/
    private String videoName;
    /*** 视频描述 ***/
    private String videoDesc;
    /*** 视频存放的路径 ***/
    private String videoPath;
    /*** 视频秒数 ***/
    private String videoSeconds;
    /*** 卖出的数量 ***/
    private Long salesCounts;
    /**
     * 视频状态：
     1、发布成功
     2、禁止播放，管理员操作
     */
    private Integer status;
    /*** 创建时间 ***/
    private Date createTime;
    /*** 创建时间 ***/
    private Date updateTime;
}