package com.iset.produits.controller;

import com.iset.produits.entities.Categories;
import com.iset.produits.entities.Produit;
import com.iset.produits.service.CategorieService;
import com.iset.produits.service.CategoriesServiceImp;
import com.iset.produits.service.ProduitService;
import com.iset.produits.service.ProduitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits") // This will work with /api/product route in your gateway
public class ProductController {

    @Autowired
    private ProduitServiceImpl produitService;

    @Autowired
    private CategoriesServiceImp categorieService;

    // Get all products (paginated)
    @GetMapping
    public List<Produit> getProduits( ){
        return produitService.getAllProduitsParPage();
    }

    // Get single product by ID
    @GetMapping("/{id}")
    public Produit getProduitById(@PathVariable Long id) {
        return produitService.getProduit(id);
    }

    // Create new product
    @PostMapping
    public Produit createProduit(@RequestBody Produit produit) {
        return produitService.saveProduit(produit);
    }

    // Update existing product
    @PutMapping("/{id}")
    public Produit updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        produit.setIdProduit(id);
        return produitService.saveProduit(produit);
    }

    // Delete product by ID
    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitService.deleteProduitById(id);
    }

    // Search by ID (same as get by ID, for backward compatibility)
    @GetMapping("/search")
    public Produit searchProduit(@RequestParam("id") Long id) {
        return produitService.getProduit(id);
    }

    // Get all categories
    @GetMapping("/categories")
    public List<Categories> getCategories() {
        return categorieService.getAllCategories();
    }

    // Save a category (optional, only if needed)
    @PostMapping("/categories")
    public Categories saveCategory(@RequestBody Categories category) {
        return categorieService.saveCategories(category);
    }
}
