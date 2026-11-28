package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.FitnessClass;
import java.util.ArrayList;
import java.util.List;

public class FitnessClassRepositoryImpl implements FitnessClassRepository {

    @Override
    public List<FitnessClass> findAll() {
        return new ArrayList<>();
    }

    @Override
    public FitnessClass findById(int id) {
        return null;
    }

    @Override
    public boolean save(FitnessClass fitnessClass) {
        return false;
    }

    @Override
    public boolean update(FitnessClass fitnessClass) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}