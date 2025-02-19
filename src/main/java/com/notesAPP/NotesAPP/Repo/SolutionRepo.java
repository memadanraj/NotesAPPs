package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.SolutionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolutionRepo extends JpaRepository<SolutionEntity,Long> {

    @Query("SELECT s FROM SolutionEntity s WHERE s.subjectEntity.subId = :subjectId")
    List<SolutionEntity> findBySubjectId(@Param("subjectId") Long subjectId);



}
