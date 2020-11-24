package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;
@Repository("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	
	//Field
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	//Setter
	public void setPurchaseDao(PurchaseDao purchaseDao) {
		System.out.println("::"+getClass().getName()+"setPurchaseDao() Call..");
		this.purchaseDao = purchaseDao;
	}
	//Constructor
	public PurchaseServiceImpl() {
		System.out.println("::"+getClass()+"default Constructor..");
	}
	
	
	@Override
	public void addPurchase(Purchase purchase) throws Exception {
		purchaseDao.insertPurchase(purchase);

	}


	@Override
	public Purchase getPurchase(int tranNo) throws Exception {
		
		return purchaseDao.findPurchase(tranNo);
	}

	@Override
	public Purchase getPurchase2(int ProdNo) throws Exception {
		return purchaseDao.findPurchase2(ProdNo);
	}

	@Override
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {

		List<Purchase> list = purchaseDao.getPurchaseList(search, buyerId);
		int totalCount = purchaseDao.getTotalCount(buyerId);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		return map;
	}

	@Override
	public Map<String, Object> getSaleList(Search searchVO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);

	}

	@Override
	public void updateTranCode(Purchase purchase, String tranCode) throws Exception {
		purchaseDao.updateTranCode(purchase, tranCode);

	}

}
