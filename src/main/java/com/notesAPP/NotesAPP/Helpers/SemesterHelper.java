package com.notesAPP.NotesAPP.Helpers;

import com.notesAPP.NotesAPP.Entiry.SemesterEntiry;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import com.notesAPP.NotesAPP.Repo.SemesterRepo;
import com.notesAPP.NotesAPP.Repo.SubjectRepo;
import lombok.experimental.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SemesterHelper {

    @Autowired
    private SemesterRepo semesterRepo;

    public SemesterEntiry findSemOrCreate(Long oldSemID, String newSemName){

        if(oldSemID!=null){
            return semesterRepo.findById(oldSemID)
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
        }
        else if(newSemName!=null &&!newSemName.isEmpty() ) {

        //CHECK IF ALREADY EXIST
        Optional<SemesterEntiry> existingSemester = semesterRepo.findBySemsterName(newSemName);
        if (existingSemester.isPresent()) {
            return existingSemester.get(); // Return existing semester
        }

        // IF NOT FOUND CREATE NEW SEM
        SemesterEntiry newSemesterEntity = new SemesterEntiry();
        newSemesterEntity.setSemsterName(newSemName);
        return semesterRepo.save(newSemesterEntity);

        }
        else {
            throw new RuntimeException("Semester not found");
        }

    }
}
