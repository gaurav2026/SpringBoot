package com.dlh.zambas.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dlh.zambas.product.dao.ProductRepository;
import com.dlh.zambas.product.model.Comments;
import com.dlh.zambas.product.model.Product;
import com.dlh.zambas.product.validate.comments.ValidateComments;
import com.google.common.collect.Lists;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	
	public void addProduct(List<Product> product){
		for(Product pro : product){
			productRepository.save(pro);
		}
	}
	
	public Product getProductById(String id){
		 return productRepository.findProductById(id);
	}
	
	public Map<String,String> postComment(Comments comments){
		ValidateComments validateComments = new ValidateComments();
		//Map<String,String> map = validateComments.validateComments(comments,Sets.newHashSet(objectionableContentRepository.findAll()));
		Map<String,String> map = validateComments.validateComments(comments);
		if(!validateComments.isCussWordUsed()){
			productRepository.updateProduct(comments.getId(), comments.getComments());
		}
		return map;
		
	}

	public List<Product> getAllProducts() {
		return  Lists.newArrayList(productRepository.findAll());
	}

		
}
