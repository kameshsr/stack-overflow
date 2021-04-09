package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Question;
import com.mountblue.stackoverflow.repository.QuestionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class QuestionServiceImplementation implements QuestionService{

    private final QuestionRepository questionRepository;

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
        //System.out.println(question.toString());
        return question;
    }

    @Override
    public void deleteQuestionById(int questionId) {
        questionRepository.deleteById(questionId);
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getFilteredQuestions(String searchQuestion) {
        List<Question> allQuestions = questionRepository.findAll();
        List<Question> filteredQuestions = new ArrayList<>();
        for (int i=0; i<allQuestions.size(); i++) {
            if(allQuestions.get(i).getTitle().toLowerCase(Locale.ROOT).contains(searchQuestion.toLowerCase(Locale.ROOT)))
                filteredQuestions.add(allQuestions.get(i));
        }
        return  filteredQuestions;
    }

    @Override
    public List<Question> sortQuestionsByFilter(String sortBy) {
        List<Question> questions = null;
        if (sortBy.equals("newest"))
            questions = questionRepository.sortByNewestDate();
        else if (sortBy.equals("oldest"))
            questions = questionRepository.sortByOldestDate();
        else questions = questionRepository.sortByMaxVotes();

        return questions;
    }
}
