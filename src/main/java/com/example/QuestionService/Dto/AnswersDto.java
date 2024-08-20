package com.example.QuestionService.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswersDto {
    @NotBlank(message = "Answer ID is mandatory")
    private Long answerid;

    @NotBlank(message = "Answer is mandatory")
    private String answer;

    @NotBlank(message = "Suggestion is mandatory")
    private String suggestion;

//    @NotBlank(message = "   Question ID is mandatory")
//    private String qid;
}
