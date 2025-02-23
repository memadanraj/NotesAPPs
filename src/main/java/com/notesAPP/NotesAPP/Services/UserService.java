package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Entiry.RolesUserEntity;
import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Repo.RoleRepo;
import com.notesAPP.NotesAPP.Repo.UserRepo;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public List<UserEntity> getAll(){
        return userRepo.findAll();
    }

    // USER CREATING THEIR ACCOUNT
    public void regUserWithRole(String userName,  String uPassword,String uEmail){
        // Assign default role "USER"
        RolesUserEntity userRole = roleRepo.findByRoleName("ADMIN");
        UserEntity add = new UserEntity();
        add.setUsername(userName);
        add.setPassword(bCryptPasswordEncoder.encode(uPassword));
        add.setEmail(uEmail);
        add.setRolesUserEntities(Set.of(userRole));

        userRepo.save(add);
    }

    //ADMIN CREATING NEW ACCOUNT
    @Transactional
    public  void createUserAdmin(String userName,  String uPassword, String uEmail,String roleName){
        UserEntity addByAdmin = new UserEntity();
        addByAdmin.setUsername(userName);
        addByAdmin.setPassword(bCryptPasswordEncoder.encode(uPassword));
        addByAdmin.setEmail(uEmail);
        RolesUserEntity roles = roleRepo.findByRoleName(roleName);

        if(roles==null){
            RolesUserEntity userRole = roleRepo.findByRoleName("USER");
            addByAdmin.setRolesUserEntities(Set.of(userRole));

        }
       else
           addByAdmin.setRolesUserEntities(Set.of(roles));

        userRepo.save(addByAdmin);
    }

    //  Admin Creating a New Role
    public RolesUserEntity createRole(String roleName) {
        RolesUserEntity newRole = new RolesUserEntity();
        newRole.setRoleName(roleName);
        return roleRepo.save(newRole);
    }
}
