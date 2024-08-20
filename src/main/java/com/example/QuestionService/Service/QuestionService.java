package com.example.QuestionService.Service;

import com.example.QuestionService.Model.Question;
import com.example.QuestionService.Repo.QuestionRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {


    private final QuestionRepo questionRepo;

    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public List<Question> getAllQuestions(Long setid) {
        return questionRepo.findBySetid(setid);
    }


    public Question getQuestionById(Long qid) {
        return questionRepo.findById(qid).orElse(null);
    }
}
