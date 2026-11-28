package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.SubscribedMember;
import com.joysistvi.prestigecliapp.repository.SubscribedMemberRepository;

import java.util.List;

public class SubscribedMemberServiceImpl implements SubscribedMemberService {

    private final SubscribedMemberRepository subscribedMemberRepository;

    public SubscribedMemberServiceImpl(SubscribedMemberRepository subscribedMemberRepository) {
        this.subscribedMemberRepository = subscribedMemberRepository;
    }

    @Override
    public List<SubscribedMember> findAll() {
        return subscribedMemberRepository.findAll();
    }

    @Override
    public SubscribedMember findById(int id) {
        return subscribedMemberRepository.findById(id);
    }

    @Override
    public List<SubscribedMember> findByUserId(int userId) {
        return subscribedMemberRepository.findByUserId(userId);
    }

    @Override
    public List<SubscribedMember> findByMembershipPlanId(int membershipPlanId) {
        return subscribedMemberRepository.findByMembershipPlanId(membershipPlanId);
    }

    @Override
    public boolean add(SubscribedMember subscribedMember) {
        return subscribedMemberRepository.add(subscribedMember);
    }

    @Override
    public boolean remove(int id) {
        return subscribedMemberRepository.remove(id);
    }

    @Override
    public boolean archive(int id) {
        return subscribedMemberRepository.archive(id);
    }

    @Override
    public boolean restore(int id) {
        return subscribedMemberRepository.restore(id);
    }

    @Override
    public boolean updateStatus(int id, String status) {
        return subscribedMemberRepository.updateStatus(id, status);
    }

}