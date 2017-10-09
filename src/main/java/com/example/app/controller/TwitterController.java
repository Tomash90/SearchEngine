package com.example.app.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TwitterController {

    @Autowired
    private Twitter twitter;

    @RequestMapping("/result")
    public String hello(@RequestParam(defaultValue = "spring") String search, Model model) {
        SearchResults searchResults = twitter.searchOperations().search(search);
        List<Tweet> tweets= searchResults.getTweets();
        model.addAttribute("tweets", tweets);
        model.addAttribute("search", search);
        return "resultPage";
    }

    @RequestMapping(value = "postSearch", method = RequestMethod.POST)
    public String postSearch(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String search = request.getParameter("search");
        if(search.toLowerCase().contains("garbage")){
            redirectAttributes.addFlashAttribute("error", "Spróbuj wpisać inne słowo");
            return "redirect:/";
        }
        redirectAttributes.addAttribute("search", search);
        return "redirect:result";
    }
}