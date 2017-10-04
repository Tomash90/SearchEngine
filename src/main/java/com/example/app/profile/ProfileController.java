package com.example.app.profile;

import com.example.app.date.LocalDateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class ProfileController {

    private UserProfileSession userProfileSession;

    @Autowired
    public ProfileController(UserProfileSession userProfileSession) {
        this.userProfileSession = userProfileSession;
    }

    @ModelAttribute
    public ProfileFormDto getProfileFormDto(){
        return userProfileSession.toForm();
    }

    @RequestMapping("/profile")
    public String showProfile(ProfileFormDto profileFormDto){
        return "profile/profilePage";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST, params = {"save"})
    public String saveData(@Valid ProfileFormDto profileFormDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "profile/profilePage";
        }
        userProfileSession.saveForm(profileFormDto);
        return "redirect:/profile";
    }

    @ModelAttribute("dateFormat")
    public String dateFormat(Locale locale){
        return LocalDateFormatter.getPattern(locale);
    }

    @RequestMapping(value = "/profile", params = {"addTaste"})
    public String addRow(ProfileFormDto profileFormDto){
        profileFormDto.getTastes().add(null);
        return "profile/profilePage";
    }
    @RequestMapping(value = "/profile", params = {"removeTaste"})
    public String removeRow(ProfileFormDto profileFormDto, HttpServletRequest request){
        Integer rowId = Integer.valueOf(request.getParameter("removeTaste"));
        profileFormDto.getTastes().remove(rowId.intValue());
        return "profile/profilePage";
    }

}
