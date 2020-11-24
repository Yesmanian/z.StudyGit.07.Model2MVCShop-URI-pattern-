package com.model2.mvc.service.product.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
@Repository("productDaoImpl")
public class ProductDaoImpl implements ProductDao {
	
	//Field
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	
	//setter (없어도 알아서 settet만들어서 돌아가는거같다)
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("::"+getClass()+"sqlSeeion() call...");
		this.sqlSession = sqlSession;
	}
	//Constructor
	public ProductDaoImpl() {
		System.out.println("::"+getClass()+" default Constructor Call..");
	}

	@Override
	public void addProduct(Product product) throws Exception {
		System.out.println("1st");
		sqlSession.insert("ProductMapper.addProduct",product);
//		System.out.println("2st");
//		sqlSession.insert("ProductMapper.addProduct",product);
	}

	@Override
	public Product getProduct(int prodNo) throws Exception {
		System.out.println("ProductDao getProduct");
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}

	@Override
	public List<Product> getProductList(Search search) throws Exception {
		
		return sqlSession.selectList("ProductMapper.getProductList",search);
	}

	@Override
	public void updateProduct(Product product) throws Exception {
		sqlSession.update("ProductMapper.updateProduct",product);

	}
	@Override
	public int getTotalCount(Search search) throws Exception {
		return sqlSession.selectOne("ProductMapper.getTotalCount", search);
	}

}
