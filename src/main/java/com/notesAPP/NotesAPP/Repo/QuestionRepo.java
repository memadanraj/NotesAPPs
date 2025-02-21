package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestionRepo extends JpaRepository<TestEntity, Long> {

   @Query("SELECT s FROM TestEntity s WHERE  s.subjectEntity.id = :subjectId")
    List<TestEntity> findBySubjectId(@Param("subjectId") Long subjectId);
}
