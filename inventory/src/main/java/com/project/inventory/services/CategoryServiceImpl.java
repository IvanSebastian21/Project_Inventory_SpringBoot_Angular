package com.project.inventory.services;

import com.project.inventory.dao.ICategoryDao;
import com.project.inventory.model.Category;
import com.project.inventory.response.CategoryResponseREST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryDao iCategoryDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseREST> search() {

        CategoryResponseREST categoryResponseREST = new CategoryResponseREST();

        try {
            List<Category> categoryList = (List<Category>) iCategoryDao.findAll();
            categoryResponseREST.getCategoryResponse().setCategoryList(categoryList);
            categoryResponseREST.setMetadata("Respuesta Ok", "00", "Respuesta Exitosa");
        } catch (Exception e) {
            categoryResponseREST.setMetadata("Error", "500", "Error en Respuesta");
            e.getStackTrace();
            e.getMessage();
            return new ResponseEntity<CategoryResponseREST>(categoryResponseREST, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CategoryResponseREST>(categoryResponseREST, HttpStatus.OK);
    }
}
