package com.dlh.zambas.product.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dlh.zambas.product.model.Comments;
import com.dlh.zambas.product.model.Product;
import com.dlh.zambas.product.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@RequestMapping("/get/products")
	public List<Product> getAllProducts() {
		return productService.getAllProducts();
	}

	@RequestMapping("/get/products/{id}")
	public Product getProductsById(@PathVariable("id") String id) {
		return productService.getProductById(id);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add/products")
	public void addProduct(@RequestBody List<Product> topic) {
		productService.addProduct(topic);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/products/comments")
	public Map<String, String> postComments(@RequestBody Comments comments) {
		return productService.postComment(comments);
	}

}
