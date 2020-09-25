package com.youle.dao;

import com.github.pagehelper.Page;
import com.youle.pojo.Address;

public interface AddressDao {
    Page<Address> selectByCondition(String queryString);

    void add(Address address);

    Address findById(Integer id);

    void edit(Address address);

    void delete(Integer id);
}
