package com.notesAPP.NotesAPP.Services;

import com.notesAPP.NotesAPP.Dto.Imageinfo;
import com.notesAPP.NotesAPP.Entiry.TestEntity;
import com.notesAPP.NotesAPP.Repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;

    @Autowired
    private CloudinaryService cloudinaryService;

    //POST NEW QUESTIONS
    public TestEntity addqn(TestEntity testEntity) {
        return questionRepo.save(testEntity);
    }

    //GET ALL QUESTIONS DETAILS
    public List<TestEntity> getAllQns(){
        return questionRepo.findAll();
    }

    // UPDATE EXIST DATA USING QID
    public TestEntity updateQn(Long qid, TestEntity testEntity , MultipartFile file) throws IOException {
        Optional<TestEntity> oldQuestionInDb = questionRepo.findById(qid);
        if(oldQuestionInDb.isPresent()){
            TestEntity t= oldQuestionInDb.get();
            //TEXT UPDATE HERE
            t.setName(testEntity.getName());
            t.setSub(testEntity.getSub());
            t.setYear(testEntity.getYear());
            t.setType(testEntity.getType());

            //IMAGE UPDATE HERE

            if (file != null && !file.isEmpty()) {
                // Delete the old image from Cloudinary
                if (t.getImageID() != null) {
                    cloudinaryService.delete(t.getImageID());
                }

                // Upload the new image
                Imageinfo uploadedImage = cloudinaryService.upload(file);
                t.setImageID(uploadedImage.publicId());
                t.setImageurl(uploadedImage.securedurl());
            }

            return questionRepo.save(t);
        }
        else
         throw  new RuntimeException("Question ID Not Found "+ qid );

    }

    //DELETE QNS BY QID
public  void  deleteQn(Long qid) throws IOException {
        Optional<TestEntity> testEntity= questionRepo.findById(qid);

        // DELETE IMAGE BY FINDING IMAGEID// PUBLIC ID
    if (testEntity.isPresent()) {
        TestEntity questionInDb = testEntity.get();
        String imageID = questionInDb.getImageID();
        cloudinaryService.delete(imageID);
        questionRepo.deleteById(qid);

    }

}

}
