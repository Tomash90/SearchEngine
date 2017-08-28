package com.example.app.profile;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProfileFormDto {
    @Min(value = 2)
    private String twitterHandle;
    @Email
    @NotEmpty
    private String email;
    @NotNull
    private LocalDate birthDay;
    @NotEmpty
    private List<String> tastes = new ArrayList<>();
}
