package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<?>getAllUsers(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            List<UserEntity> userEntities= userService.getAll();
            return  new ResponseEntity<>(userEntities,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/reg")
    public ResponseEntity<?> example(@RequestBody UserEntity requestBody) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        userService.saveNewUser(requestBody);
        return ResponseEntity.ok(requestBody);
    }





}
