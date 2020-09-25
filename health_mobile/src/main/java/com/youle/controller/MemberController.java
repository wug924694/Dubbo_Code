package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.youle.constant.MessageConstant;
import com.youle.constant.RedisMessageConstant;
import com.youle.entiy.Result;
import com.youle.pojo.Member;
import com.youle.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RequestMapping("/login")
@RestController
public class MemberController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @RequestMapping("/check")
    public Result check(@RequestBody Map map, HttpServletResponse response){
        String validateCode = (String) map.get("validateCode");
        String telephone = (String) map.get("telephone");
        String redis_code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if(validateCode != null && redis_code != null && validateCode.equals(redis_code)){
            //验证成功,判断用户是否是会员，不是则注册
            Member member = memberService.findByTelephone(telephone);
            if(member == null){
                //不是会员
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberService.add(member);
            }
            //写入cookie，内容为手机号
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 *30);
            response.addCookie(cookie);
            //将会员信息保存到redis 手机号作为key保存30分钟
            String json = JSON.toJSON(member).toString();
            jedisPool.getResource().setex(telephone,60*30,json);
            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
    }

}
