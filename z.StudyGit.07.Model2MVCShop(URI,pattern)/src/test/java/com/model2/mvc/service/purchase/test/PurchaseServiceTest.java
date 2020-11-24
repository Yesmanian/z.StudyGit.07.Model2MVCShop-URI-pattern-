package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	//@Test
	public void testGetPuchase() throws Exception {
		
		Purchase purchase = new Purchase();
		
		
		User user = new User();
		user.setUserId("user21");
		user.setUserName("SCOTT");
		
		Product product  = new Product();
		product.setProdNo(10041);
//		purchase.setBuyer(user);
//		purchase.setDivyAddr("중량천");
//		purchase.setDivyDate("20201116");
//		purchase.setDivyRequest("햄스터는 속도생명");
//		purchase.setPaymentOption("1");
		purchase.setPurchaseProd(product);
//		purchase.setReceiverName("햄스터킹");
//		purchase.setReceiverPhone("999");
		
		purchase.setTranNo(10080);
		purchase.setBuyer(user);
		
		purchase =purchaseService.getPurchase2(10041);
		System.out.println(purchase);
		Assert.assertEquals(10041, purchase.getPurchaseProd().getProdNo());
		
		
		
		
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception {
		
		Purchase purchase = purchaseService.getPurchase(10100);
		
		User user = new User();
		user.setUserId("user21");
		user.setUserName("SCOTT");
		
		Product product  = new Product();
		product.setProdNo(10040);
		
		System.out.println(purchase);
		Assert.assertNotNull(purchase);
		
		Assert.assertEquals("킹햄스터update",purchase.getReceiverName());
		
		purchase.setReceiverName("킹햄스터update");
		purchase.setBuyer(user);
		purchase.setDivyAddr("중량천");
		purchase.setDivyDate("20201116");
		purchase.setDivyRequest("햄스터는 속도생명");
		purchase.setPaymentOption("1");
		purchase.setPurchaseProd(product);
		purchase.setReceiverPhone("999");
		purchaseService.updatePurchase(purchase);
		purchaseService.updateTranCode(purchase, "2");
		
		purchase = purchaseService.getPurchase(10100);
		System.out.println(purchase.getReceiverName());
		System.out.println(purchase.getTranCode());
		Assert.assertEquals("킹햄스터update",purchase.getReceiverName());
		//"2  "으로 들어옴
		Assert.assertEquals("2  ",purchase.getTranCode());
		
	}
	@Test
	public void testPurchaseList() throws Exception {
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	
	 	String buyerId = "user21";
	 	Map<String,Object> map = purchaseService.getPurchaseList(search, buyerId);
		
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	
	}
	
}
