package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.PersonalTraining;
import com.joysistvi.prestigecliapp.model.TrainingBooking;
import com.joysistvi.prestigecliapp.repository.PersonalTrainingRepository;
import com.joysistvi.prestigecliapp.repository.TrainingBookingRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TrainingBookingServiceImpl implements TrainingBookingService {
    private final TrainingBookingRepository bookingRepository;
    private final PersonalTrainingRepository ptRepository;

    public TrainingBookingServiceImpl(TrainingBookingRepository bookingRepository,
                                      PersonalTrainingRepository ptRepository) {
        this.bookingRepository = bookingRepository;
        this.ptRepository = ptRepository;
    }

    @Override
    public List<TrainingBooking> getAllActive() {
        return bookingRepository.findAllActive();
    }

    @Override
    public List<TrainingBooking> getAllArchived() {
        return bookingRepository.findAllArchived();
    }

    @Override
    public List<TrainingBooking> getByUserId(int userId) {
        if (userId <= 0) return new ArrayList<>();
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<TrainingBooking> getByInstructorId(int instructorId) {
        if (instructorId <= 0) return new ArrayList<>();
        return bookingRepository.findByInstructorId(instructorId);
    }

    @Override
    public TrainingBooking getById(int id) {
        if (id <= 0) return null;
        return bookingRepository.findById(id);
    }

    @Override
    public boolean bookSession(int userId, int personalTrainingId, Date bookingDate, Time startTime, Time endTime) {
        if (userId <= 0 || personalTrainingId <= 0) return false;
        if (bookingDate == null || startTime == null || endTime == null) return false;
        if (!endTime.after(startTime)) return false;

        PersonalTraining pt = ptRepository.findById(personalTrainingId);
        if (pt == null || pt.getHourlyRate() == null) return false;

        long minutes = Duration.between(startTime.toLocalTime(), endTime.toLocalTime()).toMinutes();
        BigDecimal totalHours = BigDecimal.valueOf(minutes)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = totalHours.multiply(pt.getHourlyRate()).setScale(2, RoundingMode.HALF_UP);

        TrainingBooking booking = new TrainingBooking(
                userId,
                personalTrainingId,
                bookingDate,
                startTime,
                endTime,
                totalHours,
                totalAmount,
                "CONFIRMED"
        );
        if (!bookingRepository.save(booking)) {
            return false;
        }

        // Refund MembershipCard balance and log PurchaseHistory once those modules are ready
        return true;
    }

    @Override
    public boolean updateStatus(int id, String status) {
        if (id <= 0 || status == null || status.trim().isEmpty()) return false;
        return bookingRepository.updateStatus(id, status.trim().toUpperCase());
    }

    @Override
    public boolean cancelBooking(int id) {
        if (id <= 0) return false;
        TrainingBooking tb = bookingRepository.findById(id);
        if (tb == null) return false;

        if (!bookingRepository.updateStatus(id, "CANCELLED")) {
            return false;
        }

        // Refund MembershipCard balance and log PurchaseHistory once those modules are ready
        return true;
    }

    @Override
    public boolean archive(int id) {
        if (id <= 0) return false;
        return bookingRepository.archiveById(id);
    }

    @Override
    public boolean restore(int id) {
        if (id <= 0) return false;
        return bookingRepository.restoreById(id);
    }

    @Override
    public boolean delete(int id) {
        if (id <= 0) return false;
        return bookingRepository.deleteById(id);
    }
}