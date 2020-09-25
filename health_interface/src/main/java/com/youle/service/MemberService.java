package com.youle.service;

import com.youle.pojo.Member;

import java.util.List;

public interface MemberService {

    public Member findByTelephone(String telephone);

    public void add(Member member);

    public List<Integer> findMemberCountByMonth(List<String> months);
}
