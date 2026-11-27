package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.TopUpHistory;
import java.math.BigDecimal;
import java.util.List;

public interface TopUpHistoryRepository {
    List<TopUpHistory> findAll();
    List<TopUpHistory> findByCardId(int membershipCardId);
    List<TopUpHistory> findByUserId(int userId);
    List<TopUpHistory> findByStatus(String status);
    TopUpHistory findById(int id);
    int findActiveCardIdByUserId(int userId);
    boolean save(TopUpHistory topup);
    int saveAndReturnId(TopUpHistory topup);
    boolean updateStatus(int id, String status);
    boolean completeTopupAtomically(int topupId, int cardId, BigDecimal amount);
    boolean refundTopupAtomically(int topupId, int cardId, BigDecimal amount);
    boolean deleteById(int id);
}