package com.notesAPP.NotesAPP.Impl;

import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserdetailsService implements UserDetailsService {

@Autowired
private UserRepo userRepo;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByusername(username);

        if(userEntity==null)
            throw new UsernameNotFoundException(username);

        return  new UserdetailsPrinciple(userEntity);
    }
}
