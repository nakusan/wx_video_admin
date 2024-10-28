package com.wx.video.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.video.enums.OrderStatus;
import com.wx.video.model.OrderInfo;

import java.util.List;

public interface OrderInfoService extends IService<OrderInfo> {

    OrderInfo createOrderByProductId(String videoId);

    void savePrepayId(String orderNo, String prepayId);

    List<OrderInfo> listOrderByCreateTimeDesc();

    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    String getOrderStatus(String orderNo);

    List<OrderInfo> getNoPayOrderByDuration(int minutes);

    OrderInfo getOrderByOrderNo(String orderNo);
}
