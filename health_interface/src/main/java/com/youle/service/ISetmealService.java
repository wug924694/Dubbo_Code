package com.youle.service;


import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.CheckGroup;
import com.youle.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface ISetmealService {

    public void add(Setmeal setmeal,Integer[] checkgroupIds);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    void delete(Integer id);

    List<Map<String, Object>> findSetMealCount();
}
