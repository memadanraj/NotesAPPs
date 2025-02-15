package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepo extends JpaRepository<NoticeEntity, Long> {
}
