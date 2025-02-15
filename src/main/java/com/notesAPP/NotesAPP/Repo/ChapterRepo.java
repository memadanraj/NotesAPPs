package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.ChapterEntity;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChapterRepo extends JpaRepository<ChapterEntity,Long> {
    Optional<ChapterEntity> findByChapterTitleAndSubjectEntity(String chapterTitle, SubjectEntity subjectEntity);
}
