package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Entiry.NoticeEntity;
import com.notesAPP.NotesAPP.Repo.NoticeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepo noticeRepo;

    //SAVE NEW NOTICE
    public NoticeEntity saveNotice(NoticeEntity noticeEntity){

        return noticeRepo.save(noticeEntity);
    }

    //GET ALL NOTICE
    public List<NoticeEntity> getAllNotices(){

        return noticeRepo.findAll();
    }
}
