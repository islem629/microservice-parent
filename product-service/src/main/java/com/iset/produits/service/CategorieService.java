package com.iset.produits.service;

import com.iset.produits.entities.Categories;

import java.util.List;

public interface CategorieService {
    List<Categories> getAllCategories();
    Categories getCategoriesById(long id);
    Categories getCategoriesByName(String name);
    Categories saveCategories(Categories c);
    Categories updateCategories(Categories c);
    void deleteCategories(Categories c);

}
