package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.FitnessClass;
import java.util.ArrayList;
import java.util.List;

public class FitnessClassServiceImpl implements FitnessClassService {

    @Override
    public List<FitnessClass> getAllFitnessClasses() {
        return new ArrayList<>();
    }

    @Override
    public FitnessClass getFitnessClassById(int id) {
        return null;
    }

    @Override
    public boolean addFitnessClass(FitnessClass fitnessClass) {
        return false;
    }

    @Override
    public boolean updateFitnessClass(FitnessClass fitnessClass) {
        return false;
    }

    @Override
    public boolean deleteFitnessClass(int id) {
        return false;
    }
}