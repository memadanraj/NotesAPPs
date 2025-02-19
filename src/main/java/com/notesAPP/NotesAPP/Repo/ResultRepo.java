package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepo extends JpaRepository<ResultEntity,Long> {

}
