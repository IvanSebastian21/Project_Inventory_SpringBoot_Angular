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
        return iCategoryService.search();
    }

    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseREST> searchCategoryById(@PathVariable Long id) {
        return iCategoryService.searchById(id);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponseREST> save(@RequestBody Category category) {
        return iCategoryService.save(category);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseREST> update(@RequestBody Category category, @PathVariable Long id) {
        return iCategoryService.update(id, category);
    }
    @DeleteMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseREST> update(@PathVariable Long id) {
        return iCategoryService.delete(id);
    }
}
