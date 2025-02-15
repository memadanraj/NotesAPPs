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

}
