package com.notesAPP.NotesAPP.Entiry;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.w3c.dom.Text;

import java.util.List;

@Entity
@Table(name = "NotesEntiry")
public class NotesEntiry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notesId;


    @Lob
    @Column(columnDefinition = "TEXT")
    private String notesContent;

    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "chap_id")
    @JsonBackReference
    private ChapterEntity chapId;


    public Long getNotesId() {
        return notesId;
    }

    public void setNotesId(Long notesId) {
        this.notesId = notesId;
    }

    public String getNotesContent() {
        return notesContent;
    }

    public void setNotesContent(String notesContent) {
        this.notesContent = notesContent;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ChapterEntity getChapId() {
        return chapId;
    }

    public void setChapId(ChapterEntity chapId) {
        this.chapId = chapId;
    }
}
