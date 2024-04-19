package com.example.demo.Comment;

import com.example.demo.Answer.Answer;
import com.example.demo.DataNotFoundException;
import com.example.demo.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment create(Answer answer, String content, SiteUser siteUser) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreateDate(LocalDateTime.now());
        comment.setAnswer(answer);
        comment.setAuthor(siteUser);
        this.commentRepository.save(comment);
        return comment;
    }

    public Page<Comment> getList(int page, int answerId){
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createDate").descending());
        return this.commentRepository.findByAnswerId(pageable, answerId);
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public Page<Comment> getVoterList(int page, Answer answer){
        Pageable pageable = PageRequest.of(page,5, Sort.by("voter_count").descending());
        return this.commentRepository.findAllByAnswer(answer.getId(), pageable);
    }

    public void vote(Comment comment, SiteUser siteUser) {
        comment.getVoter().add(siteUser);
        this.commentRepository.save(comment);
    }

    public void modify(Comment comment, String content) {
        comment.setContent(content);
        comment.setModifyDate(LocalDateTime.now());
        this.commentRepository.save(comment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }



}
