package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {
}
