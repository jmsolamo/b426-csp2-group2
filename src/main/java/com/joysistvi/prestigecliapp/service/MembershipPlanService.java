package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.MembershipPlan;
import java.util.List;

public interface MembershipPlanService {

    List<MembershipPlan> getAllMembershipPlans();

    MembershipPlan getMembershipPlanById(int id);

    boolean addMembershipPlan(MembershipPlan membershipPlan);

    boolean updateMembershipPlan(MembershipPlan membershipPlan);

    boolean deleteMembershipPlan(int id);
}