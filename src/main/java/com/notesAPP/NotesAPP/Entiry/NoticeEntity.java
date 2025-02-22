package com.notesAPP.NotesAPP.Entiry;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "NoticeEntity")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeId;

    @Column(name = "noticeName", nullable = false)
    private String noticeName;

    @Column(name = "noticeMessage", nullable = false)
    private String noticeMessage;

    @Column(nullable = false)
    private LocalDateTime createdDate;

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeName() {
        return noticeName;
    }

    public void setNoticeName(String noticeName) {
        this.noticeName = noticeName;
    }

    public String getNoticeMessage() {
        return noticeMessage;
    }

    public void setNoticeMessage(String noticeMessage) {
        this.noticeMessage = noticeMessage;
    }
}
