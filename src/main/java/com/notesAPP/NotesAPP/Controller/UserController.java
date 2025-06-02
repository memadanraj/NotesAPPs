package com.notesAPP.NotesAPP.Controller;

import com.notesAPP.NotesAPP.Dto.LoginRequestDto;
import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Impl.JWTService;
import com.notesAPP.NotesAPP.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/userinfo")

public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    //GET ALL USER INFORMATION ADMIN
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

    // PUBLIC REGISTER FOR ACCOUNT
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
        String adminName= authentication.getName();

        userService.createUserAdmin(uName,uPassword,uEmail,roleName);
        return ResponseEntity.ok("User is Created Admin"+adminName);
    }

    //ADMIN ADD ROLE
    @PostMapping("admin/addRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRoleAdmin(@RequestParam String roleName){
        userService.createRole(roleName);
        return ResponseEntity.ok("Role Created ");
    }

    //DELETE USER ACCOUNT BY USER SELF
    @DeleteMapping("user/removeAcc")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> removeUserAccount (){

        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            UserEntity dUserEntity = userService.findByUserName(username);
            Long userId= dUserEntity.getUid();
            userService.removeUserAcc(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //USER REMOVE BY ADMIN USING USER ID
    @DeleteMapping("admin/removeUserAccs/{userIds}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> removeUsersByAdmin(@PathVariable Long userIds){
        try {
            userService.removeUserAcc(userIds);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>("User Id Not Found ",HttpStatus.NOT_FOUND);
        }

    }

    //UPDATE USER INFO BY USER
    @PutMapping("user/pwChange")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUserInfoPw(@RequestParam String uPassword ){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            userService.updateUserInfo(username,uPassword);
            // i thing some validation required here from frontend or backend what ever
            return new ResponseEntity<>(HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    //UPDATE USER DETAILS BY ADMINS/ ADD ROle
    @PutMapping("admin/updateUserInfo/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserByAdmin(@RequestParam String newPassword ,
                                                   @RequestParam (required = false )String roleName,
                                                   @PathVariable Long userId){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
           UserEntity userEntity=  userService.updateUserInfoAndRole(userId,newPassword,roleName);
            return new ResponseEntity<>(userEntity,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/public/login")
    public ResponseEntity<?> userLoginEntry(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getUserName(), loginRequestDto.getUserPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(token);  // Return the token
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);  // Unauthorized access
        }
    }



}
