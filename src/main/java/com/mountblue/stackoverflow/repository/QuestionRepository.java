package com.mountblue.stackoverflow.repository;

import com.mountblue.stackoverflow.model.Answer;
import com.mountblue.stackoverflow.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "select * from question order by id asc", nativeQuery = true)
    List<Question> sortByNewestDate();

    @Query(value = "select * from question order by id desc ", nativeQuery = true)
    List<Question> sortByOldestDate();

    @Query(value = "select * from question order by vote desc ", nativeQuery = true)
    List<Question> sortByMaxVotes();
}
