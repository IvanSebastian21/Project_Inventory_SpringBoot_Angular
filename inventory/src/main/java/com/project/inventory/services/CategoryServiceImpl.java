package com.project.inventory.services;

import com.project.inventory.dao.ICategoryDao;
import com.project.inventory.model.Category;
import com.project.inventory.response.CategoryResponseREST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Autowired
    private ICategoryDao iCategoryDao;

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseREST> search() {

        CategoryResponseREST categoryResponseREST = new CategoryResponseREST();

        try {
            List<Category> categoryList = (List<Category>) iCategoryDao.findAll();
            categoryResponseREST.getCategoryResponse().setCategoryList(categoryList);
            categoryResponseREST.setMetadata("Respuesta Ok", "200", "Respuesta Exitosa");
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
                categoryResponseREST.getCategoryResponse().setCategoryList(categoryList);
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

    @Override
    public ResponseEntity<CategoryResponseREST> save(Category category) {
        CategoryResponseREST categoryResponseREST = new CategoryResponseREST();
        List<Category> categoryList = new ArrayList<>();

        try {
            Category categorySaved = iCategoryDao.save(category);
            if (categorySaved != null) {
                categoryList.add(categorySaved);
                categoryResponseREST.getCategoryResponse().setCategoryList(categoryList);
                categoryResponseREST.setMetadata("Respuesta Ok", "200", "Se creo correctamente");
            } else {
                categoryResponseREST.setMetadata("Respuesta NotOk", "400", "No se pude crear correctamente");
            }
        } catch (Exception e) {
            categoryResponseREST.setMetadata("Error", "500", "Error al crear category");
            e.getStackTrace();
            e.getMessage();
            return new ResponseEntity<CategoryResponseREST>(categoryResponseREST, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<CategoryResponseREST>(categoryResponseREST, HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<CategoryResponseREST> update(Long id, Category updatedCategory) {
        CategoryResponseREST categoryResponseREST = new CategoryResponseREST();
        List<Category> categoryList = new ArrayList<>();

        try {
            Optional<Category> categoryOptional = iCategoryDao.findById(id);
            if (categoryOptional.isPresent()) {
                Category existingCategory = categoryOptional.get();
                // Actualizar los campos del objeto existente con los valores del objeto actualizado
                existingCategory.setName(updatedCategory.getName());
                existingCategory.setDescription(updatedCategory.getDescription());

                // Guardar la actualización en la base de datos
                Category categoryUpdated = iCategoryDao.save(existingCategory);

                categoryList.add(categoryUpdated);
                categoryResponseREST.getCategoryResponse().setCategoryList(categoryList);
                categoryResponseREST.setMetadata("Respuesta Ok", "200", "Se actualizó correctamente");
            } else {
                categoryResponseREST.setMetadata("Error", "404", "Error: Categoría no encontrada");
                return new ResponseEntity<>(categoryResponseREST, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            categoryResponseREST.setMetadata("Error", "500", "Error al actualizar categoría");
            e.printStackTrace(); // Imprimir la traza del error
            return new ResponseEntity<>(categoryResponseREST, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(categoryResponseREST, HttpStatus.OK);
    }
}

