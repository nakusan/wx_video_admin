package com.wx.video.api;

import com.google.gson.Gson;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wx.video.model.vo.Result;
import com.wx.video.service.WxPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.wx.video.utils.HttpUtils;
import com.wx.video.utils.WechatPay2ValidatorForRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@CrossOrigin //跨域
@RestController
@RequestMapping("/api/wx-pay")
@Api(tags = "网站微信支付APIv3")
@Slf4j
public class WxPayController {

    @Resource
    private WxPayService wxPayService;

    @Resource
    private Verifier verifier;

    /**
     * JSAPI下单
     * @param productId
     * @return
     * @throws Exception
     */
    @ApiOperation("调用统一下单API")
    @PostMapping("/jsapi/{productId}")
    public Result jsapiPay(@PathVariable String productId) throws Exception {
        log.info("发起支付请求 v3");
        //返回预支付交易ID和订单号
        Map<String, Object> map = wxPayService.jsapiPay(productId);
        return Result.ok(map);
    }

    /**
     * 支付通知
     * 微信支付通过支付通知接口将用户支付成功消息通知给商户
     */
    @ApiOperation("支付通知")
    @PostMapping("/jsapi/notify")
    public String jsapiNotify(HttpServletRequest request, HttpServletResponse response){
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<>();//应答对象
        try {
            //处理通知参数
            String body = HttpUtils.readData(request);
            Map<String, Object> bodyMap = gson.fromJson(body, HashMap.class);
            String requestId = (String)bodyMap.get("id");
            log.info("支付通知的id ===> {}", requestId);
            //签名的验证
            WechatPay2ValidatorForRequest wechatPay2ValidatorForRequest
                    = new WechatPay2ValidatorForRequest(verifier, requestId, body);
            if(!wechatPay2ValidatorForRequest.validate(request)){
                log.error("通知验签失败");
                //失败应答
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "通知验签失败");
                return gson.toJson(map);
            }
            log.info("通知验签成功");

            //处理订单
            wxPayService.processOrder(bodyMap);

            //应答超时
            //模拟接收微信端的重复通知
            TimeUnit.SECONDS.sleep(5);

            //成功应答
            response.setStatus(200);
            map.put("code", "SUCCESS");
            map.put("message", "成功");
            return gson.toJson(map);

        } catch (Exception e) {
            log.error("支付通知异常", e);
            //失败应答
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", "失败");
            return gson.toJson(map);
        }

    }

    /**
     * 用户取消订单
     * @param orderNo
     * @return
     * @throws Exception
     */
    @ApiOperation("用户取消订单")
    @PostMapping("/cancel/{orderNo}")
    public Result cancel(@PathVariable String orderNo) throws Exception {

        log.info("取消订单");

        wxPayService.cancelOrder(orderNo);
        return Result.build(200, "订单已取消", null);
    }

    /**
     * 查询订单
     * @param orderNo
     * @return
     * @throws Exception
     */
    @ApiOperation("查询订单：测试订单状态用")
    @GetMapping("/query/{orderNo}")
    public Result queryOrder(@PathVariable String orderNo) throws Exception {

        log.info("查询订单");

        String result = wxPayService.queryOrder(orderNo);
        return Result.build(200, "查询成功", result);

    }


//    @ApiOperation("申请退款")
//    @PostMapping("/refunds/{orderNo}/{reason}")
//    public Result refunds(@PathVariable String orderNo, @PathVariable String reason) throws Exception {
//
//        log.info("申请退款");
//        wxPayService.refund(orderNo, reason);
//        return Result.ok();
//    }

//    /**
//     * 查询退款
//     * @param refundNo
//     * @return
//     * @throws Exception
//     */
//    @ApiOperation("查询退款：测试用")
//    @GetMapping("/query-refund/{refundNo}")
//    public Result queryRefund(@PathVariable String refundNo) throws Exception {
//
//        log.info("查询退款");
//
//        String result = wxPayService.queryRefund(refundNo);
//        return Result.ok().setMessage("查询成功").data("result", result);
//    }


//    /**
//     * 退款结果通知
//     * 退款状态改变后，微信会把相关退款结果发送给商户。
//     */
//    @ApiOperation("退款结果通知")
//    @PostMapping("/refunds/notify")
//    public String refundsNotify(HttpServletRequest request, HttpServletResponse response){
//
//        log.info("退款通知执行");
//        Gson gson = new Gson();
//        Map<String, String> map = new HashMap<>();//应答对象
//
//        try {
//            //处理通知参数
//            String body = HttpUtils.readData(request);
//            Map<String, Object> bodyMap = gson.fromJson(body, HashMap.class);
//            String requestId = (String)bodyMap.get("id");
//            log.info("支付通知的id ===> {}", requestId);
//
//            //签名的验证
//            WechatPay2ValidatorForRequest wechatPay2ValidatorForRequest
//                    = new WechatPay2ValidatorForRequest(verifier, requestId, body);
//            if(!wechatPay2ValidatorForRequest.validate(request)){
//
//                log.error("通知验签失败");
//                //失败应答
//                response.setStatus(500);
//                map.put("code", "ERROR");
//                map.put("message", "通知验签失败");
//                return gson.toJson(map);
//            }
//            log.info("通知验签成功");
//
//            //处理退款单
//            wxPayService.processRefund(bodyMap);
//
//            //成功应答
//            response.setStatus(200);
//            map.put("code", "SUCCESS");
//            map.put("message", "成功");
//            return gson.toJson(map);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            //失败应答
//            response.setStatus(500);
//            map.put("code", "ERROR");
//            map.put("message", "失败");
//            return gson.toJson(map);
//        }
//    }

//    @ApiOperation("获取账单url：测试用")
//    @GetMapping("/querybill/{billDate}/{type}")
//    public Result queryTradeBill(
//            @PathVariable String billDate,
//            @PathVariable String type) throws Exception {
//
//        log.info("获取账单url");
//
//        String downloadUrl = wxPayService.queryBill(billDate, type);
//        return Result.ok().setMessage("获取账单url成功").data("downloadUrl", downloadUrl);
//    }

//    @ApiOperation("下载账单")
//    @GetMapping("/downloadbill/{billDate}/{type}")
//    public Result downloadBill(
//            @PathVariable String billDate,
//            @PathVariable String type) throws Exception {
//
//        log.info("下载账单");
//        String result = wxPayService.downloadBill(billDate, type);
//
//        return Result.ok().data("result", result);
//    }

}
