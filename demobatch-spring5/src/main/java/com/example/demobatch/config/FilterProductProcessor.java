package com.example.demobatch.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.example.demobatch.model.Product;

//process item before writing to MongoDB
@Component
public class FilterProductProcessor implements ItemProcessor<Product, Product> {

	@Override
	public Product process(Product item) throws Exception {
		if(item.getName().contains("product")) {
			item.setName(item.getName().replace("product", "newproduct"));
			return item;
		}
		return item;
	}

}