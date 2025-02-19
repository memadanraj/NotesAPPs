package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Dto.SubjectDto;
import com.notesAPP.NotesAPP.Entiry.SolutionEntity;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import com.notesAPP.NotesAPP.Repo.SolutionRepo;
import com.notesAPP.NotesAPP.Repo.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

@Service
public class SolutionService {
    @Autowired
    private SolutionRepo solutionRepo;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private SubjectRepo subjectRepo;

    public SolutionEntity UploadNotes(String solutionEntity, List<MultipartFile> file,String subName) throws IOException {

        List<String> imageUrl= cloudinaryService.uploadListImages(file);

        Optional<SubjectEntity> subjectEntityOptional = subjectRepo.findBySubjectName(subName);

        if(subjectEntityOptional.isPresent()){
            SubjectEntity subjectEntity = subjectEntityOptional.get();
            SolutionEntity add= new SolutionEntity();
            add.setImages(imageUrl);
            add.setSolName(solutionEntity);
            add.setSubjectEntity(subjectEntity);
            return solutionRepo.save(add);
        }
        else
            throw new RemoteException("Subject is not found broo");

    }

    public List<SolutionEntity> getAllSolutions() {
        return solutionRepo.findAll();
    }

    public List<SolutionEntity> getSolutionOnSub(Long subId) {

        return solutionRepo.findBySubjectId(subId);

    }
}
