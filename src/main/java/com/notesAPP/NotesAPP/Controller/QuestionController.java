package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Dto.Imageinfo;
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
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/api/qn")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

      @Autowired
    private CloudinaryService cloudinaryService;

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

    @PostMapping("/addqn")
    public ResponseEntity<?> imgaeupload(
            @RequestPart("testEntity") TestEntity testEntity,
            @RequestPart("file") MultipartFile file) throws IOException {
        try {
            Imageinfo upload = cloudinaryService.upload(file);
            testEntity.setImageID(upload.publicId());
            testEntity.setImageurl(upload.securedurl());
            questionService.addqn(testEntity);

            return new ResponseEntity<>( testEntity,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateQn/{qid}")
    public  ResponseEntity<?> updateQns(@PathVariable long qid, @RequestPart("testEntity") TestEntity newEntry,
                                        @RequestPart(value = "file", required = false) MultipartFile file){
        try {
             TestEntity updatedQn=questionService.updateQn(qid,newEntry,file);

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
