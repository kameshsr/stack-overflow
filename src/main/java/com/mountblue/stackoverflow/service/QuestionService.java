package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Question;

import java.util.Optional;

public interface QuestionService {
    void saveQuestion(Question question);

    Question getQuestion(int questionId);

    void deleteQuestionById(int questionId);
}
