package com.youle.service;

import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Address;

public interface AddressService {

    public void add(Address address);

    PageResult pageQuery(QueryPageBean queryPageBean);

    Address findById(Integer id);

    void edit(Address address);

    void delete(Integer id);
}
