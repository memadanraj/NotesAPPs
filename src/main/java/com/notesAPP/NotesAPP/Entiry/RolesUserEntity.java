package com.notesAPP.NotesAPP.Entiry;

import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = "user_roles")
public class RolesUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_user_id",nullable = false,unique = true)
    private  Long roleUserId;

    private String roleName;
    @ManyToMany(mappedBy = "rolesUserEntities")
    private Collection<UserEntity> users;

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }

    public Long getRoleUserId() {
        return roleUserId;
    }

    public void setRoleUserId(Long roleUserId) {
        this.roleUserId = roleUserId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
