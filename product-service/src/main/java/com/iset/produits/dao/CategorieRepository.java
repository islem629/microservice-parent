package com.iset.produits.dao;

import com.iset.produits.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categories, Long> {
    Categories findByNomCat(String nomCat);
}
