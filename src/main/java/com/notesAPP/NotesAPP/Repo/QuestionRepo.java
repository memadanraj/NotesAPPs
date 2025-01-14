package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.QuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepo extends JpaRepository<QuestionsEntity, Long> {
}
