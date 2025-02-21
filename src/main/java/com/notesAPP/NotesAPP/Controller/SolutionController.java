package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.SolutionEntity;
import com.notesAPP.NotesAPP.Services.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.plaf.PanelUI;
import java.util.List;
import java.util.Optional;

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

    //UPDATE CONTROLLER BASED ON SUBJECT AND NOTE ID
    @PostMapping("/update/{solId}")
    public ResponseEntity<?> updateSolutionOnId(@PathVariable Long solId,
                                                @RequestParam String solName,
                                                @RequestParam(required = false)List<MultipartFile> files,
                                                @RequestParam(required = false)List<String> deleteIds
                                                ){
        try{
            SolutionEntity solutionEntities = solutionService.updateSolution(solId,solName,files,deleteIds);
            return new ResponseEntity<>(solutionEntities,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    //GET NOTES BASED ON SOLUTION ID
    @PutMapping("/updateGet/{solid}")
    public ResponseEntity<?> getSolutionOnSolId(@PathVariable Long solid){
        try{
            Optional<SolutionEntity> solutionEntity= solutionService.getBySolId(solid);
            return new ResponseEntity<>(solutionEntity,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE NOTES BASED ON SOLUTION ID
    @DeleteMapping("removeSolution/{solId}")
    public  ResponseEntity<?> removeSolutions(@PathVariable Long solId){
        try{
            solutionService.removeById(solId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
