package com.notesAPP.NotesAPP.Services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.notesAPP.NotesAPP.Dto.Imageinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }


    public Imageinfo upload(MultipartFile file) throws IOException {

       Map uploadResult= cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap());

      Imageinfo imageinfo= new Imageinfo(
               uploadResult.get("public_id").toString(),
               uploadResult.get("secure_url").toString(),
               uploadResult.get("format").toString()
               );
return imageinfo;
    }

//    public List<Imageinfo> uploadListImages (List<MultipartFile> imageurls) throws IOException {
//
//        List<Imageinfo> imageInfoList= new ArrayList<>();
//
//        for (MultipartFile file : imageurls) {
//            Map uploadResult = cloudinary.uploader()
//                    .upload(file.getBytes(), ObjectUtils.emptyMap());
//
//            Imageinfo imageinfo = new Imageinfo(
//                    uploadResult.get("public_id").toString(),
//                    uploadResult.get("secure_url").toString(),
//                    uploadResult.get("format").toString()
//            );
//            imageInfoList.add(imageinfo);
//        }
//        return imageInfoList;
//    }


    //to serve image
    public String generateImgUrl( String publicId)
    {
        return  cloudinary.url().generate(publicId);

    }

// remove image
    public void delete(String publicId) throws IOException {
        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
