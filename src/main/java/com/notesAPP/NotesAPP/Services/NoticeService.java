package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Entiry.NoticeEntity;
import com.notesAPP.NotesAPP.Repo.NoticeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NoticeService {

    @Autowired
    private NoticeRepo noticeRepo;
    @Autowired
    private SSENotificationService sseNotificationService;

    //SAVE NEW NOTICE
    public void saveNotice(String noticeName,String noticeMessage){
        NoticeEntity newNotice= new NoticeEntity();
        newNotice.setNoticeMessage(noticeMessage);
        newNotice.setNoticeName(noticeName);
        newNotice.setCreatedDate(LocalDateTime.now());
        noticeRepo.save(newNotice);
        sseNotificationService.sendNotice(newNotice.getNoticeMessage());
    }

    //GET ALL NOTICE
    public List<NoticeEntity> getAllNotices(){

        return noticeRepo.findAll();
    }

    //DELETE NOTICE
    public void deleteNotice(Long nid) {

        noticeRepo.deleteById(nid);
    }

    //GET NOTICE BASED ON TIME
    public void pushNotice(LocalDateTime createdDate){

        List<NoticeEntity> recentNotice= noticeRepo.findByCreatedDateAfter(createdDate);

    }


    //UPDATE NOTICE

    public NoticeEntity updateNotice(NoticeEntity noticeEntity, Long nid){
        Optional<NoticeEntity> oldNotice= noticeRepo.findById(nid);
        if(oldNotice.isPresent() && !oldNotice.isEmpty()){

            NoticeEntity newNotice= oldNotice.get();
            newNotice.setNoticeName(noticeEntity.getNoticeName());
            newNotice.setNoticeMessage(noticeEntity.getNoticeMessage());
             return noticeRepo.save(newNotice);
        }
        else
            throw  new RuntimeException("Notice Id Not Found"+nid);


    }
}

