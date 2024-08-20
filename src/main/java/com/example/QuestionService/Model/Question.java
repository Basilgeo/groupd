package com.example.QuestionService.Model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qid;
    private String qdetails;
    private Long setid;

    @OneToMany
    @JoinColumn(name="questionid")
    @Cascade(value= CascadeType.ALL)
    private List<Answers> answers;

}
