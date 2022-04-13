package com.gcb.FeedService.controller;

import com.gcb.FeedService.entity.Feed;
import com.gcb.FeedService.repository.FeedRepository;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    FeedRepository repository;

    @PostMapping(value = "/createFeed")
    public ResponseEntity<Feed> createFeed(@RequestBody Feed feed) {
        
        feed.setRequestAction("createFeed");
        
        feed.setStatus("Queued");
        
        feed.setCreatedAt(new Date().toString());
        
        try {
            
            repository.save(feed);
            
        } catch (Exception ex) {
            
            feed.setFeedErrors(true);
            
            feed.setMessageError("Erro desconhecido.");
            
            return new ResponseEntity<Feed>(feed, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return new ResponseEntity<Feed>(feed, HttpStatus.CREATED);
    }
    
}
