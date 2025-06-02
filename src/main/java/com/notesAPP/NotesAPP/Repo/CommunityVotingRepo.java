package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.CommunityVotingSystemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CommunityVotingRepo extends JpaRepository<CommunityVotingSystemEntity,Long> {
//    Optional<CommunityVotingSystemEntity> findByUserIdAndEntityIdAndEntityType(Long userId, Long entityId, CommunityVotingSystemEntity.EntityType entityType);
}
