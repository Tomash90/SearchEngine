package com.example.app.controller;

import com.example.app.config.PictureUploadProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@Controller
public class PictureUploadController {

    public static final Resource PICTURE_DIR = new FileSystemResource("./pictures");
    private final Resource imageDir;
    private final Resource anonymousPicture;

    @Autowired
    public PictureUploadController(PictureUploadProperties pictureUploadProperties){
        imageDir = pictureUploadProperties.getUploadPath();
        anonymousPicture = pictureUploadProperties.getAnonymousPicture();
    }

    @RequestMapping("upload")
    public String uploadPage() {
        return "profile/uploadPage";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        if(file.isEmpty()|| !isImage(file)){
            redirectAttributes.addFlashAttribute("error", "Load image file.");
            return "redirect:/upload";
        }
        String filename = file.getOriginalFilename();
        String fileExtension = getFileExtension(filename);
        File tempFile = File.createTempFile("pic", fileExtension, imageDir.getFile());
        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return "profile/uploadPage";
    }

    @RequestMapping(value = "/uploadImage")
    public void getUploadImage(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setHeader("Content-Type", URLConnection.guessContentTypeFromName(anonymousPicture.getFilename()));
        IOUtils.copy(anonymousPicture.getInputStream(), httpServletResponse.getOutputStream());
    }

    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }
}
