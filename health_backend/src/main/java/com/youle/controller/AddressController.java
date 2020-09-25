package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.constant.RedisConstant;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.entiy.Result;
import com.youle.pojo.Address;
import com.youle.service.AddressService;
import com.youle.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;


@RestController
@RequestMapping("/map")
public class AddressController {

    @Reference
    private AddressService addressService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile")MultipartFile imgFile){
        //进行文件上传
        String filename = imgFile.getOriginalFilename();//原始文件名称
        int indexOf = filename.lastIndexOf(".");
        String name = filename.substring(indexOf);//获取文件原始后缀
        //随机生成文件名称
        filename = UUID.randomUUID().toString() + name;
        //借助七牛云工具类完成文件上传
        try {
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),filename);
            //提交云存储之后将文件名称保存到redis
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,filename);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,filename);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return addressService.pageQuery(queryPageBean);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Address address){
        try {
            addressService.add(address);
            return new Result(true,"添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"添加失败");
        }
    }

    @RequestMapping("/findById")
    public Result findById(@RequestParam("id") Integer id){
        try {
            Address address = addressService.findById(id);
            return new Result(true,"查找成功",address);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"查找失败");
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Address address){
        try {
            addressService.edit(address);
            return new Result(true,"编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"编辑失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam("id") Integer id){
        try {
            addressService.delete(id);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,"删除失败");
        }
    }
}
