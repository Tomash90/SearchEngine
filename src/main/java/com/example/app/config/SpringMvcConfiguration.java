package com.example.app.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableConfigurationProperties({PictureUploadProperties.class})
public class SpringMvcConfiguration extends WebMvcConfigurerAdapter {
}
