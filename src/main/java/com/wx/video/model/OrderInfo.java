package com.wx.video.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("order_info")
public class OrderInfo {

    //定义主键策略：跟随数据库的主键自增
    @TableId(value = "id", type = IdType.AUTO)
    private String id; //主键

    private String title;//订单标题
    private String orderNo;//商户订单编号
    private Long userId;//用户id
    private String productId;//支付产品id
    private Integer totalFee;//订单金额(分)
    private String prepayId;//预支付ID
    private String orderStatus;//订单状态
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
}
