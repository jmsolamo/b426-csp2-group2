package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.MembershipPlan;
import java.util.List;

public interface MembershipPlanRepository {

    List<MembershipPlan> findAll();

    MembershipPlan findById(int id);

    boolean save(MembershipPlan plan);

    boolean update(MembershipPlan plan);

    boolean delete(int id);
}
