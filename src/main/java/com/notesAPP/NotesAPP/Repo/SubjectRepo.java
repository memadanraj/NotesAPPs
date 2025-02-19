package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Dto.SubjectDto;
import com.notesAPP.NotesAPP.Entiry.SemesterEntiry;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity,Long> {
    @Query("SELECT s.subjectName FROM SubjectEntity s")
    List<String> findAllSubjectNames();


    Optional<SubjectEntity> findBySubjectNameAndSemesterEntiry(String subjectName, SemesterEntiry semesterEntiry);

    Optional<SubjectEntity> findBySubjectName(String subName);
}
