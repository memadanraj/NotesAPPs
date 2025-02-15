package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.NotesEntiry;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotesRepo extends JpaRepository<NotesEntiry,Long> {
    @Query("SELECT n.notesContent FROM NotesEntiry n WHERE n.chapId.id = :chapId")
    Optional<NotesEntiry> findByChapId(@Param("chapId") Long chapId);

    @Query("SELECT n FROM NotesEntiry n WHERE n.chapId.id = :chapId")
   Optional<NotesEntiry> removeByChapId(@Param("chapId") Long chapId);

}
