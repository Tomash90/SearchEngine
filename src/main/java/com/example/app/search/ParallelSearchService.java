package com.example.app.search;

import com.example.app.search.api.LightTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Service
@Profile("async")
public class ParallelSearchService implements TwitterSearch {
    private final AsyncSearch asyncSearch;

    @Autowired
    public ParallelSearchService(AsyncSearch asyncSearch) {
        this.asyncSearch = asyncSearch;
    }

    @Override
    public List<LightTweet> search(String searchType, List<String> keywords) {
        CountDownLatch latch = new CountDownLatch(keywords.size());
        List<LightTweet> allTweets = Collections.synchronizedList(new ArrayList<>());
        keywords.stream().forEach(keyword-> asyncFetch(latch, allTweets,searchType,keyword));
        await(latch);
        return allTweets;
    }

    private void await(CountDownLatch latch){
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new IllegalStateException();
        }
    }

    private void asyncFetch(CountDownLatch latch, List<LightTweet> allTweets, String searchType, String keyword) {
        asyncSearch.asyncFetch(searchType,keyword).addCallback(tweets-> onSuccess(allTweets,latch,tweets),ex -> onError(latch,ex));
    }

    private void onError(CountDownLatch latch, Throwable ex) {
        ex.printStackTrace();
        latch.countDown();
    }

    private void onSuccess(List<LightTweet> allTweets, CountDownLatch latch, List<LightTweet> tweets) {
        allTweets.addAll(tweets);
        latch.countDown();
    }
}
