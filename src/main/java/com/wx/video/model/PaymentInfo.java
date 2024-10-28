package com.wx.video.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("payment_info")
public class PaymentInfo {

    //定义主键策略：跟随数据库的主键自增
    @TableId(value = "id", type = IdType.AUTO)
    private String id; //主键

    private String orderNo;//商品订单编号
    private String transactionId;//支付系统交易编号
    private String paymentType;//支付类型
    private String tradeType;//交易类型
    private String tradeState;//交易状态
    private Integer payerTotal;//支付金额(分)
    private String content;//通知参数
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
}
