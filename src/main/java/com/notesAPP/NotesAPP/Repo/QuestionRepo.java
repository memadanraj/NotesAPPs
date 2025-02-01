package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepo extends JpaRepository<TestEntity, Long> {



}
