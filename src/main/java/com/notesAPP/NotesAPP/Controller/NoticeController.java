package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.NoticeEntity;
import com.notesAPP.NotesAPP.Services.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Executable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    //ADD NEW NOTICE HERE
    @PostMapping("addNotice")
    public ResponseEntity<?> addNewNotice(@RequestBody NoticeEntity noticeEntity){

        try {
            noticeService.saveNotice(noticeEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>( e ,HttpStatus.BAD_REQUEST);
        }
    }

    //DISPLAY ALL NOTICE HERE

    @GetMapping("getAllNotice")
    public ResponseEntity<?> getAllNotice(){
        try{
            List<NoticeEntity> getNotices= noticeService.getAllNotices();
            return new ResponseEntity<>(getNotices,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE NOTICE
    @DeleteMapping("removeNotice/{nid}")
    public ResponseEntity<?> removeNoticeById(@PathVariable Long nid){

        try{
            noticeService.deleteNotice(nid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE NOTICE DETAILS
    @PutMapping("updateNotice/{nid}")
    public ResponseEntity<NoticeEntity> updateNoticeById(@RequestBody NoticeEntity uNoticeEntity,@PathVariable Long nid){
        try{

            NoticeEntity updateNotice= noticeService.updateNotice(uNoticeEntity,nid);
            return  new ResponseEntity<>(uNoticeEntity,HttpStatus.OK);




        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
