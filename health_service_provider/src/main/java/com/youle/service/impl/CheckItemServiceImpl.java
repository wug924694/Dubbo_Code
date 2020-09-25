package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.dao.ICheckItemDao;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.CheckItem;
import com.youle.service.ICheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service(interfaceClass = ICheckItemService.class)
@Transactional
public class CheckItemServiceImpl implements ICheckItemService {
    @Autowired
    private ICheckItemDao checkItemDao;

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.selectByCondition(queryString);
        long total = page.getTotal();
        List<CheckItem> list = page.getResult();
        return new PageResult(total,list);
    }

    @Override
    public void delete(Integer checkItemId) {
        long count = checkItemDao.findCountByCheckItemId(checkItemId);
        if(count > 0){
            //不能删除
            new RuntimeException();
        }
        checkItemDao.delete(checkItemId);
    }

    @Override
    public void edit(CheckItem checkItem) {
        checkItemDao.edit(checkItem);
    }

    @Override
    public CheckItem findById(Integer checkItemId) {
        return checkItemDao.findById(checkItemId);
    }

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

}
