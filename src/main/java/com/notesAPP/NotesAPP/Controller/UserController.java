package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userinfo")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("admin/getAll")
    @PreAuthorize("hasRole('ADMIN')")
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

    @PostMapping("public/reg")
    public ResponseEntity<?> regUser(@RequestParam String uName,
                                     @RequestParam String uPassword,
                                     @RequestParam String uEmail) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        userService.regUserWithRole(uName,uPassword,uEmail);
        return ResponseEntity.ok("User Created ");
    }

    //ADMIN CREATE ACCOUNT
    @PostMapping("admin/reg")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createUserByAdmin(@RequestParam String uName,
                                               @RequestParam String uPassword,
                                               @RequestParam String uEmail,
                                               @RequestParam (required = false) String roleName){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getName();
        userService.createUserAdmin(uName,uPassword,uEmail,roleName);
        return ResponseEntity.ok("User is Created Admin");
    }

    //ADMIN ADD ROLE
    @PostMapping("admin/addRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRoleAdmin(@RequestParam String roleName){
        userService.createRole(roleName);
        return ResponseEntity.ok("Role Created ");
    }




}
