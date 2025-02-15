package com.notesAPP.NotesAPP.Helpers;

import com.notesAPP.NotesAPP.Entiry.ChapterEntity;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import com.notesAPP.NotesAPP.Repo.ChapterRepo;

import lombok.experimental.Helper;
import org.aspectj.weaver.loadtime.Options;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class ChapterHelper {

    @Autowired
    private ChapterRepo chapterRepo;

    @Autowired
    private SubjectHelper subjectHelper;

    public ChapterEntity findChapOrCreate(Long oldChapID, String newChapName, Long oldSubID, String newSubName, Long semesterId, String newSemesterName) {

        if (oldChapID != null) {
            return chapterRepo.findById(oldChapID)
                    .orElseThrow(() -> new RuntimeException("Chapter not found"));
        }
        else if(newChapName!=null &&!newChapName.isEmpty() ) {
            SubjectEntity oldsubentity = subjectHelper.findSubOrCreate(oldSubID, newSubName, semesterId, newSemesterName);

            Optional<ChapterEntity> existChapter = chapterRepo.findByChapterTitleAndSubjectEntity(newChapName, oldsubentity);
            if (existChapter.isPresent()) {
                return existChapter.get();
            }

            ChapterEntity newChapEntity = new ChapterEntity();
            newChapEntity.setSubjectEntity(oldsubentity);
            newChapEntity.setChapterTitle(newChapName);
            return chapterRepo.save(newChapEntity);
        }
        else {
            throw new RuntimeException("ChapterNotFound ");
        }


    }


}
