package com.notesAPP.NotesAPP.Entiry;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SemesterEntiry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long semId;

    @Column(name = "semsterName", unique = true, nullable = false)
    private String semsterName;

    @OneToMany(mappedBy = "semesterEntiry")
    @JsonManagedReference
    private List<SubjectEntity> subjectEntities;

    public Long getSemId() {
        return semId;
    }

    public void setSemId(Long semId) {
        this.semId = semId;
    }

    public String getSemsterName() {
        return semsterName;
    }

    public void setSemsterName(String semsterName) {
        this.semsterName = semsterName;
    }

    public List<SubjectEntity> getSubjectEntities() {
        return subjectEntities;
    }

    public void setSubjectEntities(List<SubjectEntity> subjectEntities) {
        this.subjectEntities = subjectEntities;
    }
}
