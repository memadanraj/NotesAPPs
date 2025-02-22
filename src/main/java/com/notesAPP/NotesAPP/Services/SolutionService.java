package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Dto.ImageInfoMultipleImage;
import com.notesAPP.NotesAPP.Dto.Imageinfo;
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
import java.util.stream.Collectors;

@Service
public class SolutionService {
    @Autowired
    private SolutionRepo solutionRepo;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private SubjectRepo subjectRepo;
    String folderName= "Solution";

    //ADD SOLUTIONS
    public SolutionEntity UploadNotes(String solutionEntity, List<MultipartFile> file,String subName) throws IOException {

        List<Imageinfo> imageUrl= cloudinaryService.uploadListImages(file,folderName);
        List<ImageInfoMultipleImage> imageDataList = imageUrl.stream()
                .map(img -> new ImageInfoMultipleImage(img.publicId(),img.securedurl()))
                .collect(Collectors.toList());

        Optional<SubjectEntity> subjectEntityOptional = subjectRepo.findBySubjectName(subName);

        if(subjectEntityOptional.isPresent()){
            SubjectEntity subjectEntity = subjectEntityOptional.get();
            SolutionEntity add= new SolutionEntity();
            add.setImages(imageDataList);

            add.setSolName(solutionEntity);
            add.setSubjectEntity(subjectEntity);
            return solutionRepo.save(add);
        }
        else
            throw new RemoteException("Subject is not found broo");

    }
// GET ALL SOLUTIONS

    public List<SolutionEntity> getAllSolutions() {
        return solutionRepo.findAll();
    }

    //GET SOLUTION ON SUBJECT
    public List<SolutionEntity> getSolutionOnSub(Long subId) {

        return solutionRepo.findBySubjectId(subId);

    }

    //UPDATE SOLUTIONS IMAGE AND DELETE IF UPDATED
    public SolutionEntity updateSolution(Long solId,
                                         String solName,
                                         List<MultipartFile> files,
                                         List<String> deleteId) throws IOException {
        Optional<SolutionEntity> oldSolution = solutionRepo.findById(solId);
        if (oldSolution.isPresent()) {
            SolutionEntity newSolution = oldSolution.get();
            newSolution.setSolName(solName);

            if (deleteId != null) {
                cloudinaryService.deleteImages(deleteId);
                cloudinaryService.deleteImages(deleteId);
                newSolution.setImages(newSolution.getImages()
                        .stream()
                        .filter(img -> !deleteId.contains(img.getPublicId()))
                        .collect(Collectors.toList()));
            }

            if (!files.isEmpty()) {

                List<Imageinfo> imageUrl = cloudinaryService.uploadListImages(files,folderName);
                List<ImageInfoMultipleImage> imageDataList = imageUrl.stream()
                        .map(img -> new ImageInfoMultipleImage(img.publicId(), img.securedurl()))
                        .toList();
                newSolution.getImages().addAll(imageDataList);

            }
            return solutionRepo.save(newSolution);
        }
    else {
        throw new RemoteException("Solution Id Not found");
        }
    }
//GET BY NOTES ID ONLY
    public Optional<SolutionEntity> getBySolId(Long solid) {
        return solutionRepo.findById(solid);
    }
//DELETE SOLUTION BY ID
    public void removeById(Long solId) throws IOException {

        Optional<SolutionEntity> solutionEntity= solutionRepo.findById(solId);
        if(solutionEntity.isPresent()){
            SolutionEntity dSolutionEntity= solutionEntity.get();
            if(dSolutionEntity.getImages()!=null && !dSolutionEntity.getImages().isEmpty()){
            List<String>publicIds=dSolutionEntity.getImages().stream()
                    .map(ImageInfoMultipleImage::getPublicId)
                    .toList();
            cloudinaryService.deleteImages(publicIds);
            }
            solutionRepo.deleteById(solId);

        }
        else
            throw new RemoteException("Solution Id Not found ");
    }
}