package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.youle.dao.MemberDao;
import com.youle.pojo.Member;
import com.youle.service.MemberService;
import com.youle.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if(password != null){
            //使用MD5加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> months) {
        List<Integer> list = new ArrayList<>();
        for (String month : months) {
            String date = month + ".31";
            Integer count = memberDao.findMemberCountBeforeDate(date);
            list.add(count);
        }
        return list;
    }
}
