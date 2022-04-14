package com.gcb.FeedService.controller;

import com.gcb.FeedService.entity.Feed;
import com.gcb.FeedService.repository.FeedRepository;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    FeedRepository repository;

    @PostMapping(value = "/create")
    public ResponseEntity<Feed> createFeed(@RequestBody Feed feed) throws Exception {

        feed.setRequestAction("createFeed");

        feed.setStatus("Queued");

        feed.setCreatedAt(new Date().toString());

        try {

            repository.save(feed);
        } catch (Exception ex) {

            throw new Exception(ex.getMessage());

        }
        return new ResponseEntity<Feed>(feed, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/getAll")
    public ResponseEntity<List<Feed>> listAll() throws Exception {

        try {

            Iterable feedsResponse = repository.findAll();

            List<Feed> feeds = ImmutableList.copyOf(feedsResponse);

            return new ResponseEntity<>(feeds, HttpStatus.OK);
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

    }

    @GetMapping(value = "/get")
    public ResponseEntity<Feed> getFeed(@RequestParam(name = "feedId") String feedId) throws Exception {
        try{
            var feed = repository.findById(feedId).get();
            
            return new ResponseEntity<>(feed, HttpStatus.OK);
        }catch(Exception ex){
            throw new Exception(ex.getMessage());
        }
    }

}
