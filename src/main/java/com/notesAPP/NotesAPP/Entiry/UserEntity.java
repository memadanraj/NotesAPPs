package com.notesAPP.NotesAPP.Entiry;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity

@Table(name = "UserDetails")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;

    @Column(name = "username", unique = true, nullable = false)
    private String username;


    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;
    private LocalDateTime createdDate = LocalDateTime.now();


    private int reputation = 0;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles_map",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_user_id"))
    private Set<RolesUserEntity> rolesUserEntities = new HashSet<>();


    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityQuestionEntity> communityQuestions = new ArrayList<>();

    //getterSetter here


    public List<CommunityQuestionEntity> getCommunityQuestions() {
        return communityQuestions;
    }

    public void setCommunityQuestions(List<CommunityQuestionEntity> communityQuestions) {
        this.communityQuestions = communityQuestions;
    }

    public void addRole(RolesUserEntity role) {
        rolesUserEntities.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(RolesUserEntity role) {
        rolesUserEntities.remove(role);
        role.getUsers().remove(this);
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RolesUserEntity> getRolesUserEntities() {
        return rolesUserEntities;
    }

    public void setRolesUserEntities(Set<RolesUserEntity> rolesUserEntities) {
        this.rolesUserEntities = rolesUserEntities;
    }
}
