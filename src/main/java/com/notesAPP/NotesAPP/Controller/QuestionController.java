package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.TestEntity;
import com.notesAPP.NotesAPP.Services.CloudinaryService;
import com.notesAPP.NotesAPP.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/qn")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

      @Autowired
    private CloudinaryService cloudinaryService;

// GET ALL QUESTIONS
    @GetMapping("/getAllQn")
    public ResponseEntity<?> getAllQn(){
        try {
            List<TestEntity> allQns =questionService.getAllQns();
            return new ResponseEntity<>(allQns, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    GET ONLY IMAGE BASED ON   SUBJECT
    @GetMapping("/getQnImage/{subId}")
    public ResponseEntity<?> getImageBySemAndSub(
            @PathVariable Long subId

    ){
        try{

            List<String> qnsImageBySub = questionService.getImageUrlBySubAndSem(subId);


                return new ResponseEntity<>(qnsImageBySub,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("getQnOnSub/{subId}")
    public ResponseEntity<?> getOnSubSolution(@PathVariable Long subId){

        try {
            List<TestEntity> solutionEntities= questionService.getQnBySub(subId);

            return new ResponseEntity<>(solutionEntities,HttpStatus.OK);
        }
        catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //POST QUESTIONS
    @PostMapping("/addQn")
    public ResponseEntity<?> imgaeupload(
           @RequestParam String qName,
           @RequestParam String qYear,
           @RequestParam String qType,
           @RequestParam String subName,
           @RequestParam List<MultipartFile> files) throws IOException {
        try {
           TestEntity testEntity = questionService.addQuestions(qName,qYear,qType,subName,files);

            return new ResponseEntity<>( testEntity,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateQn/{qid}")
    public  ResponseEntity<?> updateQns(@PathVariable long qid,
                                        @RequestPart("testEntity") TestEntity newEntry,
                                        @RequestParam String qName,
                                        @RequestParam String qYear,
                                        @RequestParam String qType,
                                        @RequestParam (required = false)List<String> deleteIds,
                                        @RequestPart(value = "file", required = false) List<MultipartFile> file){
        try {
             TestEntity updatedQn=questionService.updateQn(qid,qName,qYear,qType,deleteIds,file);

             //REMEMBER ME TO REMOVE ENTITY RETURN IN FINAL SECURITY
             return new ResponseEntity<>(updatedQn,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("remove/{qid}")
    public ResponseEntity<?> deleteQnById(@PathVariable Long qid){
        try {
            questionService.deleteQn(qid);
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
