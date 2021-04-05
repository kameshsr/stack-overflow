package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    void saveQuestion(Question question);

    Question getQuestion(int questionId);

    void deleteQuestionById(int questionId);
    void save(Question question);

    List<Question> getAllQuestions();

    List<Question> getFilteredQuestions(String searchQuestion);
}
