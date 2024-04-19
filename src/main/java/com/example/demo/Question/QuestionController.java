package com.example.demo.Question;

import com.example.demo.Answer.Answer;
import com.example.demo.Answer.AnswerForm;
import com.example.demo.Answer.AnswerService;
import com.example.demo.Category.Category;
import com.example.demo.Category.CategoryService;
import com.example.demo.Image.ImageRepository;
import com.example.demo.User.SiteUser;
import com.example.demo.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService questionService;
    private final UserService userService;
    private final AnswerService answerService;
    private final CategoryService categoryService;
    private final ImageRepository imageRepository;
    RedirectAttributes redirectAttributes;

    // redirectAttributes.addFlashAttribute("profile",profile);
    @ModelAttribute("profileImages")
    public String getProfileImage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) { // 유저가 있음
            SiteUser siteUser = userService.getUser(userDetails.getUsername());
            // 해당 사용자의 프로필 이미지를 가져옵니다.
//            List<Image> profileImages = imageRepository.findBySiteUser(siteUser);

//            if(model.containsAttribute("temp")) // 임시 데이터
//                return "/aaaa/test.png";
//            else if (profileImages.isEmpty())  // 유저는 존재하지만 프로필은 비어 있음
//                return "/default_profile_image.png";
//            else
// 유저 존재 프로필 안 빔
//                return profileImages.getFirst().getPostImageUrl();

            if (model.containsAttribute("temp")) // 임시 데이터
                return (String) model.getAttribute("temp");
            else
                return siteUser.getUrl() != null ? siteUser.getUrl() : "/default_profile_image.png";


        } else // 유저가 없는 경우
            return "/aaaa/test.png";
    }

    @GetMapping("/list")
    public String profileList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                              @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Question> paging = this.questionService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);


        return "question_list";
    }


    @GetMapping("/list/{id}")
    public String categoryList(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "kw", defaultValue = "") String kw,
                               @PathVariable("id") int id) {
        Specification<Question> categorySpec = questionService.searchCategory(id);
        Page<Question> paging = questionService.findBySpecification(categorySpec, PageRequest.of(page, 10));
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "question_list";
    }

    @ModelAttribute("categories")
    public List<Category> getCategories() {
        return categoryService.getList();
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm, @RequestParam(value = "page", defaultValue = "0") int page) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        Page<Answer> paging = this.answerService.getList(page, id);
        model.addAttribute("paging", paging);
        return "question_detail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Integer categoryId = questionForm.getCategoryId();

        // 카테고리 ID를 사용하여 해당 카테고리 정보를 가져옵니다.
        Category category = this.categoryService.getCategoryById(categoryId);
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser, category);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
        }
        this.questionService.delete(question);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

    @GetMapping(value = "/voter/{id}")
    public String vote(Model model, @PathVariable("id") Integer id, AnswerForm answerForm,
                       @RequestParam(value = "page", defaultValue = "0") int page) {
        Question question = this.questionService.getQuestion(id);
        Page<Answer> paging = this.answerService.getVoterList(page, question);
        model.addAttribute("question", question);
        model.addAttribute("paging", paging);
        return "question_detail";
    }


}
