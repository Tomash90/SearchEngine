package com.example.app.controller;

import com.example.app.config.PictureUploadProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@SessionAttributes("picturePath")
public class PictureUploadController {

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
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws IOException {
        if(file.isEmpty()|| !isImage(file)){
            redirectAttributes.addFlashAttribute("error", "Load image file.");
            return "redirect:/upload";
        }
        Resource picturePath = copyFileToImages(file);
        model.addAttribute("picturePath", picturePath);
        return "profile/uploadPage";
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
    public void getUploadImage(@ModelAttribute("picturePath") Resource imagePath, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setHeader("Content-Type", URLConnection.guessContentTypeFromName(imagePath.toString()));
        Path path= Paths.get(imagePath.getURI());
        Files.copy(path, httpServletResponse.getOutputStream());
    }

    @ModelAttribute("picturePath")
    public Resource picturePath() {
        return anonymousPicture;
    }

    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOException(IOException exception){
        ModelAndView modelAndView = new ModelAndView("profile/uploadPage");
        modelAndView.addObject("error", exception.getMessage());
        return modelAndView;
    }

    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }
}
