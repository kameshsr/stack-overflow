package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.AnswerComment;
import com.mountblue.stackoverflow.model.QuestionComment;

import java.util.List;

public interface QuestionCommentService {

    List<QuestionComment> findAll();
    void save(QuestionComment questionComment);
    void deleteById(int id);

}
