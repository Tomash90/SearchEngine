package com.example.app.date;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.time.LocalDate;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastLocaleDate.PastValidator.class)
@Documented
public @interface PastLocaleDate {

    String message() default "{javax.validation.constraints.Past.message}";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
    class PastValidator implements ConstraintValidator<PastLocaleDate, LocalDate>{
        public void initialize(PastLocaleDate date){}

        @Override
        public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
            return value == null || value.isBefore(LocalDate.now());
        }
    }

}
