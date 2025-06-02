package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.CommunityAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommunityAnswerRepo extends JpaRepository<CommunityAnswerEntity,Long> {
    List<CommunityAnswerEntity> findByQuestionId(Long questionId);
}
