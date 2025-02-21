package com.notesAPP.NotesAPP.Entiry;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.notesAPP.NotesAPP.Dto.ImageInfoMultipleImage;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="testTB")


public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qid;

    private String name;

    private String year;

    private String type;

    @ElementCollection
    List<ImageInfoMultipleImage> imageurls;


    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonBackReference
    private SubjectEntity subjectEntity;

    public Long getQid() {
        return qid;
    }

    public void setQid(Long qid) {
        this.qid = qid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ImageInfoMultipleImage> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<ImageInfoMultipleImage> imageurls) {
        this.imageurls = imageurls;
    }

    public SubjectEntity getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }
}
