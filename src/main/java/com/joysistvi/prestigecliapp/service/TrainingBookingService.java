package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.TrainingBooking;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface TrainingBookingService {
    List<TrainingBooking> getAllActive();
    List<TrainingBooking> getAllArchived();
    List<TrainingBooking> getByUserId(int userId);
    List<TrainingBooking> getByInstructorId(int instructorId);
    //TrainingBooking getById(int id);
    boolean bookSession(int userId, int personalTrainingId, Date bookingDate, Time startTime, Time endTime);
    boolean updateStatus(int id, String status);
    boolean cancelBooking(int id);
    boolean archive(int id);
    boolean restore(int id);
    boolean delete(int id);
}