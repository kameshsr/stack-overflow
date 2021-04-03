package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment,Integer> {

    List<AnswerComment> findByAnswerId(int id);

    @Query(value = "select * from answer_comment where id=:answerCommentId", nativeQuery = true)
    AnswerComment findAnswerCommentById(@Param("answerCommentId") int answerCommentId);
}
