package com.example.app.controller;

import com.example.app.config.PictureUploadProperties;
import com.example.app.profile.UserProfileSession;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

@Controller
public class PictureUploadController {

    private final Resource imageDir;
    private final Resource anonymousPicture;
    private final MessageSource messageSource;
    private UserProfileSession userProfileSession;

    @Autowired
    public PictureUploadController(PictureUploadProperties pictureUploadProperties, MessageSource messageSource, UserProfileSession userProfileSession){
        this.imageDir = pictureUploadProperties.getUploadPath();
        this.anonymousPicture = pictureUploadProperties.getAnonymousPicture();
        this.messageSource = messageSource;
        this.userProfileSession = userProfileSession;
    }

    @RequestMapping("upload")
    public String uploadPage() {
        return "profile/uploadPage";
    }

    @RequestMapping(value = "/profile", params = {"upload"}, method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws IOException {
        if(file.isEmpty() || !isImage(file)){
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("upload.file.bad.format",null,Locale.getDefault()));
            return "redirect:/profile";
        }
        Resource picturePath = copyFileToImages(file);
        userProfileSession.setPicturePath(picturePath);
        return "redirect:profile";
    }

    private Resource copyFileToImages(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String fileExtension = getFileExtension(filename);
        File tempFile = File.createTempFile("pic", fileExtension, imageDir.getFile());
        try (InputStream in = file.getInputStream();
             OutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return new FileSystemResource(tempFile);
    }

    @RequestMapping(value = "/uploadImage")
    public void getUploadImage(HttpServletResponse httpServletResponse) throws IOException {
        Resource picturePath = userProfileSession.getPicturePath();
        if(picturePath == null){
            picturePath = anonymousPicture;
        }
        httpServletResponse.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.toString()));
        Path path= Paths.get(picturePath.getURI());
        Files.copy(path, httpServletResponse.getOutputStream());
    }

    @ModelAttribute("picturePath")
    public Resource picturePath() {
        return anonymousPicture;
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(Locale locale){
        ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
        modelAndView.addObject("error", messageSource.getMessage("upload.io.exception",null, locale));
        return modelAndView;
    }

    @RequestMapping("uploadError")
    public ModelAndView onUploadError(Locale locale){
        ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
        modelAndView.addObject("error", messageSource.getMessage("upload.file.too.big", null, locale));
        return modelAndView;
    }

    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }
}
