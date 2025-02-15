package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.SemesterEntiry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemesterRepo extends JpaRepository<SemesterEntiry,Long> {
    //USED TO FIND EXISTING SEMESTER NAME ONLY
    Optional<SemesterEntiry> findBySemsterName(String semsterName);
}
