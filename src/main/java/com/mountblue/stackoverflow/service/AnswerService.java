package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Answer;

import java.util.List;

public interface AnswerService {
    void save(Answer answer);
    Answer findById( int id);
    void deleteById(int id);

    List<Answer> findByQuestionId(int questionId);
}
