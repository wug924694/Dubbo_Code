package com.youle.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.youle.constant.MessageConstant;
import com.youle.constant.RedisConstant;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.entiy.Result;
import com.youle.pojo.CheckGroup;
import com.youle.pojo.Setmeal;
import com.youle.service.ISetmealService;
import com.youle.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/setmeal")
@RestController
public class SetmealController {
    @Reference
    private ISetmealService setmealService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        //进行文件上传
        String filename = imgFile.getOriginalFilename();//原始文件名称
        int indexOf = filename.lastIndexOf(".");
        String name = filename.substring(indexOf + 1);//获取文件原始后缀
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

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,@RequestParam("groupIds") Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.findPage(queryPageBean);
    }


    @RequestMapping("/findById")
    public Result findById(@RequestParam("id")Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
    }

    @RequestMapping("/findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(@RequestParam("id") Integer setmealId){
        try {
            List<Integer> groupIds = setmealService.findCheckGroupIdsBySetmealId(setmealId);
            return new Result(true,MessageConstant.QUERY_SETMEALLIST_SUCCESS,groupIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody Setmeal setmeal,@RequestParam("groupIds") Integer[] checkgroupIds){
        try {
            setmealService.edit(setmeal,checkgroupIds);
            return new Result(true,MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam("id") Integer id){
        try {
            setmealService.delete(id);
            return new Result(true,"删除套餐成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false,"删除套餐失败");
    }

    @RequestMapping("/getAllSetmeal")
    public Result getAllSetmeal(){
        try {
            List<Setmeal> list = setmealService.findAll();
            return new Result(true,"查询套餐成功",list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "查询套餐失败");
        }
    }
}
