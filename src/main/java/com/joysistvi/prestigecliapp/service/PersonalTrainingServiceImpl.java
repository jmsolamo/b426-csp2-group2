package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.PersonalTraining;
import com.joysistvi.prestigecliapp.repository.PersonalTrainingRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PersonalTrainingServiceImpl implements PersonalTrainingService {
    private final PersonalTrainingRepository ptRepository;

    public PersonalTrainingServiceImpl(PersonalTrainingRepository ptRepository) {
        this.ptRepository = ptRepository;
    }

    @Override
    public List<PersonalTraining> getAllActive() {
        return ptRepository.findAllActive();
    }

    @Override
    public List<PersonalTraining> getAllArchived() {
        return ptRepository.findAllArchived();
    }

    @Override
    public List<PersonalTraining> getByInstructorId(int instructorId) {
        if (instructorId <= 0) return new ArrayList<>();
        return ptRepository.findByInstructorId(instructorId);
    }

    @Override
    public PersonalTraining getById(int id) {
        if (id <= 0) return null;
        return ptRepository.findById(id);
    }

    @Override
    public boolean create(int instructorId, String category, String description, BigDecimal hourlyRate) {
        if (instructorId <= 0) return false;
        if (category == null || category.trim().isEmpty()) return false;
        if (hourlyRate == null || hourlyRate.compareTo(BigDecimal.ZERO) <= 0) return false;

        // Validate instructor role via UserRepository once ready
        PersonalTraining pt = new PersonalTraining(
                instructorId,
                category.trim(),
                description != null ? description.trim() : null,
                hourlyRate
        );
        return ptRepository.save(pt);
    }

    @Override
    public boolean update(int id, int instructorId, String category, String description, BigDecimal hourlyRate) {
        if (id <= 0 || instructorId <= 0) return false;
        if (category == null || category.trim().isEmpty()) return false;
        if (hourlyRate == null || hourlyRate.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (ptRepository.findById(id) == null) return false;

        // Validate instructor role via UserRepository once ready
        PersonalTraining pt = new PersonalTraining(
                instructorId,
                category.trim(),
                description != null ? description.trim() : null,
                hourlyRate
        );
        pt.setId(id);
        return ptRepository.update(pt);
    }

    @Override
    public boolean archive(int id) {
        if (id <= 0) return false;
        return ptRepository.archiveById(id);
    }

    @Override
    public boolean restore(int id) {
        if (id <= 0) return false;
        return ptRepository.restoreById(id);
    }

    @Override
    public boolean delete(int id) {
        if (id <= 0) return false;
        return ptRepository.deleteById(id);
    }

    @Override
    public List<PersonalTraining> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) return new ArrayList<>();
        return ptRepository.searchByKeyword(keyword.trim());
    }
}