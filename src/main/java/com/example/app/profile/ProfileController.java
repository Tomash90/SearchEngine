package com.example.app.profile;

import com.example.app.date.LocalDateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Locale;

@Controller
public class ProfileController {

    @RequestMapping("/profile")
    public String showProfile(ProfileFormDto profileFormDto){
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public String saveData(ProfileFormDto profileFormDto){
        System.out.println("Profil zapisano pomyślnie" + profileFormDto);
        return "redirect:/profile";
    }

    @ModelAttribute("dateFormat")
    public String dateFormat(Locale locale){
        return LocalDateFormatter.getPattern(locale);
    }
}
