package com.youle.dao;

import com.github.pagehelper.Page;
import com.youle.pojo.CheckGroup;
import com.youle.pojo.Setmeal;


import java.util.List;
import java.util.Map;

public interface ISetmealDao {
    void add(Setmeal setmeal);

    void addSetmealAndCheckGroup(Map map);

    Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId);

    void deleteAssociation(Integer setmealId);

    void edit(Setmeal setmeal);

    void delete(Integer id);

    List<Map<String, Object>> findSetMealCount();
}
