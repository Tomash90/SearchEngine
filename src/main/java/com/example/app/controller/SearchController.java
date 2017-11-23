package com.example.app.controller;

import com.example.app.search.TwitterSearch;
import com.example.app.search.api.LightTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {

    private TwitterSearch searchService;

    @Autowired
    public SearchController(TwitterSearch searchService) {
        this.searchService = searchService;
    }
    @RequestMapping("/search/{searchType}")
    public ModelAndView search(@PathVariable String searchType, @MatrixVariable List<String> keywords){
        List<LightTweet> tweets = searchService.search(searchType,keywords);
        ModelAndView modelAndView = new ModelAndView("resultPage");
        modelAndView.addObject("tweets", tweets);
        modelAndView.addObject("search",String.join(",",keywords));
        return modelAndView;
    }
}
