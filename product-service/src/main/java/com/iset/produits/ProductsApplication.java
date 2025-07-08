package com.iset.produits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iset.produits.service.*;



@SpringBootApplication
public class ProductsApplication implements CommandLineRunner{
	@Autowired
	private ProduitServiceImpl service;
public static void main(String[] args) {
		SpringApplication.run(ProductsApplication.class, args);
	
	}

@Override
public void run(String... args) throws Exception {
	// TODO Auto-generated method stub

	System.out.print("app run");
	
}}
	


