package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.FitnessClass;
import java.util.List;

public interface FitnessClassService {

    List<FitnessClass> getAllFitnessClasses();

    FitnessClass getFitnessClassById(int id);

    boolean addFitnessClass(FitnessClass fitnessClass);

    boolean updateFitnessClass(FitnessClass fitnessClass);

    boolean deleteFitnessClass(int id);
}