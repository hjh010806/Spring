package com.example.demo.Answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {

    Page<Answer> findAll(Pageable pageable);

    Page<Answer> findByQuestionId(Pageable pageable, int questionId);


//    Page<Answer> findAllByQuestion(Pageable pageable, Question question);

    @Query(value="select "
            + "distinct a.*, count(av.answer_id) as voter_count "
            + "from answer a "
            + "left outer join answer_voter av on av.answer_id=a.id "
            + "where a.question_id = :questionId "
            + "group by a.id, av.answer_id "
            + "order by voter_count desc, a.create_date desc "
            , countQuery = "select " +
            "            count(av.answer_id) " +
            "            from answer a " +
            "            left outer join answer_voter av on av.answer_id=a.id " +
            "            where a.question_id = :questionId " +
            "            group by a.id, av.answer_id "
            , nativeQuery = true)
    Page<Answer> findAllByQuestion(@Param("questionId") Integer questionId, Pageable pageable);




//todo JPQL사용하려고 도전해본것 나중에 해볼것

//    @Query("SELECT a, COUNT(a.voter) AS voter_count " +
//            "FROM Answer a " +
//            "LEFT JOIN a.voter av " +
//            "WHERE a.question.id = :questionId " +
//            "GROUP BY a.id " +
//            "ORDER BY voter_count DESC, a.createDate DESC")
//    Page<Answer> findAllByQuestion(@Param("questionId") Integer questionId, Pageable pageable);
}

