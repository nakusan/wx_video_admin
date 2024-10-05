package com.wx.video.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("category")
public class Category  implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String categoryId;
    private String categoryName;

}
