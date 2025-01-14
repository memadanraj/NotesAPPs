package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.QuestionsEntity;
import com.notesAPP.NotesAPP.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/qn")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping("/addqn")
    public ResponseEntity<?> questionPost(@RequestPart QuestionsEntity questionsEntity, MultipartFile file)  {
        try {
            questionService.addQuestion(questionsEntity,file);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
