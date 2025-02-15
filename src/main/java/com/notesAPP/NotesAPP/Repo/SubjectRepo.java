package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.SemesterEntiry;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepo extends JpaRepository<SubjectEntity,Long> {

    Optional<SubjectEntity> findBySubjectNameAndSemesterEntiry(String subjectName, SemesterEntiry semesterEntiry);
}
