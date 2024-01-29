package com.project.inventory.services;

import com.project.inventory.model.Category;
import com.project.inventory.response.CategoryResponseREST;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    public ResponseEntity<CategoryResponseREST> search();

    public ResponseEntity<CategoryResponseREST> searchById(Long id);

    public ResponseEntity<CategoryResponseREST> save(Category category);

    public ResponseEntity<CategoryResponseREST> update(Long id, Category category);
}
