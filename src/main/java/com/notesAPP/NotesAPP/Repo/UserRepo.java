package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {
}
