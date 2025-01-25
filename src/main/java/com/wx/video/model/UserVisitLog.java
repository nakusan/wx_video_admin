package com.wx.video.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_user_visit_log")
public class UserVisitLog {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String openid;
    private String videoId;
    private Date visitTime;
}
