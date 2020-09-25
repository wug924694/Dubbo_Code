package com.youle.service;


import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.CheckItem;

import java.util.List;

public interface ICheckItemService {

    void add(CheckItem checkItem);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer checkItemId);

    void edit(CheckItem checkItem);

    CheckItem findById(Integer checkItemId);

    List<CheckItem> findAll();
}
