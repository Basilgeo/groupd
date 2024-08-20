package com.example.QuestionService.Repo;

import com.example.QuestionService.Model.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssessmentRepo extends JpaRepository<Assessment,Long> {
}
