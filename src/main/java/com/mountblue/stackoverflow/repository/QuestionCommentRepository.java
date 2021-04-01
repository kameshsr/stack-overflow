package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionCommentRepository extends JpaRepository <QuestionComment, Integer>{
}
