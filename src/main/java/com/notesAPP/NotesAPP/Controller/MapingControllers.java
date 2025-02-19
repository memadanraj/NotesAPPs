package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Dto.SubjectDto;
import com.notesAPP.NotesAPP.Repo.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/mapping")
public class MapingControllers {

    @Autowired
    private SubjectRepo subjectRepo;

   @GetMapping("/sub")
   public ResponseEntity<List<SubjectDto>> getAllSubjects() {
       try {
           List<SubjectDto> subjectEntities = subjectRepo.findAll()
                   .stream()
                   .map(subject -> new SubjectDto(subject.getSubId(), subject.getSubjectName()))
                   .collect(Collectors.toList());



           return ResponseEntity.ok(subjectEntities);
       } catch (Exception e) {
           return ResponseEntity.badRequest().build();
       }
   }

}
