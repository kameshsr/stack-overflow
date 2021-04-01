package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Answer;

public interface AnswerService {
    void save(Answer answer);
    Answer findById( int id);
    void deleteById(int id);
}
