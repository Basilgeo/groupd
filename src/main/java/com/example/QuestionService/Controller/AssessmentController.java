package com.example.QuestionService.Controller;

import com.example.QuestionService.Dto.AssessmentDto;
import com.example.QuestionService.Dto.QuestionDto;
import com.example.QuestionService.Model.Assessment;
import com.example.QuestionService.Model.Question;
import com.example.QuestionService.Service.AssessmentService;
import com.example.QuestionService.Service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing Assessments and related Questions.
 */
@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final QuestionService questionService;

    // Constructor-based dependency injection
    public AssessmentController(AssessmentService assessmentService, QuestionService questionService) {
        this.assessmentService = assessmentService;
        this.questionService = questionService;
    }

    /**
     * Creates a new assessment.
     *
     * @param assessmentDto The details of the assessment to create, provided in the request body.
     * @return A ResponseEntity containing the created Assessment and HTTP status 201 Created.
     */
    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@RequestBody AssessmentDto assessmentDto) {
        Assessment createdAssessment = assessmentService.createAssessment(assessmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAssessment);
    }

    /**
     * Retrieves all assessments.
     *
     * @return A ResponseEntity containing a list of all Assessments and HTTP status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<Assessment>> getAssessments() {
        List<Assessment> assessments = assessmentService.getAllAssessments();
        return ResponseEntity.status(HttpStatus.OK).body(assessments);
    }

    /**
     * Retrieves all questions for a specific assessment.
     *
     * @param setid The ID of the assessment set for which to retrieve questions, provided as a path variable.
     * @return A ResponseEntity containing a list of Questions for the specified assessment set and HTTP status 200 OK.
     */
    @GetMapping("/{setid}")
    public ResponseEntity<Object> getQuestions(@PathVariable Long setid) {
            List<Question> questions = questionService.getAllQuestions(setid);
            if (questions == null || questions.isEmpty()) {
                // Return a custom message with HTTP status 200 OK if no questions are found
                return ResponseEntity.status(HttpStatus.OK).body(getString() + ": " + setid);
            }
            return ResponseEntity.status(HttpStatus.OK).body(questions);
    }

    private static String getString() {
        return "No questions found for assessment set ID";
    }

    /**
     * Updates a specific question within an assessment.
     *
     * @param setid The ID of the assessment set containing the question to update, provided as a path variable.
     * @param qid The ID of the question to update, provided as a path variable.
     * @param questionDto The updated question details, provided in the request body.
     * @return A ResponseEntity containing the updated Question and HTTP status 202 Accepted.
     */
    @PutMapping("/{setid}/{qid}")
    public ResponseEntity<Object> updateAssessmentbyqid(
            @PathVariable Long setid,
            @PathVariable Long qid,
            @RequestBody QuestionDto questionDto) {

        Question updatedQuestion = assessmentService.updateAssessmentbyqid(setid, qid, questionDto);
       if(updatedQuestion!=null) {
           return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedQuestion);
       }
       else {
          // throw new RuntimeException("Question not found with questionid: " + qid + " and setname: " + setid);
           return ResponseEntity.status(HttpStatus.OK).body("Question not found");
       }
    }

    /**
     * Deletes a specific question from an assessment.
     *
     * @param setid The ID of the assessment set containing the question to delete, provided as a path variable.
     * @param qid The ID of the question to delete, provided as a path variable.
     * @return HTTP status 204 No Content to indicate successful deletion without a response body.
     */
    @DeleteMapping("/{setid}/{qid}")
    public ResponseEntity<String> deleteAssessment(
            @PathVariable Long setid,
            @PathVariable Long qid) {
        try {
            assessmentService.deleteAssessmentByQidAndSetId(setid, qid);
            return ResponseEntity.status(HttpStatus.OK).body("Question deleted successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body("Question not found with question ID: " + qid + " and assessment set ID: " + setid);
        }
    }

    @GetMapping("/question/{qid}")
    public ResponseEntity<Object> getQuestionById(@PathVariable Long qid) {
        if (questionService.getQuestionById(qid) == null) {
            return ResponseEntity.status(HttpStatus.OK).body("Question not found with ID: " + qid);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(questionService.getQuestionById(qid));
        }
    }
}
