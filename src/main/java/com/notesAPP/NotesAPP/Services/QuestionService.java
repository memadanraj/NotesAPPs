package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Entiry.QuestionsEntity;
import com.notesAPP.NotesAPP.Repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;

    //add question paper
    public QuestionsEntity addQuestion(QuestionsEntity questionsEntity, MultipartFile file) throws IOException {
         questionsEntity.setFilename(file.getOriginalFilename());
         questionsEntity.setFiletype(file.getContentType());
         questionsEntity.setFiledata(file.getBytes());
        return questionRepo.save(questionsEntity);
    }
}
