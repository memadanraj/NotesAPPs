package com.notesAPP.NotesAPP.Entiry;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "chapter_entity", uniqueConstraints = @UniqueConstraint(columnNames = {"chapterTitle", "subject_id"}))

public class ChapterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long chapId;

    @Column(name = "chapterTitle" , nullable = false)
    private String chapterTitle;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonBackReference
    private SubjectEntity subjectEntity;

    @OneToMany(mappedBy = "chapId")
    @JsonManagedReference
    private List<NotesEntiry> notesEntiries;


    public List<NotesEntiry> getNotesEntiries() {
        return notesEntiries;
    }

    public void setNotesEntiries(List<NotesEntiry> notesEntiries) {
        this.notesEntiries = notesEntiries;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public Long getChapId() {
        return chapId;
    }

    public void setChapId(Long chapId) {
        this.chapId = chapId;
    }

    public SubjectEntity getSubjectEntity() {
        return subjectEntity;
    }

    public void setSubjectEntity(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }
}
