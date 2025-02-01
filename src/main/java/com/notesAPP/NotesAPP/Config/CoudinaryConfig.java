package com.notesAPP.NotesAPP.Config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CoudinaryConfig {

    @Bean
    public Cloudinary getCloudinary(){

        Map config = new HashMap();
        config.put("cloud_name","dczmitp3w");
        config.put("api_key", "341795488313387");
        config.put("api_secret","QLdNf6DrjF3jljds6dsz5va9YcA");
        config.put("secure",true);
        return new Cloudinary(config);
    }
}
