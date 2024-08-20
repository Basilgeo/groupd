package com.example.QuestionService.Dto;

import com.example.QuestionService.Model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssessmentDto {
    @NotBlank(message = "Assessment ID is mandatory")
    private Long setid;

    @NotBlank(message = "Set name is mandatory")
    private String setname;

    @NotBlank(message = "Created by is mandatory")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Status must only contain alphabetic characters")
    private String createdby;

    @NotBlank(message = "Domain is mandatory")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Domain must only contain alphabetic characters")
    private String domain;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank(message = "Category name is mandatory")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Status must only contain alphabetic characters")
    private String updatedby;

    private Date createdtimestamp;
    private Date updatedtimestamp;

    private List<QuestionDto> questions;
}