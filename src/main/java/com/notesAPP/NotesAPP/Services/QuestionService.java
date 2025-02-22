package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Dto.ImageInfoMultipleImage;
import com.notesAPP.NotesAPP.Dto.Imageinfo;
import com.notesAPP.NotesAPP.Entiry.SubjectEntity;
import com.notesAPP.NotesAPP.Entiry.TestEntity;
import com.notesAPP.NotesAPP.Repo.QuestionRepo;
import com.notesAPP.NotesAPP.Repo.SubjectRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private SubjectRepo subjectRepo;
    String folderName= "Questions";

    //POST NEW QUESTIONS
    public TestEntity addqn(TestEntity testEntity) {



        return questionRepo.save(testEntity);
    }

    //ADD QUESTION UPDATE
    @Transactional
    public  TestEntity addQuestions(String qName,
                                    String qYears,
                                    String qType ,
                                    String subName,
                                    List<MultipartFile> file) throws IOException {


        List<Imageinfo> imageUrl= cloudinaryService.uploadListImages(file,folderName);
        List<ImageInfoMultipleImage> imageDataList = imageUrl.stream()
                .map(img -> new ImageInfoMultipleImage(img.publicId(),img.securedurl()))
                .toList();
        Optional<SubjectEntity> subjectEntityOptional = subjectRepo.findBySubjectName(subName);

        if(subjectEntityOptional.isPresent()) {
            SubjectEntity subjectEntity = subjectEntityOptional.get();
            TestEntity add = new TestEntity();
            add.setName(qName);
            add.setYear(qYears);
            add.setType(qType);
            add.setSubjectEntity(subjectEntity);
            add.setImageurls(imageDataList);
            return questionRepo.save(add);
        }else
            throw new RuntimeException("Subject not Defined ");

    }

    //GET ALL QUESTIONS DETAILS
    public List<TestEntity> getAllQns(){
        return questionRepo.findAll();
    }

    // UPDATE EXIST DATA USING QID
    @Transactional
    public TestEntity updateQn(Long qid,
                               String qName,
                               String qYears,
                               String qType ,
                               List<String> deleteId,
                               List<MultipartFile> file) throws IOException {
        Optional<TestEntity> oldQuestionInDb = questionRepo.findById(qid);
        if(oldQuestionInDb.isPresent()){
            TestEntity t= oldQuestionInDb.get();
            //TEXT UPDATE HERE
            t.setName(qName);
            t.setYear(qYears);
            t.setType(qType);

            //FIRST DELETE IF RQU FOR IMAGE

            if (deleteId != null) {
                cloudinaryService.deleteImages(deleteId);
                cloudinaryService.deleteImages(deleteId);
                t.setImageurls(t.getImageurls()
                        .stream()
                        .filter(img -> !deleteId.contains(img.getPublicId()))
                        .collect(Collectors.toList()));
            }
            //IMAGE UPDATE HERE

            if (!file.isEmpty()) {

                List<Imageinfo> imageUrl = cloudinaryService.uploadListImages(file,folderName);
                List<ImageInfoMultipleImage> imageDataList = imageUrl.stream()
                        .map(img -> new ImageInfoMultipleImage(img.publicId(), img.securedurl()))
                        .toList();
                t.getImageurls().addAll(imageDataList);

            }

            return questionRepo.save(t);
        }
        else
         throw  new RuntimeException("Question ID Not Found "+ qid );

    }

    //DELETE QNS BY QID
public  void  deleteQn(Long qid) throws IOException {
        Optional<TestEntity> testEntity= questionRepo.findById(qid);


    if(testEntity.isPresent()){
        TestEntity dQuestion= testEntity.get();
        if(dQuestion.getImageurls()!=null && !dQuestion.getImageurls().isEmpty()){
            List<String>publicIds=dQuestion.getImageurls().stream()
                    .map(ImageInfoMultipleImage::getPublicId)
                    .toList();
            cloudinaryService.deleteImages(publicIds);
        }
        questionRepo.deleteById(qid);

    }
    else
        throw new RemoteException("Solution Id Not found ");
}


//Get Qn BASED ON SUB ID ONLY

    public List<TestEntity> getQnBySub(Long subId){
        return questionRepo.findBySubjectId(subId);
    }

    //GET QN IMAGE ONLY BASED ON SEM AND SUBJECT ONLY

    public List<String> getImageUrlBySubAndSem(Long subId){
        List<TestEntity> qnEntities = questionRepo.findBySubjectId(subId);
        return qnEntities.stream()
                .flatMap(qn -> qn.getImageurls().stream()) // Get all images
                .map(ImageInfoMultipleImage::getImageUrl) // Extract only imageUrl
                .collect(Collectors.toList()); //
    }

}
