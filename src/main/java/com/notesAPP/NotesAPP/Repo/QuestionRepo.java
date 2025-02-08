package com.notesAPP.NotesAPP.Repo;

import com.notesAPP.NotesAPP.Entiry.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface QuestionRepo extends JpaRepository<TestEntity, Long> {


    List<TestEntity> findBySemAndSub(String sem, String sub);

    List<TestEntity> findImageUrlBySemAndSub(String sem, String sub);
}
