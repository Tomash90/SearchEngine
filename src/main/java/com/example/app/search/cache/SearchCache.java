package com.example.app.search.cache;

import com.example.app.search.SearchParamsBuilder;
import com.example.app.search.api.LightTweet;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.social.TwitterProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchCache {
    protected final Log logger = LogFactory.getLog(getClass());
    private Twitter twitter;

    @Autowired
    public SearchCache(TwitterProperties properties) {
        this.twitter = new TwitterTemplate(properties.getAppId(), properties.getAppSecret());
    }

    @Cacheable("searches")
    public List<LightTweet> fetch(String searchType, String keyword) {
        logger.info("Lack of word in cache memory");
        SearchParameters searchParameters = SearchParamsBuilder.createSearchParam(searchType, keyword);
        return twitter.searchOperations().search(searchParameters).getTweets().stream().map(LightTweet::ofTweet).collect(Collectors.toList());
    }
}
