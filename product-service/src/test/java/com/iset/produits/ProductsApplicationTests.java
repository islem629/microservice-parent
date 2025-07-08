package com.iset.produits;

import com.iset.produits.controller.ProductController;
import com.iset.produits.dao.ProduitRepository;
import com.iset.produits.entities.Categories;
import com.iset.produits.entities.Produit;
import com.iset.produits.service.CategorieService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Add this to make all tests transactional
public class ProductsApplicationTests {

	@Container
	public static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:5.7.34")
			.withDatabaseName("testdb")
			.withUsername("testuser")
			.withPassword("testpass");

	@Autowired
	private MockMvc mvc;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private ProduitRepository produitRepository;

	@Autowired
	private CategorieService categorieService;

	@DynamicPropertySource
	static void registerMySQLProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mysql::getJdbcUrl);
		registry.add("spring.datasource.username", mysql::getUsername);
		registry.add("spring.datasource.password", mysql::getPassword);
	}

	@BeforeEach
	void setup() {
		// Insert test categories into the database
		entityManager.createNativeQuery("INSERT INTO categories (id_cat, nom_cat) VALUES (1, 'Electronics'), (2, 'Wireless');").executeUpdate();
	}

	@Test
	void SHOULD_CREATE_ALL_PRODUCTS() throws Exception {
		mvc.perform(get("/createProduit"))
				.andExpect(status().isOk())
				.andExpect(view().name("createProduit"))
				.andExpect(model().attributeExists("categories"));
	}

	@Test
	void shouldSaveProduitandRedirectToList() throws Exception {
		// Get existing category
		Categories category = categorieService.getAllCategories().get(0);

		mvc.perform(post("/saveProduit")
						.param("nomProduit", "testContainer")
						.param("prixProduit", "200.0")
						.param("categorie.idCat", category.getIdCat().toString()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/ListeProduits"));

		List<Produit> saved =  produitRepository.findByNomProduit("testContainer");
		assertNotNull(saved);
		assertEquals(200.0, saved.get(0).getPrixProduit());
	}
}