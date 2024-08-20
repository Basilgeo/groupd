package com.example.QuestionService.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Assessment {
    @Id
    private Long setid;
    private String setname;
    private String createdby;
    private String domain;
    @Enumerated(EnumType.STRING)
    private Status status= Status.IN_PROGRESS;
    private String updatedby;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdtimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedtimestamp;

    @PrePersist
    protected void onCreate() {
        createdtimestamp = new Date();
        updatedtimestamp = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedtimestamp = new Date();
    }

    @OneToMany
    @JoinColumn(name="setid")
    @Cascade(value= CascadeType.ALL)
    @JsonIgnore
    private List<Question> questions;


}
