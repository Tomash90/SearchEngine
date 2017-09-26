package com.example.app.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

@ConfigurationProperties(prefix = "upload.image")
@Getter
public class PictureUploadProperties {
    private Resource uploadPath;
    private Resource anonymousPicture;

    public void setAnonymousPicture(String anonymousPicture) {
        this.anonymousPicture = new DefaultResourceLoader().getResource(anonymousPicture);
    }

    public void setUploadPath(String uploadPath){
        this.uploadPath = new DefaultResourceLoader().getResource(uploadPath);
    }
}
