package com.wx.video.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wx.video.model.RefundInfo;

import java.util.List;

public interface RefundInfoService extends IService<RefundInfo> {

    RefundInfo createRefundByOrderNo(String orderNo, String reason);

    void updateRefund(String content);

    List<RefundInfo> getNoRefundOrderByDuration(int minutes);
}
