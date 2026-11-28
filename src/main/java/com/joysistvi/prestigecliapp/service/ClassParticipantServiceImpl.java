package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.ClassParticipant;
import com.joysistvi.prestigecliapp.repository.ClassParticipantRepository;

import java.util.List;

public class ClassParticipantServiceImpl implements ClassParticipantService {

    private final ClassParticipantRepository classParticipantRepository;

    public ClassParticipantServiceImpl(ClassParticipantRepository classParticipantRepository) {
        this.classParticipantRepository = classParticipantRepository;
    }

    @Override
    public List<ClassParticipant> findAll() {
        return classParticipantRepository.findAll();
    }

    @Override
    public ClassParticipant findById(int id) {
        return classParticipantRepository.findById(id);
    }

    @Override
    public List<ClassParticipant> findByUserId(int userId) {
        return classParticipantRepository.findByUserId(userId);
    }

    @Override
    public List<ClassParticipant> findByFitnessClassId(int fitnessClassId) {
        return classParticipantRepository.findByFitnessClassId(fitnessClassId);
    }

    @Override
    public boolean add(ClassParticipant classParticipant) {
        return classParticipantRepository.add(classParticipant);
    }

    @Override
    public boolean remove(int id) {
        return classParticipantRepository.remove(id);
    }

    @Override
    public boolean archive(int id) {
        return classParticipantRepository.archive(id);
    }

    @Override
    public boolean restore(int id) {
        return classParticipantRepository.restore(id);
    }

    @Override
    public boolean updateStatus(int id, String status) {
        return classParticipantRepository.updateStatus(id, status);
    }

}