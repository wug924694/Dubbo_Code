package com.youle.service;

import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.CheckGroup;
import com.youle.pojo.CheckItem;

import java.util.List;

public interface ICheckGroupService {

    void add(CheckGroup checkGroup,Integer[] checkItemIds);

    PageResult pageQuery(QueryPageBean queryPageBean);


    CheckGroup findById(Integer checkGroupId);

    List<CheckItem> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    void edit(CheckGroup checkGroup, Integer[] checkItemIds);

    void delete(Integer checkGroupId);

    List<CheckGroup> findAll();
}
