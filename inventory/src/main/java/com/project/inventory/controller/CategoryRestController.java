package com.project.inventory.controller;

import com.project.inventory.model.Category;
import com.project.inventory.response.CategoryResponseREST;
import com.project.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

//    @PutMapping("/categories/{id}")
//    public ResponseEntity<CategoryResponseREST> save(@RequestBody Category category, @PathVariable Long id) {
//        ResponseEntity<CategoryResponseREST> response = iCategoryService.save(category);
//        return response;
//    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryResponseREST> updateCategory(@PathVariable Long id, @RequestBody Category updatedCategory) {
        try {
            ResponseEntity<CategoryResponseREST> searchResponse = iCategoryService.searchById(id);

            if (searchResponse.getStatusCode() == HttpStatus.OK) {
                Category existingCategory = searchResponse.getBody().getCategoryResponse().getCategoryList().get(0);

                existingCategory.setName(updatedCategory.getName());
                existingCategory.setDescription(updatedCategory.getDescription());

                ResponseEntity<CategoryResponseREST> updateResponse = iCategoryService.save(existingCategory);

                return updateResponse;
            } else {
                CategoryResponseREST errorResponse = new CategoryResponseREST();
                errorResponse.setMetadata("Error", "304", "Error: no se ha realizado ninguna modificación");
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            CategoryResponseREST errorResponse = new CategoryResponseREST();
            errorResponse.setMetadata("Error", "500", "Error en Respuesta");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
