package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment,Integer> {

}
