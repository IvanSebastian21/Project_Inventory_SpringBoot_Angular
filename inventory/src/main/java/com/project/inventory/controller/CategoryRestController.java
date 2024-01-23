package com.project.inventory.controller;

import com.project.inventory.response.CategoryResponseREST;
import com.project.inventory.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
