package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.TrainingBooking;
import java.util.List;

public interface TrainingBookingRepository {
    List<TrainingBooking> findAllActive();
    List<TrainingBooking> findAllArchived();
    List<TrainingBooking> findByUserId(int userId);
    List<TrainingBooking> findByInstructorId(int instructorId);
    TrainingBooking findById(int id);
    boolean save(TrainingBooking booking);
    int saveAndReturnId(TrainingBooking booking);
    boolean updateStatus(int id, String status);
    boolean archiveById(int id);
    boolean restoreById(int id);
    boolean deleteById(int id);
}