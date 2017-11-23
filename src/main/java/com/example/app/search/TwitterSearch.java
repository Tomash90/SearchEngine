package com.example.app.search;

import com.example.app.search.api.LightTweet;

import java.util.List;

public interface TwitterSearch {
    List<LightTweet> search(String searchType, List<String> keywords);
}
