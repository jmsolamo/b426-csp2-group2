package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.FitnessClass;
import java.util.List;

public interface FitnessClassRepository {

    List<FitnessClass> findAll();

    FitnessClass findById(int id);

    boolean save(FitnessClass fitnessClass);

    boolean update(FitnessClass fitnessClass);

    boolean delete(int id);
}