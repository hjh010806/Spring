package com.example.demo.Category;

import com.example.demo.Question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final QuestionRepository questionRepository;

    public void create(String categoryName) {
        Category q = new Category();
        q.setCategoryName(categoryName);
        this.categoryRepository.save(q);
    }

    public List<Category> getList() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryById(Integer categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.orElse(null); // 카테고리를 찾지 못하면 null 반환
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public String getCategoryNameById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        return (category != null) ? category.getCategoryName() : null;
    }


}
