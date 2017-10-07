package com.example.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private Twitter twitter;

    @Autowired
    public SearchService(Twitter twitter){
        this.twitter = twitter;
    }

    public List<Tweet> search(String searchType, List<String> keywords){
        List<SearchParameters> searches = keywords.stream().map(taste -> createSearchParam(searchType, taste)).collect(Collectors.toList());
        List<Tweet> tweets = searches.stream().map(params -> twitter.searchOperations().search(params))
                .flatMap(searchResults -> searchResults.getTweets().stream())
                .collect(Collectors.toList());
        return tweets;
    }

    private SearchParameters.ResultType getResultType(String searchType){
        for(SearchParameters.ResultType knowType : SearchParameters.ResultType.values()){
            if(knowType.name().equalsIgnoreCase(searchType)){
                return knowType;
            }
        }
        return SearchParameters.ResultType.RECENT;
    }

    private SearchParameters createSearchParam(String searchType,String taste){
        SearchParameters.ResultType resultType = getResultType(searchType);
        SearchParameters searchParameters = new SearchParameters(taste);
        searchParameters.resultType(resultType);
        searchParameters.count(3);
        return searchParameters;
    }
}
