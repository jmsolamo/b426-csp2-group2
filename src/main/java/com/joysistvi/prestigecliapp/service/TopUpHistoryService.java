package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.TopUpHistory;
import java.math.BigDecimal;
import java.util.List;

public interface TopUpHistoryService {
    List<TopUpHistory> getAll();
    List<TopUpHistory> getByUserId(int userId);
    List<TopUpHistory> getPending();
    TopUpHistory getById(int id);
    boolean submitTopup(int userId, String paymentMethod, String paymentReference, BigDecimal amount);
    boolean completeTopup(int id);
    boolean failTopup(int id);
    boolean refundTopup(int id);
    boolean delete(int id);
}