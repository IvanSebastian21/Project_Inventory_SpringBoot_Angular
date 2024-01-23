package com.project.inventory.services;

import com.project.inventory.response.CategoryResponseREST;
import org.springframework.http.ResponseEntity;

public interface ICategoryService {
    public ResponseEntity<CategoryResponseREST> search();

}
