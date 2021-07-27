package com.shreeharibi.expensetracker.controller;

import com.shreeharibi.expensetracker.category.Category;
import com.shreeharibi.expensetracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("{details}")
    public Category getCategoryByIdorName(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name) {
        if (!name.isEmpty()){
            return categoryService.getCategoryByName(name);
        }
        if (id >= 0) {
            return categoryService.getCategoryById(id);
        }
        return null;
    }

    @PostMapping
    public void addNewCategory(@RequestBody Category category) {
        categoryService.addNewCategory(category);
    }

    @DeleteMapping
    public void deleteCategoryByName(@RequestBody Map<String, List<String>> categoryList) {
        List<String> categories = categoryList.get("category");
        for (String category : categories) {
            categoryService.deleteCategoryByName(category);
        }
    }

    @DeleteMapping(path = "{categoryId}")
    public void deleteCategoryById(
            @PathVariable("categoryId") Long categoryId
    ) {
        categoryService.deleteCategoryById(categoryId);
    }

    @PutMapping(path = "{oldCategoryName}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable("oldCategoryName") String oldCategoryName,
            @RequestBody Category category
    ) {
        return categoryService.updateCategory(oldCategoryName, category);
    }
}
