package com.example.demo.Comment;

import com.example.demo.Answer.Answer;
import com.example.demo.Answer.AnswerService;
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

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class  CommentController {
    private final AnswerService answerService;
    private final CommentService commentService;
    private final UserService userService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id, CommentForm commentForm,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        Page<Comment> paging = this.commentService.getList(id, page);
        model.addAttribute("answer", answer);
        model.addAttribute("paging", paging);
        return "answer_detail";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id,
                               @Valid CommentForm commentForm, BindingResult bindingResult,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               Principal principal) {
        Answer answer = this.answerService.getAnswer(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        model.addAttribute("answer", answer);
        Page<Comment> paging = this.commentService.getList(id, page);
        model.addAttribute("paging",paging);
        if (bindingResult.hasErrors()) {
            return "answer_detail";
        }
        Comment comment = this.commentService.create(answer, commentForm.getContent(), siteUser);
        return String.format("redirect:/answer/detail/%s#comment_%s", comment.getAnswer().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String commentVote(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.commentService.vote(comment, siteUser);
        return String.format("redirect:/answer/detail/%s#comment_%s", comment.getAnswer().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String commentModify(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Comment comment = this.commentService.getComment(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentForm.setContent(comment.getContent());
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String commentModify(@Valid CommentForm commentForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "comment_form";
        }
        Comment comment = commentService.getComment(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.modify(comment, commentForm.getContent());
        return String.format("redirect:/answer/detail/%s#comment_%s", comment.getAnswer().getId(), comment.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(Principal principal, @PathVariable("id") Integer id) {
        Comment comment = this.commentService.getComment(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/answer/detail/%s", comment.getAnswer().getId());
    }


}
