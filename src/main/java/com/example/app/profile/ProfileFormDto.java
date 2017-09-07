package com.example.app.profile;

import com.example.app.date.PastLocaleDate;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileFormDto {
    @Size(min = 2, max = 20)
    private String twitterHandle;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @PastLocaleDate
    private LocalDate birthDay;
    @NotEmpty
    private List<String> tastes = new ArrayList<>();
}
