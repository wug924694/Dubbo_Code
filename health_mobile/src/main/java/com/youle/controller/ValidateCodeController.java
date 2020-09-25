package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.youle.constant.MessageConstant;
import com.youle.constant.RedisMessageConstant;
import com.youle.entiy.Result;
import com.youle.utils.SMSUtils;
import com.youle.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RequestMapping("/validateCode")
@RestController
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        //发送短信验证码
        //生成随机验证码
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        //给用户发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            //短信发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //发送成功
        //将短信保存到redis(10分钟)
        jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,600,code.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        //给用户发送短信
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code.toString());
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result( false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        //存到redis
        jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,600,code.toString());
        return new Result(true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}
