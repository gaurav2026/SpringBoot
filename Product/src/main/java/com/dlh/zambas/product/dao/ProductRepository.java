package com.dlh.zambas.product.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dlh.zambas.product.model.Product;

public interface ProductRepository extends CrudRepository<Product, String>{

	@Query("select p from Product p where p.id = :id")
	public Product findProductById(@Param("id") String id);
	
	@Modifying
	@Query("update Product p set p.comments=:comments where p.id = :id")
	@Transactional
	public void updateProduct(@Param("id") String id,@Param("comments") String comments);
}
