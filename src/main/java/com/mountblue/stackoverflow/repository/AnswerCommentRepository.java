package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment,Integer> {

    List<AnswerComment> findByAnswerId(int id);

}
