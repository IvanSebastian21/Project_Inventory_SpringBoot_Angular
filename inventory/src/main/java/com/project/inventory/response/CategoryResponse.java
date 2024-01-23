package com.project.inventory.response;

import com.project.inventory.model.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    private List<Category> categoryList;
}
