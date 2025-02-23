package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.RolesUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository <RolesUserEntity,Long> {
    RolesUserEntity findByRoleName(String roleName);
}
