package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.AnswerComment;

import java.util.List;

public interface AnswerCommentService {

    List<AnswerComment> findAll();
    AnswerComment findById(int id);
    void deleteById(int id);
    void save(AnswerComment answerComment);
}
