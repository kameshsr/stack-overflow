package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionCommentRepository extends JpaRepository <QuestionComment, Integer>{
List<QuestionComment> findByQuestionId(int questionId);

    @Query(value = "select * from question_comment where id=:questionCommentId", nativeQuery = true)
    QuestionComment findQuestionCommentById(@Param("questionCommentId") int questionCommentId);
}
