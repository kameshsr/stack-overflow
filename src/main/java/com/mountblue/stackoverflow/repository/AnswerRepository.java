package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    @Query(value = "select * from answer where question_id=:questionId", nativeQuery = true)
    List<Answer> findByQuestionId(int questionId);
}
