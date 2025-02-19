package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.SolutionEntity;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import com.notesAPP.NotesAPP.Services.SolutionService;
import jakarta.persistence.GeneratedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@CrossOrigin(origins = "*")

@RestController
@RequestMapping("api/solution")
public class SolutionController {

    @Autowired
    private SolutionService solutionService;

    @PostMapping("add")
    public ResponseEntity<?> uploadSolutions(@RequestParam String solutionEntity, @RequestParam List<MultipartFile> file , @RequestParam String subName){

        try{

            SolutionEntity solutionEntity1= solutionService.UploadNotes(solutionEntity,file,subName);
            return new ResponseEntity<>(solutionEntity1,HttpStatus.CREATED);


        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getall")
    public ResponseEntity<?> getAll(){
        try{
            List<SolutionEntity> solutionEntity= solutionService.getAllSolutions();
            return new ResponseEntity<>(solutionEntity,HttpStatus.OK);


        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getOnSub/{subId}")
    public ResponseEntity<?> getOnSubSolution(@PathVariable Long subId){

        try {
            List<SolutionEntity> solutionEntities= solutionService.getSolutionOnSub(subId);

            return new ResponseEntity<>(solutionEntities,HttpStatus.OK);
        }
        catch (Exception e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
