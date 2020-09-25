package com.youle.service;

import com.youle.entiy.Result;

import java.util.Map;

public interface IOrderService {
    public Result order(Map map) throws Exception;

    Map findById(Integer id);
}
