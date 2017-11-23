package com.example.app.search.api;

import com.example.app.search.TwitterSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchApiController {

    private TwitterSearch searchService;

    @Autowired
    public SearchApiController(TwitterSearch searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/{searchType}", method = RequestMethod.GET)
    public List<LightTweet> search(@PathVariable String searchType, @MatrixVariable List<String> keywords){
        return searchService.search(searchType, keywords);
    }
}
