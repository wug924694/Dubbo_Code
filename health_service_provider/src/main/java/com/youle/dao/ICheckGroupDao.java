package com.youle.dao;


import com.github.pagehelper.Page;
import com.youle.pojo.CheckGroup;
import com.youle.pojo.CheckItem;

import java.util.List;
import java.util.Map;

public interface ICheckGroupDao {

    public void add(CheckGroup checkGroup);

    public void addCheckGroupAndCheckItem(Map map);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(Integer checkGroupId);

    List<CheckItem> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    void edit(CheckGroup checkGroup);

    void deleteAssociation(Integer checkGroupId);

    void delete(Integer checkGroupId);

    List<CheckGroup> findAll();

    List<CheckGroup> findByCheckGruopBySetMealId(Integer setmealId);
}
