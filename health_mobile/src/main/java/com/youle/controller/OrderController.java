package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.youle.constant.MessageConstant;
import com.youle.constant.RedisMessageConstant;
import com.youle.entiy.Result;
import com.youle.pojo.Order;
import com.youle.service.IOrderService;
import com.youle.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private IOrderService oderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){//{name:zs,age:18....}
        String telephone = (String) map.get("telephone");
        //从redis中取出短信的验证码
        String ValidateCodeRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        //从用户输入的短信验证码和redis中的短信验证码进行对比
        if(ValidateCodeRedis != null && validateCode != null && validateCode.equals(ValidateCodeRedis)){
            //如果对比成功  调用服务完成预约信息的保存
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Result result = null;
            try {
                result = oderService.order(map);
            } catch (Exception e) {
                e.printStackTrace();
                if(result != null){
                    return result;
                }else{
                    //预约设置失败
                    return new Result(false,MessageConstant.ORDERSETTING_FAIL);
                }
            }
            //给用户发送预约成功的短信
            if(result.isFlag()){
                //预约成功
                try {
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,(String)map.get("orderDate"));
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }else{
            //如果对比失败  返回结果页面
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map map = oderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
