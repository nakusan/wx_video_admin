package com.wx.video.api;

import com.wx.video.enums.OrderStatus;
import com.wx.video.model.OrderInfo;
import com.wx.video.model.vo.Result;
import com.wx.video.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin //开放前端的跨域访问
@Api(tags = "商品订单管理")
@RestController
@RequestMapping("/api/order-info")
public class OrderInfoController {

    @Resource
    private OrderInfoService orderInfoService;

    @ApiOperation("订单列表")
    @GetMapping("/list")
    public Result list(){

        List<OrderInfo> list = orderInfoService.listOrderByCreateTimeDesc();
        return Result.build(200, "success", list);
    }

    /**
     * 查询本地订单状态
     * @param orderNo
     * @return
     */
    @ApiOperation("查询本地订单状态")
    @GetMapping("/query-order-status/{orderNo}")
    public Result queryOrderStatus(@PathVariable String orderNo){

        String orderStatus = orderInfoService.getOrderStatus(orderNo);
        if(OrderStatus.SUCCESS.getType().equals(orderStatus)){
            return Result.build(200, "支付成功", null); //支付成功
        }
        return Result.build(101, "支付中...", null);
    }



}
