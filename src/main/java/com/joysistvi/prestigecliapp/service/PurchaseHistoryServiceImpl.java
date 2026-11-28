package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.PurchaseHistory;
import com.joysistvi.prestigecliapp.repository.PurchaseHistoryRepository;

import java.util.List;

public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    private final PurchaseHistoryRepository purchaseHistoryRepository;

    public PurchaseHistoryServiceImpl(PurchaseHistoryRepository purchaseHistoryRepository) {
        this.purchaseHistoryRepository = purchaseHistoryRepository;
    }

    @Override
    public List<PurchaseHistory> findAll() {
        return purchaseHistoryRepository.findAll();
    }

    @Override
    public PurchaseHistory findById(int id) {
        return purchaseHistoryRepository.findById(id);
    }

    @Override
    public List<PurchaseHistory> findByMembershipCardId(int membershipCardId) {
        return purchaseHistoryRepository.findByMembershipCardId(membershipCardId);
    }

    @Override
    public PurchaseHistory findByPurchaseReference(String purchaseReference) {
        return purchaseHistoryRepository.findByPurchaseReference(purchaseReference);
    }

    @Override
    public List<PurchaseHistory> findByPurchaseType(String purchaseType) {
        return purchaseHistoryRepository.findByPurchaseType(purchaseType);
    }

    @Override
    public List<PurchaseHistory> search(String keyword) {
        return purchaseHistoryRepository.search(keyword);
    }

    @Override
    public boolean save(PurchaseHistory purchaseHistory) {
        return purchaseHistoryRepository.save(purchaseHistory);
    }

    @Override
    public boolean updateStatus(int id, String status) {
        return purchaseHistoryRepository.updateStatus(id, status);
    }

}