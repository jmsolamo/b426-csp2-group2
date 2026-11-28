package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.MembershipCard;
import java.util.List;

public interface MembershipCardRepository {

    List<MembershipCard> findAll();

    MembershipCard findById(int id);

    boolean save(MembershipCard membershipCard);

    boolean update(MembershipCard membershipCard);

    boolean delete(int id);
}