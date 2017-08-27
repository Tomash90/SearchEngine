package com.example.app.profile;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileFormDto {
    private String twitterHandle;
    private String email;
    private LocalDate birthDay;
    private List<String> tastes = new ArrayList<>();
}
