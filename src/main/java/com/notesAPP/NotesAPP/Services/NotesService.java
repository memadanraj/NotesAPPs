package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Dto.Imageinfo;
import com.notesAPP.NotesAPP.Entiry.ChapterEntity;
import com.notesAPP.NotesAPP.Entiry.NotesEntiry;
import com.notesAPP.NotesAPP.Entiry.SemesterEntiry;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import com.notesAPP.NotesAPP.Helpers.ChapterHelper;
import com.notesAPP.NotesAPP.Helpers.SemesterHelper;

import com.notesAPP.NotesAPP.Helpers.SubjectHelper;
import com.notesAPP.NotesAPP.Repo.ChapterRepo;
import com.notesAPP.NotesAPP.Repo.NotesRepo;
import com.notesAPP.NotesAPP.Repo.SemesterRepo;
import com.notesAPP.NotesAPP.Repo.SubjectRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    private SubjectHelper subjectHelper;
    @Autowired
    private SemesterHelper semesterHelper;
    @Autowired
    private ChapterHelper chapterHelper;
    @Autowired
    private ChapterRepo chapterRepo;
    @Autowired
    private NotesRepo notesRepo;
    @Autowired
    private SemesterRepo semesterRepo;
    @Autowired
    private SubjectRepo subjectRepo;
    @Autowired
    private CloudinaryService cloudinaryService;

    @Transactional
    public NotesEntiry createNotes(

            String content,

            Long semesterId,
            String newSemesterName,
            Long subjectId,
            String newSubjectName,
            Long chapterId,
            String newChapterName
    ) throws IOException {
        // Find or create the Subject

        SubjectEntity subjectEntity = subjectHelper.findSubOrCreate(subjectId, newSubjectName,semesterId,newSemesterName);

        // Find or create the Semester
        SemesterEntiry semesterEntity = semesterHelper.findSemOrCreate(semesterId, newSemesterName);

        // Find or create the Chapter
        ChapterEntity chapterEntity = chapterHelper.findChapOrCreate(chapterId, newChapterName,subjectId,newSubjectName,semesterId,newSemesterName);

        // Create a new Note
        NotesEntiry note = new NotesEntiry();
        note.setNotesContent(content);
        note.setChapId(chapterEntity); // Ensure this field name matches in your entity

        // Save the note to the repository
        return notesRepo.save(note);
    }

    //FIND ALL NOTES
    public List<NotesEntiry> findAllNote (){
         return notesRepo.findAll();
    }

    //FIND NOTES BASED ON CHAPTERS

    public Optional<NotesEntiry> findNotesOnChap(long chapsID) {

        return notesRepo.findByChapId(chapsID);
    }

    //UPDATE NOTES CONTENT BASED ON CHAPTERS

    public NotesEntiry updateNoteOnChap(NotesEntiry content, Long chapId){

//        Optional<NotesEntiry> oldNotes= notesRepo.findByChapId(chapId);
       Optional<NotesEntiry> oldNotes= notesRepo.findById(chapId);

        if(oldNotes.isPresent()){

            NotesEntiry newNote= oldNotes.get();
            newNote.setNotesContent(content.getNotesContent());
            return notesRepo.save(newNote);

        }
        else
            throw  new RuntimeException("Question ID Not Found "+ chapId );


    }

    //DELETE NOTES ON CHAPTERS
    public void  removeNotes (long chapID) {


        Optional<NotesEntiry> notes= notesRepo.removeByChapId(chapID);
        System.out.println(notes);

        if(notes.isPresent()){
            NotesEntiry notesEntiry= notes.get();
            notesRepo.delete(notesEntiry);
            chapterRepo.deleteById(chapID);
        }


    }

}
