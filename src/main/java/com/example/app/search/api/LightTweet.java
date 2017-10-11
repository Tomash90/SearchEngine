package com.example.app.search.api;

import lombok.Data;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
public class LightTweet {
    private String profileImageUrl;
    private String user;
    private String text;
    private LocalDateTime date;
    private String lang;
    private Integer retweetCount;

    public LightTweet(String text) {
        this.text = text;
    }

    public static LightTweet ofTweet(Tweet tweet){
        LightTweet lightTweet = new LightTweet(tweet.getText());
        Date dateOfcreation = tweet.getCreatedAt();
        if(dateOfcreation != null){
            lightTweet.date = LocalDateTime.ofInstant(dateOfcreation.toInstant(), ZoneId.systemDefault());
        }
        TwitterProfile twitterProfile = tweet.getUser();
        if(twitterProfile != null){
            lightTweet.user = twitterProfile.getName();
            lightTweet.profileImageUrl = twitterProfile.getProfileImageUrl();
        }
        lightTweet.lang = tweet.getLanguageCode();
        lightTweet.retweetCount = tweet.getRetweetCount();
        return lightTweet;
    }
}
