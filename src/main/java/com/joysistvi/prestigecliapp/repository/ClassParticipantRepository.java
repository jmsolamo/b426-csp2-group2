package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.ClassParticipant;

import java.util.List;

public interface ClassParticipantRepository {

    public List<ClassParticipant> findAll();
    public ClassParticipant findById(int id);
    public List<ClassParticipant> findByUserId(int userId);
    public List<ClassParticipant> findByFitnessClassId(int fitnessClassId);
    public boolean add(ClassParticipant classParticipant);
    public boolean remove(int id);
    public boolean archive(int id);
    public boolean restore(int id);
    public boolean updateStatus(int id, String status);

}