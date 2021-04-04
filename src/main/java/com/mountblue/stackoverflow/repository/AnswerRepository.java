package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    @Query(value = "select * from answer where question_id=:questionId", nativeQuery = true)
    List<Answer> findByQuestionId(@Param("questionId") int questionId);

    @Query(value = "select * from answer where question_id=:questionId order by created_at", nativeQuery = true)
    List<Answer> findSortedAnswerByTimeStamp(@Param("questionId") int questionId);

    @Query(value = "select * from answer where question_id=:questionId order by vote desc", nativeQuery = true)
    List<Answer> findSortedAnswerByVotes(@Param("questionId") int questionId);
}
