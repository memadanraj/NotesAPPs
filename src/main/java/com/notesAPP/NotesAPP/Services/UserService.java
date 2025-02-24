package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Entiry.RolesUserEntity;
import com.notesAPP.NotesAPP.Entiry.UserEntity;
import com.notesAPP.NotesAPP.Impl.JWTService;
import com.notesAPP.NotesAPP.Repo.RoleRepo;
import com.notesAPP.NotesAPP.Repo.UserRepo;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public List<UserEntity> getAll(){
        return userRepo.findAll();
    }

    // USER CREATING THEIR ACCOUNT
    public void regUserWithRole(String userName,  String uPassword,String uEmail){
        // Assign default role "USER"
        RolesUserEntity userRole = roleRepo.findByRoleName("USER");
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
    @Transactional
    public RolesUserEntity createRole(String roleName) {
        RolesUserEntity newRole = new RolesUserEntity();
        newRole.setRoleName(roleName);
        return roleRepo.save(newRole);
    }

    //DELETE USER ACCOUNT
    public void  removeUserAcc(Long uId){
        userRepo.deleteById(uId);

    }

    //FIND USER ENTITY BY USER NAME
    public  UserEntity findByUserName(String userName){

        return userRepo.findByusername(userName);
    }

    //UPDATE INFO PW FOR USER
    public void updateUserInfo(String userName, String newPassword){

        UserEntity userEntity = userRepo.findByusername(userName);
        userEntity.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepo.save(userEntity);
    }

    //UPDATE USER BY ADMIN
    public UserEntity  updateUserInfoAndRole(Long userId,String newPasswordUser, String roleName){
        Optional<UserEntity> oldUserEntity = userRepo.findById(userId);
        if(oldUserEntity.isPresent()){

            UserEntity newUserEntity= oldUserEntity.get();
            newUserEntity.setPassword(bCryptPasswordEncoder.encode(newPasswordUser));
            RolesUserEntity roles = roleRepo.findByRoleName(roleName);
            if(roles!=null){
                newUserEntity.addRole(roles);
            }
            return userRepo.save(newUserEntity);
        }
        else
            throw new RuntimeException("User Id Not Found ");

    }
//USER LOGIN VERIFY HERE TEST
    public String verifyUserLogin(String username, String userPassword) {

        Authentication authentication= authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(username,
                       userPassword));
       if (authentication.isAuthenticated())
           return jwtService.generateToken(username);
       return "failed";

    }
}
