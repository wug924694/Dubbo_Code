package com.youle.dao;


import com.github.pagehelper.Page;
import com.youle.pojo.CheckItem;

import java.util.List;


public interface ICheckItemDao {


    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);

    public long findCountByCheckItemId(Integer checkItemId);

    public void delete(Integer checkItemId);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer checkItemId);

    List<CheckItem> findAll();

    List<CheckItem> findByCheckItemByCheckGroupId(Integer checkGroupId);
}
