package com.iset.produits.service;

import com.iset.produits.dao.CategorieRepository;
import com.iset.produits.entities.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImp implements CategorieService{
    @Autowired
    private CategorieRepository categorieRepository;
    @Override
    public List<Categories> getAllCategories() {
        return categorieRepository.findAll();
    }

    @Override
    public Categories getCategoriesById(long id) {
        return categorieRepository.findById(id).orElse(null);
    }

    @Override
    public Categories getCategoriesByName(String name) {
        return categorieRepository.findAll()
                .stream()
                .filter(categories -> categories.getNomCat().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    @Override
    public Categories saveCategories(Categories c) {
        return categorieRepository.save(c);
    }

    @Override
    public Categories updateCategories(Categories c) {
        if (categorieRepository.existsById(c.getIdCat())){
            return categorieRepository.save(c);
        }
        return null;
    }

    @Override
    public void deleteCategories(Categories c) {
        categorieRepository.delete(c);
    }
}
