package com.example.demo.Answer;

import com.example.demo.DataNotFoundException;
import com.example.demo.Question.Question;
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
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        this.answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }

    public Page<Answer> getList(int page,int questionId){
        Pageable pageable = PageRequest.of(page, 5, Sort.by("createDate").descending());
        return this.answerRepository.findByQuestionId(pageable,questionId);
    }




//    public Page<Answer> getVoterList(int page,Question question){
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("voter"));
//        Pageable pageable = PageRequest.of(page,5);
//        return this.answerRepository.findAllByQuestion(question.getId(), pageable);
//    }

    // 두개 다 똑같음

    public Page<Answer> getVoterList(int page,Question question){
        Pageable pageable = PageRequest.of(page,5, Sort.by("voter_count").descending());
        return this.answerRepository.findAllByQuestion(question.getId(), pageable);
    }



}
