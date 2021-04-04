package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AnswerServiceImpl  implements AnswerService{

   private final AnswerRepository answerRepository;
    @Autowired
    AnswerServiceImpl(AnswerRepository answerRepository){
        this.answerRepository=answerRepository;
    }
    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    @Override
    public Answer findById(int id) {
        Optional<Answer> result=answerRepository.findById(id);

        Answer  answer=null;
        if(result.isPresent()){
            answer=result.get();
        }else{
            throw new RuntimeException("Did not find answer id"+id);
        }
        return answer;
    }

    @Override
    public void deleteById(int id) {
        answerRepository.deleteById(id);
    }

    @Override
    public List<Answer> findByQuestionId(int questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    @Override
    public List<Answer> findSortedAnswerByTimeStamp(int questionId) {
        return answerRepository.findSortedAnswerByTimeStamp(questionId);
    }

    @Override
    public List<Answer> findSortedAnswerByVotes(int questionId) {
        return answerRepository.findSortedAnswerByVotes(questionId);
    }
}
