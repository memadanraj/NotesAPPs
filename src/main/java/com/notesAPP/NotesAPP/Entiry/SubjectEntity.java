package com.notesAPP.NotesAPP.Entiry;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "subject_entity", uniqueConstraints = @UniqueConstraint(columnNames = {"subjectName", "semester_id"}))
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subId;

    @Column(name = "subjectName" , nullable = false)
    private String subjectName;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    @JsonBackReference
    private SemesterEntiry semesterEntiry;

    @OneToMany(mappedBy = "subjectEntity")
    @JsonManagedReference
    private List<ChapterEntity> chapterEntityList;

    @OneToMany(mappedBy = "subjectEntity")
    @JsonManagedReference
    private List<SolutionEntity> solutionEntities;

    public List<SolutionEntity> getSolutionEntities() {
        return solutionEntities;
    }

    public void setSolutionEntities(List<SolutionEntity> solutionEntities) {
        this.solutionEntities = solutionEntities;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public SemesterEntiry getSemesterEntiry() {
        return semesterEntiry;
    }

    public void setSemesterEntiry(SemesterEntiry semesterEntiry) {
        this.semesterEntiry = semesterEntiry;
    }

    public List<ChapterEntity> getChapterEntityList() {
        return chapterEntityList;
    }

    public void setChapterEntityList(List<ChapterEntity> chapterEntityList) {
        this.chapterEntityList = chapterEntityList;
    }
}
