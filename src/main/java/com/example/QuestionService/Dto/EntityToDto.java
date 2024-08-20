package com.example.QuestionService.Dto;

import com.example.QuestionService.Model.Question;
import com.example.QuestionService.Model.Answers;
import com.example.QuestionService.Model.Assessment;

import java.util.List;
import java.util.stream.Collectors;

public class EntityToDto {

    public static AnswersDto convertToDto(Answers answers) {
        if (answers == null) {
            return null;
        }
        return AnswersDto.builder()
                .answerid(answers.getAnswerid())
                .answer(answers.getAnswer())
                .suggestion(answers.getSuggestion())
                //.qid(answers.getQid())
                .build();
    }

    public static Answers convertToEntity(AnswersDto answersDto) {
        if (answersDto == null) {
            return null;
        }
        return Answers.builder()
                .answerid(answersDto.getAnswerid())
                .answer(answersDto.getAnswer())
                .suggestion(answersDto.getSuggestion())
                //.qid(AnswersDto.getQid())
                .build();
    }

    public static QuestionDto convertToDto(Question question) {
        if (question == null) {
            return null;
        }
        List<AnswersDto> answersDtoList = question.getAnswers() != null ? question.getAnswers().stream()
                .map(EntityToDto::convertToDto)
                .collect(Collectors.toList()) : null;

        return QuestionDto.builder()
                .qid(question.getQid())
                .qdetails(question.getQdetails())
                //.setid(question.getSetid())
                .answers(answersDtoList)
                .build();
    }

    public static Question convertToEntity(QuestionDto questionDto) {
        if (questionDto == null) {
            return null;
        }
        List<Answers> answersList = questionDto.getAnswers() != null ? questionDto.getAnswers().stream()
                .map(EntityToDto::convertToEntity)
                .collect(Collectors.toList()) : null;

        return Question.builder()
                .qid(questionDto.getQid())
                .qdetails(questionDto.getQdetails())
                .setid(questionDto.getSetid())
                .answers(answersList)
                .build();
    }

    public static AssessmentDto convertToDto(Assessment assessment) {
        if (assessment == null) {
            return null;
        }

        return AssessmentDto.builder()
                .setid(assessment.getSetid())
                .setname(assessment.getSetname())
                .createdby(assessment.getCreatedby())
                .domain(assessment.getDomain())
                .status(assessment.getStatus())
                .updatedby(assessment.getUpdatedby())
                .createdtimestamp(assessment.getCreatedtimestamp())
                .updatedtimestamp(assessment.getUpdatedtimestamp())
                .questions(assessment.getQuestions() != null ? assessment.getQuestions().stream()
                        .map(EntityToDto::convertToDto)
                        .collect(Collectors.toList()) : null)
                .build();
    }

    public static Assessment convertToEntity(AssessmentDto assessmentDTO) {
        if (assessmentDTO == null) {
            return null;
        }

        List<Question> questionsList = assessmentDTO.getQuestions() != null ? assessmentDTO.getQuestions().stream()
                .map(EntityToDto::convertToEntity)
                .collect(Collectors.toList()) : null;

        return Assessment.builder()
                .setid(assessmentDTO.getSetid())
                .setname(assessmentDTO.getSetname())
                .createdby(assessmentDTO.getCreatedby())
                .domain(assessmentDTO.getDomain())
                .status(assessmentDTO.getStatus())
                .updatedby(assessmentDTO.getUpdatedby())
                .createdtimestamp(assessmentDTO.getCreatedtimestamp())
                .updatedtimestamp(assessmentDTO.getUpdatedtimestamp())
                .questions(questionsList)
                .build();
    }
}
