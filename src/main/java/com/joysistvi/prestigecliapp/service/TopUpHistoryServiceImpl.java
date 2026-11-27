package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.TopUpHistory;
import com.joysistvi.prestigecliapp.repository.TopUpHistoryRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TopUpHistoryServiceImpl implements TopUpHistoryService {
    private final TopUpHistoryRepository topupRepository;

    public TopUpHistoryServiceImpl(TopUpHistoryRepository topupRepository) {
        this.topupRepository = topupRepository;
    }

    @Override
    public List<TopUpHistory> getAll() {
        return topupRepository.findAll();
    }

    @Override
    public List<TopUpHistory> getByUserId(int userId) {
        if (userId <= 0) return new ArrayList<>();
        return topupRepository.findByUserId(userId);
    }

    @Override
    public List<TopUpHistory> getPending() {
        return topupRepository.findByStatus("PENDING");
    }

    @Override
    public TopUpHistory getById(int id) {
        if (id <= 0) return null;
        return topupRepository.findById(id);
    }

    @Override
    public boolean submitTopup(int userId, String paymentMethod, String paymentReference, BigDecimal amount) {
        if (userId <= 0) return false;
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (paymentMethod == null || paymentMethod.trim().isEmpty()) return false;

        int cardId = topupRepository.findActiveCardIdByUserId(userId);
        if (cardId <= 0) return false;

        String normMethod = paymentMethod.trim().toUpperCase();
        String payRef = (paymentReference != null && !paymentReference.trim().isEmpty()) ? paymentReference.trim() : null;
        String tempRef = "TEMP-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        TopUpHistory th = new TopUpHistory(cardId, tempRef, payRef, amount, normMethod, "PENDING");
        return topupRepository.save(th);
    }

    @Override
    public boolean completeTopup(int id) {
        if (id <= 0) return false;
        TopUpHistory th = topupRepository.findById(id);
        if (th == null) return false;

        return topupRepository.completeTopupAtomically(id, th.getMembershipCardId(), th.getAmount());
    }

    @Override
    public boolean failTopup(int id) {
        if (id <= 0) return false;
        TopUpHistory th = topupRepository.findById(id);
        if (th == null) return false;

        return topupRepository.updateStatus(id, "FAILED");
    }

    @Override
    public boolean refundTopup(int id) {
        if (id <= 0) return false;
        TopUpHistory th = topupRepository.findById(id);
        if (th == null) return false;

        return topupRepository.refundTopupAtomically(id, th.getMembershipCardId(), th.getAmount());
    }

    @Override
    public boolean delete(int id) {
        if (id <= 0) return false;
        return topupRepository.deleteById(id);
    }
}