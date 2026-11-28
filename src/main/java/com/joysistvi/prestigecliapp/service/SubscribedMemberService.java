package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.SubscribedMember;

import java.util.List;

public interface SubscribedMemberService {

    public List<SubscribedMember> findAll();
    public SubscribedMember findById(int id);
    public List<SubscribedMember> findByUserId(int userId);
    public List<SubscribedMember> findByMembershipPlanId(int membershipPlanId);
    public boolean add(SubscribedMember subscribedMember);
    public boolean remove(int id);
    public boolean archive(int id);
    public boolean restore(int id);
    public boolean updateStatus(int id, String status);

}