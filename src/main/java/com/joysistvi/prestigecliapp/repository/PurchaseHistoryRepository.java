
package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.PurchaseHistory;
import java.util.List;

public interface PurchaseHistoryRepository {

    public List<PurchaseHistory> findAll();
    public PurchaseHistory findById(int id);
    public List<PurchaseHistory> findByMembershipCardId(int membershipCardId);
    public PurchaseHistory findByPurchaseReference(String purchaseReference);
    public List<PurchaseHistory> findByPurchaseType(String purchaseType);
    public List<PurchaseHistory> search(String keyword);
    public boolean save(PurchaseHistory purchaseHistory);
    public boolean updateStatus(int id, String status);

}
