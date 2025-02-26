package com.notesAPP.NotesAPP.Controller;


import com.cloudinary.Cloudinary;
import com.notesAPP.NotesAPP.Dto.Imageinfo;
import com.notesAPP.NotesAPP.Entiry.NotesEntiry;
import com.notesAPP.NotesAPP.Services.CloudinaryService;
import com.notesAPP.NotesAPP.Services.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("api/notes")
public class NotesController {

    @Autowired
    private NotesService notesService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private Cloudinary cloudinary;

    String folderName= "Notes";

    @PostMapping("admin/addNotes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NotesEntiry> saveNotes(
            @RequestParam String content,

            @RequestParam(required = false) Long semesterId,
            @RequestParam(required = false) String newSemesterName,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) String newSubjectName,
            @RequestParam(required = false) Long chapterId,
            @RequestParam(required = false) String newChapterName
    ) {
        try {

            // Handle the case where semesterId, subjectId, or chapterId is null
            NotesEntiry createdNotes = notesService.createNotes(
                    content,semesterId, newSemesterName,
                    subjectId, newSubjectName, chapterId, newChapterName
            );

            return new ResponseEntity<>(createdNotes, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //UPLOAD IMAGE FROM TEXT EDITOR
    @PostMapping("admin/upload-images")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<?> uploadImageRichText(@RequestPart("file") MultipartFile file){
        try {

            Imageinfo filename= cloudinaryService.upload(file,folderName);

            String imageUrl = cloudinary.url().generate(filename.publicId());

            return new ResponseEntity<>(imageUrl,HttpStatus.OK);
        }
        catch (Exception e)
        {
       // Return an informative error message
            return new ResponseEntity<>("Error uploading image: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //GET ALL NOTES
    @GetMapping("admin/getAllNotes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllNotes(){

        try{
            List<NotesEntiry> allNotes= notesService.findAllNote();

            return new ResponseEntity<>( allNotes,HttpStatus.OK);

        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //GET NOTES BASED ON CHAPTERS

    @GetMapping("user/getNotesONChap/{chapID}")
    @PreAuthorize("hasRole('USER')")
    public  ResponseEntity<?> getNotesOnChapters(@PathVariable Long chapID){

        try{
                Optional<NotesEntiry> notesEntiries= notesService.findNotesOnChap(chapID);
                return  new ResponseEntity<>(notesEntiries,HttpStatus.OK);


        }
        catch (Exception e)
        {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE NOTES BASES ON CHAPTERS

    @DeleteMapping("admin/deleteNotesOnChap/{chapID}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<?> deleteNotesOnChap( @PathVariable Long chapID){
        try {
            notesService.removeNotes(chapID);
            return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){

            return new ResponseEntity<>("chapId not found ", HttpStatus.NOT_FOUND);
        }

    }

    //UPDATE BASED ON CHAPTERS

    @PutMapping("admin/updateOnChap/{chapID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateNotesOnChap( @RequestBody NotesEntiry content,
                                                @PathVariable Long chapID){

        try {
             NotesEntiry newNoteContent=notesService.updateNoteOnChap(content,chapID);
             return new ResponseEntity<>(newNoteContent,HttpStatus.CREATED);

        }
        catch (Exception e) {
            return  new ResponseEntity<>("ChapId Not Found", HttpStatus.NOT_FOUND);
        }
    }

}
