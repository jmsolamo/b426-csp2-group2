package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.PersonalTraining;
import java.util.List;

public interface PersonalTrainingRepository {
    List<PersonalTraining> findAllActive();
    List<PersonalTraining> findAllArchived();
    List<PersonalTraining> findByInstructorId(int instructorId);
    PersonalTraining findById(int id);
    boolean save(PersonalTraining pt);
    int saveAndReturnId(PersonalTraining pt);
    boolean update(PersonalTraining pt);
    boolean archiveById(int id);
    boolean restoreById(int id);
    boolean deleteById(int id);
    List<PersonalTraining> searchByKeyword(String keyword);
}