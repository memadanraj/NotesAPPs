package com.notesAPP.NotesAPP.Entiry;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "votes")
@Getter
@Setter
public class CommunityVotingSystemEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        private UserEntity user;

        @Enumerated(EnumType.STRING)
        private VoteType voteType;

        private Long entityId;  // Can be a question or an answer

        @Enumerated(EnumType.STRING)
        private EntityType entityType;

        public enum VoteType {
            UPVOTE, DOWNVOTE
        }

        public enum EntityType {
            QUESTION, ANSWER
        }
    }

