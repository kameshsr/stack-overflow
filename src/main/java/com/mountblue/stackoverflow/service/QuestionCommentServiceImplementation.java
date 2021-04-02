package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.QuestionComment;
import com.mountblue.stackoverflow.repository.QuestionCommentRepository;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.List;
@Service
public class QuestionCommentServiceImplementation implements QuestionCommentService{

    private QuestionCommentRepository questionCommentRepository;
    public QuestionCommentServiceImplementation(QuestionCommentRepository questionCommentRepository){
        this.questionCommentRepository=questionCommentRepository;
    }
    @Override
    public List<QuestionComment> findAll() {
        return questionCommentRepository.findAll();
    }

    @Override
    public void save(QuestionComment questionComment) {
        questionCommentRepository.save(questionComment);
    }

    @Override
    public void deleteById(int id) {
       questionCommentRepository.deleteById(id);
    }

    @Override
    public List<QuestionComment> findByQuestionId(int questionId) {
        return null;
    }
}
