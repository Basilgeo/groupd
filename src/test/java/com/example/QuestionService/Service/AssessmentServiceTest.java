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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AssessmentServiceTest {

    @Mock
    private QuestionRepo questionRepo; // Mock the repository for questions

    @Mock
    private AssessmentRepo assessmentRepo; // Mock the repository for assessments

    @InjectMocks
    private AssessmentService assessmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAssessment() {
        AssessmentDto assessmentDto = AssessmentDto.builder()
                .setid(1L)
                .setname("Assessment 1")
                .createdby("Creator")
                .domain("Domain")
                .status(Status.IN_PROGRESS)
                .updatedby("Updater")
                .createdtimestamp(new Date())
                .updatedtimestamp(new Date())
                .questions(Arrays.asList())
                .build();

        Assessment assessment = EntityToDto.convertToEntity(assessmentDto);

        when(assessmentRepo.save(any(Assessment.class))).thenReturn(assessment);

        Assessment result = assessmentService.createAssessment(assessmentDto);

        assertNotNull(result);
        assertEquals(1L, result.getSetid());
        assertEquals("Assessment 1", result.getSetname());
    }

    @Test
    public void testGetAllAssessments() {
        Assessment assessment1 = new Assessment();
        assessment1.setSetid(1L);
        assessment1.setSetname("Assessment 1");

        Assessment assessment2 = new Assessment();
        assessment2.setSetid(2L);
        assessment2.setSetname("Assessment 2");

        List<Assessment> assessments = Arrays.asList(assessment1, assessment2);

        when(assessmentRepo.findAll()).thenReturn(assessments);

        List<Assessment> result = assessmentService.getAllAssessments();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testUpdateAssessmentByQid() {
        Long setid = 1L;
        Long qid = 1L;

        QuestionDto questionDto = QuestionDto.builder()
                .qid(qid)
                .qdetails("Updated Question")
                .setid(setid)
                .answers(Arrays.asList())
                .build();

        Question question = EntityToDto.convertToEntity(questionDto);
        Assessment assessment = new Assessment();
        assessment.setSetid(setid);
        assessment.setQuestions(Arrays.asList(question));

        when(assessmentRepo.findById(setid)).thenReturn(Optional.of(assessment));
        when(questionRepo.findById(qid)).thenReturn(Optional.of(new Question()));
        when(questionRepo.save(any(Question.class))).thenReturn(question);

        Question result = assessmentService.updateAssessmentbyqid(setid, qid, questionDto);

        assertNotNull(result);
        assertEquals(qid, result.getQid());
    }

    @Test
    public void testUpdateAssessmentByQidNotFound() {
        Long setid = 1L;
        Long qid = 1L;
        QuestionDto questionDto = QuestionDto.builder().build();

        when(assessmentRepo.findById(setid)).thenReturn(Optional.empty());

        Question result = assessmentService.updateAssessmentbyqid(setid, qid, questionDto);

        assertNull(result);
    }

    @Test
    public void testDeleteAssessmentByQidAndSetId() {
        Long setid = 1L;
        Long qid = 1L;

        when(questionRepo.findByQidAndSetid(qid, setid)).thenReturn(Optional.of(new Question()));
        doNothing().when(questionRepo).delete(any(Question.class));

        assertDoesNotThrow(() -> assessmentService.deleteAssessmentByQidAndSetId(setid, qid));

        verify(questionRepo, times(1)).delete(any(Question.class));
    }

    @Test
    public void testDeleteAssessmentByQidAndSetIdNotFound() {
        Long setid = 1L;
        Long qid = 1L;

        when(questionRepo.findByQidAndSetid(qid, setid)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> assessmentService.deleteAssessmentByQidAndSetId(setid, qid));
    }
}
