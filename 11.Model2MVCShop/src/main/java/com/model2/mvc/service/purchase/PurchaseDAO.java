package com.model2.mvc.service.purchase;

import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;


public interface PurchaseDAO {
	
	
	public void insertPurchase(Purchase purchase);
	
	public Purchase findPurchase(int tranNo) throws Exception;
	
	public List<Purchase> getPurchaseList(Search search, String userId) throws Exception;
	
//	public Map<String, Object> getSaleList(Search search);
	
	public void updatePurchase(Purchase purchase);
	
	public void updateTranCode(Purchase purchase);
	
	public int getTotalCount(String sql) throws Exception;
	
}