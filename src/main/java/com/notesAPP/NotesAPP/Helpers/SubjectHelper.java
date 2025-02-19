package com.notesAPP.NotesAPP.Helpers;

import com.notesAPP.NotesAPP.Entiry.SemesterEntiry;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import com.notesAPP.NotesAPP.Helpers.SemesterHelper;
import com.notesAPP.NotesAPP.Repo.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubjectHelper {

    @Autowired
    private SubjectRepo subjectRepo;

    @Autowired
    private SemesterHelper semesterHelper;

    public SubjectEntity findSubOrCreate(Long oldSubID, String newSubName, Long semesterId, String newSemesterName) {

        if (oldSubID != null) {
            return subjectRepo.findById(oldSubID)
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
        }

        if (newSubName != null && !newSubName.isEmpty()) {
            SemesterEntiry semesterEntiry = semesterHelper.findSemOrCreate(semesterId, newSemesterName);

            // ✅ Check if the subject already exists
            Optional<SubjectEntity> existingSubject = subjectRepo.findBySubjectNameAndSemesterEntiry(newSubName, semesterEntiry);
            if (existingSubject.isPresent()) {
                return existingSubject.get(); // ✅ Return existing subject
            }

            // 🔹 Only create a new subject if it does NOT exist
            SubjectEntity newSubEntity = new SubjectEntity();
            newSubEntity.setSemesterEntiry(semesterEntiry);
            newSubEntity.setSubjectName(newSubName);
            return subjectRepo.save(newSubEntity);
        }

        throw new RuntimeException("Subject Not Found");
    }

}
