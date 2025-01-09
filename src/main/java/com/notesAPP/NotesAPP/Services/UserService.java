package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public List<UserEntity> getAll(){
        return userRepo.findAll();
    }

    public void saveNewUser(UserEntity userEntity){
        userRepo.save(userEntity);
    }
}
