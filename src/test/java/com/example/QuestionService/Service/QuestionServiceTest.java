package com.example.QuestionService.Service;

import com.example.QuestionService.Model.Question;
import com.example.QuestionService.Repo.QuestionRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class QuestionServiceTest {

    @Mock
    private QuestionRepo questionRepo;

    @InjectMocks
    private QuestionService questionService;

    public QuestionServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllQuestions() {
        Long setid = 1L;
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1L, "Sample Question", setid, null));
        when(questionRepo.findBySetid(setid)).thenReturn(questions);

        List<Question> result = questionService.getAllQuestions(setid);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Sample Question", result.get(0).getQdetails());
    }

    @Test
    public void testGetQuestionById() {
        Long qid = 1L;
        Question question = new Question(qid, "Sample Question", 1L, null);
        when(questionRepo.findById(qid)).thenReturn(Optional.of(question));

        Question result = questionService.getQuestionById(qid);

        assertNotNull(result);
        assertEquals("Sample Question", result.getQdetails());
    }

    @Test
    public void testGetQuestionByIdNotFound() {
        Long qid = 1L;
        when(questionRepo.findById(qid)).thenReturn(Optional.empty());

        Question result = questionService.getQuestionById(qid);

        assertNull(result);
    }
}
