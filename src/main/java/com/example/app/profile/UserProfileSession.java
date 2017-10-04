package com.example.app.profile;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserProfileSession implements Serializable {
    private String twitterHandle;
    private String email;
    private LocalDate birthDay;
    private List<String> tastes = new ArrayList<>();

    public void saveForm(ProfileFormDto profileFormDto){
        this.twitterHandle = profileFormDto.getTwitterHandle();
        this.email = profileFormDto.getEmail();
        this.birthDay = profileFormDto.getBirthDay();
        this.tastes = profileFormDto.getTastes();
    }

    public ProfileFormDto toForm() {
        ProfileFormDto profileFormDto = new ProfileFormDto();
        profileFormDto.setTwitterHandle(twitterHandle);
        profileFormDto.setEmail(email);
        profileFormDto.setBirthDay(birthDay);
        profileFormDto.setTastes(tastes);
        return profileFormDto;
    }
}
