package com.wx.video.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("users")
public class User implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /*** openid ***/
    private String openid;
    /*** sessionKey ***/
    private String sessionKey;
    /*** 我的头像，如果没有默认给一张 ***/
    private String faceImage;
    /*** 昵称 ***/
    private String nickname;
    /*** 创建日期 ***/
    private Date createTime;
    /*** 更新日期 ***/
    private Date updateTime;
}