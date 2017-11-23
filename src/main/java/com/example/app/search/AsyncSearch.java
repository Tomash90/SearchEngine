package com.example.app.search;

import com.example.app.search.api.LightTweet;
import com.example.app.search.cache.SearchCache;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

@Component
public class AsyncSearch {

    protected final Log logger = LogFactory.getLog(getClass());
    private SearchCache searchCache;

    @Autowired
    public AsyncSearch(SearchCache searchCache) {
        this.searchCache = searchCache;
    }

    @Async
    public ListenableFuture<List<LightTweet>> asyncFetch(String searchType, String keyword){
        logger.info(Thread.currentThread().getName() + " - Searching word " + keyword);
        return new AsyncResult<>(searchCache.fetch(searchType,keyword));
    }
}
