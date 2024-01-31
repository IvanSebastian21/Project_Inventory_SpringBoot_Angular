package com.project.inventory.services;

import com.project.inventory.dao.ICategoryDao;
import com.project.inventory.model.Category;
import com.project.inventory.response.CategoryResponseREST;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseREST> update(Long id, Category updatedCategory) {
        CategoryResponseREST categoryResponseREST = new CategoryResponseREST();

        try {
            Optional<Category> searchResponse = iCategoryDao.findById(id);

            if (searchResponse.isPresent()) {
                Category existingCategory = searchResponse.get();

                if (updatedCategory.getName() != null && !updatedCategory.getName().trim().isEmpty() &&
                        updatedCategory.getDescription() != null && !updatedCategory.getDescription().trim().isEmpty()) {

                    existingCategory.setName(updatedCategory.getName());
                    existingCategory.setDescription(updatedCategory.getDescription());

                    Category updatedCategoryEntity = iCategoryDao.save(existingCategory);

                    categoryResponseREST.getCategoryResponse().setCategoryList(Collections.singletonList(updatedCategoryEntity));
                    categoryResponseREST.setMetadata("Success", "200", "Categoría actualizada exitosamente");
                    return new ResponseEntity<>(categoryResponseREST, HttpStatus.OK);

                } else {
                    // Al menos uno de los campos está vacío
                    categoryResponseREST.setMetadata("Error", "400", "Los campos 'name' y 'description' no pueden estar vacíos");
                    return new ResponseEntity<>(categoryResponseREST, HttpStatus.BAD_REQUEST);
                }

            } else {
                categoryResponseREST.setMetadata("Error", "404", "Categoría no encontrada");
                return new ResponseEntity<>(categoryResponseREST, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            categoryResponseREST.setMetadata("Error", "500", "Hubo un error al actualizar la categoría");
            e.printStackTrace();
            return new ResponseEntity<>(categoryResponseREST, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseREST> delete(Long id) {
        CategoryResponseREST categoryResponseREST = new CategoryResponseREST();

        try {
            // Validar que el ID no sea nulo ni negativo
            if (id != null && id > 0) {
                Optional<Category> searchResponse = iCategoryDao.findById(id);

                if (searchResponse.isPresent()) {
                    // La categoría existe, proceder con la eliminación
                    iCategoryDao.deleteById(id);

                    categoryResponseREST.setMetadata("Success", "200", "Categoría eliminada exitosamente");
                    return new ResponseEntity<>(categoryResponseREST, HttpStatus.OK);
                } else {
                    // La categoría no fue encontrada
                    categoryResponseREST.setMetadata("Error", "404", "Categoría no encontrada");
                    return new ResponseEntity<>(categoryResponseREST, HttpStatus.NOT_FOUND);
                }

            } else {
                // El ID es nulo o negativo
                categoryResponseREST.setMetadata("Error", "400", "El ID de la categoría no es válido");
                return new ResponseEntity<>(categoryResponseREST, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            categoryResponseREST.setMetadata("Error", "500", "Hubo un error al eliminar la categoría");
            e.printStackTrace();
            return new ResponseEntity<>(categoryResponseREST, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

