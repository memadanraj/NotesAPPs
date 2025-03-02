package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.CommunityQuestionEntity;
import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Repo.UserRepo;
import com.notesAPP.NotesAPP.Services.CommunityQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ComQn")
public class CommunityQuestionController {

    @Autowired
    private CommunityQuestionService communityQuestionService;
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/user/createQn")
    public ResponseEntity<?> createComQuestion( @RequestParam String title,
                                                @RequestParam String description,
                                                @RequestParam(required = false) String imageUrl,
                                                @RequestParam(required = false) String tags){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


            if (authentication == null || !authentication.isAuthenticated()) {
                return new ResponseEntity<>("User is not authenticated", HttpStatus.UNAUTHORIZED);
            }
            String userName= authentication.getName();
            UserEntity userEntity= userRepo.findByusername(userName);

            // Extract UserEntity from Authentication

            CommunityQuestionEntity communityQuestionEntity = new CommunityQuestionEntity();
            communityQuestionEntity.setTitle(title);
            communityQuestionEntity.setDescription(description);
            communityQuestionEntity.setImageUrl(imageUrl);
            communityQuestionEntity.setTags(tags);
            communityQuestionEntity.setUser(userEntity);

            CommunityQuestionEntity comQuestion = communityQuestionService.createComQuestion(communityQuestionEntity, userEntity);
            return new ResponseEntity<>(comQuestion,HttpStatus.CREATED);


        }catch (Exception e){
            return new ResponseEntity<>("Error: " + e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    //GET ALL QUESTIONS

}
