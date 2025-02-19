package com.notesAPP.NotesAPP.Entiry;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long resultID;

    @Column(nullable = false)
    private String resultName;

    @Column(nullable = false)
    private  String resultPublicId;
    @Column(nullable = false)
    private String resultUrl;

    public Long getResultID() {
        return resultID;
    }

    public void setResultID(Long resultID) {
        this.resultID = resultID;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getResultPublicId() {
        return resultPublicId;
    }

    public void setResultPublicId(String resultPublicId) {
        this.resultPublicId = resultPublicId;
    }

    public String getResultUrl() {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }
}
