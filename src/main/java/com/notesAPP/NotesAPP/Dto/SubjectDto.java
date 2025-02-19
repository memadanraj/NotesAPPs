package com.notesAPP.NotesAPP.Dto;

public class SubjectDto {


        private Long subId;
        private String subjectName;

        public SubjectDto(Long subId, String subjectName) {
            this.subId = subId;
            this.subjectName = subjectName;
        }

        public Long getSubId() { return subId; }
        public String getSubjectName() { return subjectName; }
    }

