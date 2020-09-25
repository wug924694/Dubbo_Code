package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.constant.RedisConstant;
import com.youle.dao.AddressDao;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Address;
import com.youle.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.List;


@Service(interfaceClass = AddressService.class)
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private JedisPool jedisPool;

    @Override
    public void add(Address address) {
        addressDao.add(address);
        //将图片名称存储到redis
        String img = address.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,img);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Address> page = addressDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<Address> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public Address findById(Integer id) {
        return addressDao.findById(id);
    }

    @Override
    public void edit(Address address) {
        addressDao.edit(address);
    }

    @Override
    public void delete(Integer id) {
        addressDao.delete(id);
    }
}
