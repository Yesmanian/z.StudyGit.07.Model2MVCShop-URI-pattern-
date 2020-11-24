package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;

@Repository("purchaseDaoImpl")
public class PurchaseDaoImpl implements PurchaseDao {
	
	//Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	//Setter
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("::"+getClass()+"sqlSession() Call...");
		this.sqlSession = sqlSession;
	}
	
	public PurchaseDaoImpl() {
		System.out.println("::"+getClass()+" default Constructor Call..");
	}
	@Override
	public void insertPurchase(Purchase purchase) throws Exception {
		sqlSession.insert("PurchaseMapper.addPurchase", purchase);
	}


	@Override
	public List<Purchase> getPurchaseList(Search search, String buyerId) throws Exception {
		Map map = new HashMap();
		
		map.put("search", search);
		map.put("buyerId", buyerId);
		
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	@Override
	public Purchase findPurchase(int tranNo) throws Exception {

		return sqlSession.selectOne("PurchaseMapper.getPurchase1", tranNo);
	}

	@Override
	public Purchase findPurchase2(int prodNo) throws Exception {
		return sqlSession.selectOne("PurchaseMapper.getPurchase2", prodNo);
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		sqlSession.update("PurchaseMapper.updatePurchase", purchase);

	}

	@Override
	public void updateTranCode(Purchase purchase, String tranCode) throws Exception {
		
		Map map = new HashMap();
		
		map.put("purchase", purchase);
		map.put("tranCode", tranCode);
		
		sqlSession.update("PurchaseMapper.updateTranCode", map);

	}

	@Override
	public int getTotalCount(String buyerId) throws Exception {
		
		
		return sqlSession.selectOne("PurchaseMapper.getTotalCount", buyerId);
	}
	
	

}
