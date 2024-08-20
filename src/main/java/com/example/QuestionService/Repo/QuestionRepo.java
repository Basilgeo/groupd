package com.example.QuestionService.Repo;

import com.example.QuestionService.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepo extends JpaRepository<Question,Long> {

    Optional<Object> findByQidAndSetid(Long questionid, Long setid);

    List<Question> findBySetid(Long setid);
    //void deleteByQidAndSetname(String qid, String setname);
}
