package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.CommunityQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityQuestionRepo extends JpaRepository<CommunityQuestionEntity,Long> {

}
