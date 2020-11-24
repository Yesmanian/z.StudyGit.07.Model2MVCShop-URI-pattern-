package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;
@Repository("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	
	//Field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	
	
	
	//Setter
	public void setProductDao(ProductDao productDao) {
		System.out.println("::"+getClass().getName()+"setProductDao() Call..");
		this.productDao = productDao;
	}
	
	//Constructor
	public ProductServiceImpl() {
		System.out.println("::"+getClass()+"default Constructor..");
	}
	
	//Method
	@Override
	public void addProduct(Product product) throws Exception {
		productDao.addProduct(product);

	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		
		System.out.println("productServiceImpl getProduct");
		return productDao.getProduct(prodNo);
	}

	@Override
	public Map<String, Object> getProductList(Search search) throws Exception {
		List<Product> list  = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", totalCount);
		return map;
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		productDao.updateProduct(product);

	}

}
