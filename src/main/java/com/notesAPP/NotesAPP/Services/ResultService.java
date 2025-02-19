package com.notesAPP.NotesAPP.Services;

import com.cloudinary.Cloudinary;
import com.notesAPP.NotesAPP.Dto.PDFinfo;
import com.notesAPP.NotesAPP.Entiry.ResultEntity;
import com.notesAPP.NotesAPP.Repo.ResultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private ResultRepo resultRepo;

    public ResultEntity uploadResults(String resultName, MultipartFile pdfFIle) throws IOException {

        PDFinfo pdFinfo = cloudinaryService.uploadPDF(pdfFIle);
//        String pdfUrl = cloudinaryService.generateImgUrl(pdFinfo.publicId());

        ResultEntity result= new ResultEntity();
        result.setResultName(resultName);
        result.setResultPublicId(pdFinfo.publicId());
        result.setResultUrl(pdFinfo.securedurl());
        return resultRepo.save(result);

    }

// GET ALL RESULTS
    public List<ResultEntity> findAllResults() {
        return resultRepo.findAll();
    }
//DELETE RESULT BY ID
    public void deleteResult(Long resultId) throws IOException {
        Optional<ResultEntity> found= resultRepo.findById(resultId);
        if(found.isPresent() && !found.isEmpty()){
           ResultEntity resultIndb= found.get();
           String foundPublicId= resultIndb.getResultPublicId();
           cloudinaryService.delete(foundPublicId);
            resultRepo.deleteById(resultId);
        }


    }
}
