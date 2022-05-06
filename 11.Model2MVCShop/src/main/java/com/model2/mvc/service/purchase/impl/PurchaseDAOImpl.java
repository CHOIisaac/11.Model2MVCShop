package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseDAO;

@Repository("purchaseDAOImpl")
public class PurchaseDAOImpl implements PurchaseDAO{
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public PurchaseDAOImpl() {
		System.out.println(this.getClass());
	}
	
	public void insertPurchase(Purchase purchase) {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}
	
	public Purchase findPurchase(int tranNo) throws Exception{
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}
	
	public List<Purchase> getPurchaseList(Search search, String userId) throws Exception{
		Map<String, String> map = new HashMap<String, String>();

		
		List<Purchase> list = sqlSession.selectList("PurchaseMapper.getPurchaseList", map); 
		for (Purchase purchase : list) {
			Product product = sqlSession.selectOne("ProductMapper.getProduct", purchase.getPurchaseProd().getProdNo());
			User user = sqlSession.selectOne("UserMapper.getUser", purchase.getBuyer().getUserId());
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			if(purchase.getTranCode() != null) { 
				purchase.setTranCode(purchase.getTranCode().trim());
			}
		}
		
		
		return list;
	}
	
//	public Map<String, Object> getSaleList(Search search);
	
	public void updatePurchase(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);
	}
	
	public void updateTranCode(Purchase purchase) {
		sqlSession.update("PurchaseMapper.updateTranCode", purchase);
		
	}
	
	public int getTotalCount(String userId) throws Exception{
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", userId);
	}
	
}