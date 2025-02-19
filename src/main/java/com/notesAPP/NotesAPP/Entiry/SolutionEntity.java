package com.notesAPP.NotesAPP.Entiry;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.notesAPP.NotesAPP.Dto.Imageinfo;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "solutionEntity")
public class SolutionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long solId;

    private String solName;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonBackReference
    private SubjectEntity subjectEntity;

    public SubjectEntity getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getSolName() {
        return solName;
    }

    public void setSolName(String solName) {
        this.solName = solName;
    }

    public Long getSolId() {
        return solId;
    }

    public void setSolId(Long solId) {
        this.solId = solId;
    }
}
