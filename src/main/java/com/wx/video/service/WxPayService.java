package com.wx.video.service;

import java.security.GeneralSecurityException;
import java.util.Map;

public interface WxPayService {

    /**
     * 初始化JSAPI支付请求
     *
     * @param productId 商品ID，用于标识购买的商品
     * @return 返回一个包含支付所需数据的Map对象，包括但不限于签名、时间戳等
     * @throws Exception 如果支付请求初始化过程中出现错误，则抛出异常
     */
    Map<String, Object> jsapiPay(String productId) throws Exception;

    /**
     * 处理订单信息
     *
     * 该方法主要负责处理接收到的订单数据，进行一系列的业务逻辑处理，例如验证订单信息的完整性，
     * 进行库存检查，计算订单总价等它通过接受一个包含订单信息的映射来执行这些操作
     *
     * @param bodyMap 包含订单信息的键值对映射，其中键是订单数据的字段名，值是对应的字段值
     * @throws GeneralSecurityException 如果在处理订单过程中遇到安全相关的问题，例如数据加密或解密失败，
     *                                  则抛出此异常
     */
    void processOrder(Map<String, Object> bodyMap) throws GeneralSecurityException;

    /**
     * 取消订单
     *
     * @param orderNo 订单编号，用于标识要取消的订单
     * @throws Exception 如果取消订单的过程中发生错误，则抛出异常
     */
    void cancelOrder(String orderNo) throws Exception;

    /**
     * 根据订单号查询订单信息
     *
     * @param orderNo 订单号，用于标识特定的订单
     * @return 返回查询到的订单信息，具体格式和内容取决于实现
     * @throws Exception 如果查询过程中发生错误，抛出此异常
     */
    String queryOrder(String orderNo) throws Exception;

    /**
     * 检查订单状态
     * 此方法用于检查特定订单号对应的订单状态它可能涉及到与数据库或外部系统的交互，
     * 以获取并验证订单的当前状态
     *
     * @param orderNo 订单号，用于标识和查询特定的订单
     * @throws Exception 如果在检查订单状态过程中遇到任何错误或异常情况，将抛出此异常
     */
    void checkOrderStatus(String orderNo) throws Exception;

//    void refund(String orderNo, String reason) throws Exception;
//
//    String queryRefund(String orderNo) throws Exception;
//
//    void checkRefundStatus(String refundNo) throws Exception;
//
//    void processRefund(Map<String, Object> bodyMap) throws Exception;
//
//    String queryBill(String billDate, String type) throws Exception;
//
//    String downloadBill(String billDate, String type) throws Exception;
//
//    Map<String, Object> nativePayV2(Long productId, String remoteAddr) throws Exception;
}
