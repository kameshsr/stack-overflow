package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.AnswerComment;
import com.mountblue.stackoverflow.repository.AnswerCommentRepository;
import com.mountblue.stackoverflow.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerCommentServiceImpl implements  AnswerCommentService{

    private final AnswerCommentRepository answerCommentRepository;

    public AnswerCommentServiceImpl(AnswerCommentRepository answerCommentRepository){
        this.answerCommentRepository=answerCommentRepository;

    }

    @Override
    public List<AnswerComment> findAll() {
        return answerCommentRepository.findAll();
    }

    @Override
    public AnswerComment findById(int id) {
        Optional<AnswerComment> result=answerCommentRepository.findById(id);

        AnswerComment  answerComment=null;
        if(result.isPresent()){
            answerComment=result.get();
        }else{
            throw new RuntimeException("Did not find answer comment id"+id);
        }
        return answerComment;

    }

    @Override
    public void deleteById(int id) {
        answerCommentRepository.deleteById(id);
    }

    @Override
    public void save(AnswerComment answerComment) {
        answerCommentRepository.save(answerComment);
    }

    @Override
    public List<AnswerComment> findByAnswerId(int id) {
        return answerCommentRepository.findByAnswerId(id);
    }


}
