package com.wx.video.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_file_info")
public class FileInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /*** 文件名 ***/
    private String name;
    /*** 文件地址 ***/
    private String path;
    /*** 状态 ***/
    private int status;
    /*** 创建时间 ***/
    private Date createTime;
    /*** 创建时间 ***/
    private Date updateTime;
}
