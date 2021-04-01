package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImplementation implements QuestionService{

    private QuestionRepository questionRepository;

    public QuestionServiceImplementation(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question getQuestion(int questionId) {
        Optional<Question> optional = questionRepository.findById(questionId);
        Question question = null;
        if (optional.isPresent()) {
            question = optional.get();
        } else {
            throw new RuntimeException("Question not found for id: " + questionId);
        }
        return question;
    }

    @Override
    public void deleteQuestionById(int questionId) {
        questionRepository.deleteById(questionId);
    }
}
