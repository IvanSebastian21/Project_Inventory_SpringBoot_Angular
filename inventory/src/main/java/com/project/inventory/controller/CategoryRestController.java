package com.project.inventory.controller;

import com.project.inventory.model.Category;
import com.project.inventory.response.CategoryResponseREST;
import com.project.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class CategoryRestController {

    @Autowired
    private ICategoryService iCategoryService;

    @GetMapping("/categories")
    public ResponseEntity<CategoryResponseREST> searchCategories() {
        ResponseEntity<CategoryResponseREST> response = iCategoryService.search();
        return response;
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseREST> searchCategoryById(@PathVariable Long id) {
        ResponseEntity<CategoryResponseREST> response = iCategoryService.searchById(id);
        return response;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseREST> save(@RequestBody Category category) {
        ResponseEntity<CategoryResponseREST> response = iCategoryService.save(category);
        return response;
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseREST> update(@RequestBody Category category, @PathVariable Long id) {
        ResponseEntity<CategoryResponseREST> response = iCategoryService.update(id, category);
        return response;
    }
}
