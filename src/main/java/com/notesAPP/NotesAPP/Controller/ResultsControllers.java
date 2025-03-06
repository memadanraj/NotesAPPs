package com.notesAPP.NotesAPP.Controller;

import com.cloudinary.Cloudinary;
import com.notesAPP.NotesAPP.Dto.PDFinfo;
import com.notesAPP.NotesAPP.Entiry.ResultEntity;
import com.notesAPP.NotesAPP.Services.CloudinaryService;
import com.notesAPP.NotesAPP.Services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
@RestController
@RequestMapping("api/results")
@CrossOrigin(origins = "http://localhost:5173")
public class ResultsControllers {
    @Autowired
   private ResultService resultService;

    //ADD RESULTS
    @PostMapping("admin/addResults")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testUploadPdf(@RequestParam String resultName, @RequestParam("pdfFIle") MultipartFile pdfFIle){
        try{
             ResultEntity result=resultService.uploadResults(resultName,pdfFIle);
             return new ResponseEntity<>(result,HttpStatus.CREATED);


        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//  GET ALL RESULTS
    @GetMapping("user/getAllResults")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllResults(){
        try{
            List<ResultEntity> allResults= resultService.findAllResults();
            return new ResponseEntity<>(allResults,HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE RESULTS FROM BOTH SIDE
    @DeleteMapping("admin/removeResults/{resultId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeResultsById(@PathVariable Long resultId){
        try{

            resultService.deleteResult(resultId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
