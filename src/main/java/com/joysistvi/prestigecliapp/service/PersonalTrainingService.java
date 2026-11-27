package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.PersonalTraining;
import java.math.BigDecimal;
import java.util.List;

public interface PersonalTrainingService {
    List<PersonalTraining> getAllActive();
    List<PersonalTraining> getAllArchived();
    List<PersonalTraining> getByInstructorId(int instructorId);
    PersonalTraining getById(int id);
    boolean create(int instructorId, String category, String description, BigDecimal hourlyRate);
    boolean update(int id, int instructorId, String category, String description, BigDecimal hourlyRate);
    boolean archive(int id);
    boolean restore(int id);
    boolean delete(int id);
    List<PersonalTraining> search(String keyword);
}