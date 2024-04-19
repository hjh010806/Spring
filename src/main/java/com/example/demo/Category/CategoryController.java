package com.example.demo.Category;

import com.example.demo.Question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/category")
@Controller
public class CategoryController {
    private final CategoryService categoryService;
    private final QuestionService questionService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String showCategoryForm(@ModelAttribute("categoryForm") CategoryForm categoryForm) {
        return "category_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("categoryForm") CategoryForm categoryForm,
                                 BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "category_form";
        }
        categoryService.create(categoryForm.getCategoryName());
        return "redirect:/question/list";
    }

//    @GetMapping("/{categoryId}")
//    public String getQuestionsByCategory(@PathVariable("categoryId") Integer categoryId, Model model, @RequestParam(defaultValue = "0") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
//        // 카테고리에 해당하는 질문들을 가져옵니다.
//        List<Question> questions = questionService.getQuestionsByCategory(categoryId);
//
//        // 카테고리 이름을 가져옵니다.
//        String categoryName = categoryService.getCategoryById(categoryId).getCategoryName();
//
//        // 페이징 처리 로직을 적용하여 페이지 목록을 가져옵니다.
//        Page<Question> paging = questionService.getQuestionsPaged(categoryId, pageNumber, pageSize);
//
//        // 뷰에 질문 목록과 카테고리 이름, 페이징 객체를 전달합니다.
//        model.addAttribute("questions", questions);
//        model.addAttribute("categoryName", categoryName);
//
//        // 페이징 객체를 전달합니다.
//        model.addAttribute("paging", paging);
//
//        return "question_list"; // 질문 목록을 보여줄 뷰의 이름
//    }

}
