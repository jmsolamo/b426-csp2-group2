package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.MembershipCard;
import java.util.ArrayList;
import java.util.List;

public class MembershipCardRepositoryImpl implements MembershipCardRepository {

    @Override
    public List<MembershipCard> findAll() {
        return new ArrayList<>();
    }

    @Override
    public MembershipCard findById(int id) {
        return null;
    }

    @Override
    public boolean save(MembershipCard membershipCard) {
        return false;
    }

    @Override
    public boolean update(MembershipCard membershipCard) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}