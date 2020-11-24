package com.model2.mvc.service.product.test;

import java.util.List;
import java.util.Map;

import javax.xml.crypto.KeySelector.Purpose;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {
	
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//@Test
	public void testGetProduct() throws Exception {
		
		
		Product product = new Product();
//		product.setProdName("Chocolate");
//		product.setProdDetail("amond chocho");
//		product.setManuDate("20201116");
//		product.setPrice(5000);
//		product.setFileName("강사님이 주신 초코렛");
//		
//		productService.addProduct(product);
		
		product.setProdNo(10120);
		int prodNo = product.getProdNo();
		product = productService.getProduct(prodNo);
		
		Assert.assertEquals(10120, product.getProdNo());
		Assert.assertEquals("Chocolate", product.getProdName());
		Assert.assertEquals("amond chocho", product.getProdDetail());
		Assert.assertEquals("20201116", product.getManuDate());
		Assert.assertEquals(5000, product.getPrice());
		Assert.assertEquals("강사님이 주신 초코렛", product.getFileName());
		
	}
	
	//@Test
	public void testUpdateProduct() throws Exception {
		int prodNo = 10120;
		Product product = productService.getProduct(prodNo);
		
		Assert.assertNotNull(product);
		
		Assert.assertEquals("Chocolate", product.getProdName());
		Assert.assertEquals("amond chocho", product.getProdDetail());
		
		product.setProdDetail("강사님이 주신 아몬드 초코릿");
		
		productService.updateProduct(product);
		
		product = productService.getProduct(prodNo);
		Assert.assertNotNull(product);
		
		System.out.println(product);
		
		Assert.assertEquals("강사님이 주신 아몬드 초코릿", product.getProdDetail());
		
	}
	//@Test
	public void testGetProductListAll() throws Exception {
		
		Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = productService.getProductList(search);
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	System.out.println(list);
	 	
	 	Integer totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	 	System.out.println("=======================================");
	 	
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	search.setSearchCondition("0");
	 	search.setSearchKeyword("");
	 	map = productService.getProductList(search);
	 	
	 	list = (List<Object>)map.get("list");
	 	Assert.assertEquals(3, list.size());
	 	
	 	//==> console 확인
	 	System.out.println(list);
	 	
	 	totalCount = (Integer)map.get("totalCount");
	 	System.out.println(totalCount);
	 	
	}
	@Test
	public void testGetProductListByprodName() throws Exception {
		
		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("1");
	 	search.setSearchKeyword("보");
		Map<String,Object> map = productService.getProductList(search);
		
		List<Object> list = (List<Object>)map.get("list");
		Assert.assertEquals(2, list.size());
		System.out.println(list);
		
		Integer totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
		
		System.out.println("=======================================");
		
		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = productService.getProductList(search);
		
		list = (List<Object>)map.get("list");
		Assert.assertEquals(3, list.size());
		
		//==> console 확인
		System.out.println(list);
		
		totalCount = (Integer)map.get("totalCount");
		System.out.println(totalCount);
		
	}
	
	
	
}
