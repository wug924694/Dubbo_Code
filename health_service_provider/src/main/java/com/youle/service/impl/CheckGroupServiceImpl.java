package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.dao.ICheckGroupDao;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.CheckGroup;
import com.youle.pojo.CheckItem;
import com.youle.service.ICheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ICheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements ICheckGroupService {
    @Autowired
    private ICheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkItemIds) {
        //新增检查组 同时需要关联检查项
        checkGroupDao.add(checkGroup);
        //设置检查组和检查项的多对多关系
        //获取新增检查组的id
        Integer checkGroupId = checkGroup.getId();
        setCheckGroupAndCheckItem(checkGroupId,checkItemIds);
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckGroup> pages = checkGroupDao.findByCondition(queryString);
        return new PageResult(pages.getTotal(),pages.getResult());
    }

    @Override
    public CheckGroup findById(Integer checkGroupId) {
        return checkGroupDao.findById(checkGroupId);
    }

    @Override
    public List<CheckItem> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(checkGroupId);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkItemIds) {
        checkGroupDao.edit(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        checkGroupDao.deleteAssociation(checkGroupId);
        setCheckGroupAndCheckItem(checkGroupId,checkItemIds);
    }

    @Override
    public void delete(Integer checkGroupId) {
        checkGroupDao.deleteAssociation(checkGroupId);
        checkGroupDao.delete(checkGroupId);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


    public void setCheckGroupAndCheckItem(Integer checkGroupId, Integer[] checkItemIds){
        if(checkItemIds != null && checkItemIds.length >= 0){
            for (Integer itemId : checkItemIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",itemId);
                checkGroupDao.addCheckGroupAndCheckItem(map);
            }
        }
    }
}
