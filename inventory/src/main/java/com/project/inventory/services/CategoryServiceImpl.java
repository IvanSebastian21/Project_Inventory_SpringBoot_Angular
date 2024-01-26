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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<CategoryResponseREST> searchById(Long id) {
        CategoryResponseREST categoryResponseREST = new CategoryResponseREST();
        List<Category> categoryList = new ArrayList<>();

        try {
            Optional<Category> categoryOptional = iCategoryDao.findById(id);
            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                categoryList.add(category);
                categoryResponseREST.setMetadata("Ok", "200", "Se agregaron datos");
            } else {
                categoryResponseREST.setMetadata("Error", "404", "Error: Categoria no encontrada");
                return new ResponseEntity<CategoryResponseREST>(categoryResponseREST, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            categoryResponseREST.setMetadata("Error", "500", "Error en Respuesta");
            e.getStackTrace();
            e.getMessage();
            return new ResponseEntity<CategoryResponseREST>(categoryResponseREST, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CategoryResponseREST>(categoryResponseREST, HttpStatus.OK);
    }
}
