package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.MembershipPlan;
import java.util.ArrayList;
import java.util.List;

public class MembershipPlanServiceImpl implements MembershipPlanService {

    @Override
    public List<MembershipPlan> getAllMembershipPlans() {
        return new ArrayList<>();
    }

    @Override
    public MembershipPlan getMembershipPlanById(int id) {
        return null;
    }

    @Override
    public boolean addMembershipPlan(MembershipPlan membershipPlan) {
        return false;
    }

    @Override
    public boolean updateMembershipPlan(MembershipPlan membershipPlan) {
        return false;
    }

    @Override
    public boolean deleteMembershipPlan(int id) {
        return false;
    }
}