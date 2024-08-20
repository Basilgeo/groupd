package com.example.QuestionService.Service;

import com.example.QuestionService.Dto.AssessmentDto;
import com.example.QuestionService.Dto.EntityToDto;
import com.example.QuestionService.Dto.QuestionDto;
import com.example.QuestionService.Model.Assessment;
import com.example.QuestionService.Model.Question;
import com.example.QuestionService.Model.Status;
import com.example.QuestionService.Repo.AssessmentRepo;
import com.example.QuestionService.Repo.QuestionRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class AssessmentService {


    private final QuestionRepo questionRepo;


    private final AssessmentRepo assessmentRepo;



    public AssessmentService(QuestionRepo questionRepo, AssessmentRepo assessmentRepo) {
        this.questionRepo = questionRepo;
        this.assessmentRepo = assessmentRepo;

    }


    public Assessment createAssessment(AssessmentDto assessment) {
        try {

            Assessment assessment1 = EntityToDto.convertToEntity(assessment);

            assessment1.setStatus(Status.IN_PROGRESS);
            return assessmentRepo.save(assessment1);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create assessment", e);
        }
    }

    public List<Assessment> getAllAssessments() {
        try{
            return assessmentRepo.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to get all assessments", e);
        }

    }


    public Question updateAssessmentbyqid(Long setid, Long qid, QuestionDto question) {


        Optional<Assessment> setInfo= assessmentRepo.findById(setid);
        Optional<Question> updQuestion=questionRepo.findById(qid);
        Question question1 = EntityToDto.convertToEntity(question);
        if(updQuestion.isPresent()&& setInfo.isPresent()){


            updQuestion.get().setAnswers(question1.getAnswers());
            Question test = updQuestion.get();
            return questionRepo.save(test);

        }
        else{
            return null;
        }
    }

    public void deleteAssessmentByQidAndSetId(Long setid, Long qid) {

        if (setid == null) {
            throw new IllegalArgumentException("Set name cannot be null or empty");
        }
        if (qid == null) {
            throw new IllegalArgumentException("Question ID cannot be null or empty");
        }


        Question question = (Question) questionRepo.findByQidAndSetid(qid, setid)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with questionid: " + qid + " and setname: " + setid));
        questionRepo.delete(question);
    }


}


