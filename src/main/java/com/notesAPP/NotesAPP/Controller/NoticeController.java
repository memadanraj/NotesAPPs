package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.NoticeEntity;
import com.notesAPP.NotesAPP.Services.NoticeService;
import com.notesAPP.NotesAPP.Services.SSENotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.util.List;

@CrossOrigin(value="*")
@RestController
@RequestMapping("api/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    @Autowired
    private SSENotificationService sseNotificationService;

    //ADD NEW NOTICE HERE
    @PostMapping("admin/addNotice")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewNotice(@RequestParam String noticeName,
                                          @RequestParam String noticeMessage) {

        try {
            noticeService.saveNotice(noticeName,noticeMessage);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    //DISPLAY ALL NOTICE HERE

    @GetMapping("user/getAllNotice")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllNotice() {
        try {
            List<NoticeEntity> getNotices = noticeService.getAllNotices();
            return new ResponseEntity<>(getNotices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //DELETE NOTICE
    @DeleteMapping("admin/removeNotice/{nid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeNoticeById(@PathVariable Long nid) {

        try {
            noticeService.deleteNotice(nid);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //UPDATE NOTICE DETAILS
    @PutMapping("admin/updateNotice/{nid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NoticeEntity> updateNoticeById(@RequestBody NoticeEntity uNoticeEntity, @PathVariable Long nid) {
        try {

            NoticeEntity updateNotice = noticeService.updateNotice(uNoticeEntity, nid);
            return new ResponseEntity<>(uNoticeEntity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //NOTICE STREAM OR NOTIFY USER
    @GetMapping("user/notification/stream")
    @PreAuthorize("hasRole('USER')")
    public SseEmitter streamNotifications() {
        return sseNotificationService.createEmitter();
    }
}
