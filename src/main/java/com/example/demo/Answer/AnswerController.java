package com.example.demo.Answer;

import com.example.demo.Comment.Comment;
import com.example.demo.Comment.CommentForm;
import com.example.demo.Comment.CommentService;
import com.example.demo.Question.Question;
import com.example.demo.Question.QuestionService;
import com.example.demo.User.SiteUser;
import com.example.demo.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    private final CommentService commentService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, AnswerForm answerForm,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               Principal principal) {
        Question question = this.questionService.getQuestion(id);
        Page<Answer> paging = this.answerService.getList(page,id);
        model.addAttribute("question", question);
        model.addAttribute("paging", paging);
        return "question_detail";

    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                               @Valid AnswerForm answerForm, BindingResult bindingResult,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        model.addAttribute("question",question);
        Page<Answer> paging = this.answerService.getList(page,id);
        model.addAttribute("paging",paging);
        if (bindingResult.hasErrors()) {
            return "question_detail";
        }
        Answer answer = this.answerService.create(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer);
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        Answer answer = this.answerService.getAnswer(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.vote(answer, siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    @GetMapping(value = "/detail/{id}")
    public String answerDetail(Model model, @PathVariable("id") Integer id,
                               CommentForm commentForm, @RequestParam(value = "page", defaultValue = "0") int page) {
        Answer answer = this.answerService.getAnswer(id);
        model.addAttribute("answer", answer);
        Page<Comment> paging = this.commentService.getList(page,id);
        model.addAttribute("paging", paging);
        return "answer_detail";
    }


    @GetMapping(value = "/voter/{id}")
    public String Vote(Model model, @PathVariable("id") Integer id, CommentForm commentForm,
                       @RequestParam(value = "page", defaultValue = "0") int page) {
        Answer answer = this.answerService.getAnswer(id);
        Page<Comment> paging = this.commentService.getVoterList(page, answer);
        model.addAttribute("answer", answer);
        model.addAttribute("paging",paging);
        return "answer_detail";
    }




}
