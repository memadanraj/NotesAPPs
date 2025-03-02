package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Entiry.CommunityQuestionEntity;
import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Repo.CommunityQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityQuestionService {

    @Autowired
    private CommunityQuestionRepo communityQuestionRepo;

    public CommunityQuestionEntity createComQuestion(CommunityQuestionEntity communityQuestionEntity,
                                                     UserEntity userEntity){
        communityQuestionEntity.setUser(userEntity);
         return communityQuestionRepo.save(communityQuestionEntity);
    }

}
