package com.example.demo.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findByAnswerId(Pageable pageable, int AnswerId);

    @Query(value="select "
            + "distinct c.*, count(cv.comment_id) as voter_count "
            + "from comment c "
            + "left outer join comment_voter cv on cv.comment_id=c.id "
            + "where c.answer_id = :answerId "
            + "group by c.id, cv.comment_id "
            + "order by voter_count desc, c.create_date desc "
            , countQuery = "select " +
            "            count(cv.comment_id) " +
            "            from comment c " +
            "            left outer join comment_voter cv on cv.comment_id=c.id " +
            "            where c.answer_id = :answerId " +
            "            group by c.id, cv.comment_id "
            , nativeQuery = true)
    Page<Comment> findAllByAnswer(@Param("answerId") Integer answerId, Pageable pageable);
}
