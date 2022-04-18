package com.gcb.FeedService.controller;

import com.gcb.FeedService.entity.Feed;
import com.gcb.FeedService.repository.FeedRepository;
import com.google.common.collect.ImmutableList;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feed")
public class FeedController {

    @Autowired
    FeedRepository repository;
    
    @ApiResponse(responseCode = "201", description = "Feed Criado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    @PostMapping(value = "/create")
    public ResponseEntity<Feed> createFeed(@RequestBody Feed feed) throws Exception {
        
        Feed newFeed = new Feed(
                feed.getRequestId(),
                "Create", 
                "Processing", 
                new Date().toString(),
                "", 
                false,
                "");

        feed.setCreatedAt(new Date().toString());
        try {

            repository.save(feed);
            
        } catch (Exception ex) {
            
            return new ResponseEntity<>(feed, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return new ResponseEntity<Feed>(feed, HttpStatus.CREATED);
    }
    
    @ApiResponse(responseCode = "204", description = "Feed alterado com sucesso.")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    @PutMapping(value = "/update")
    public ResponseEntity<Feed> updateStatus(@RequestBody Feed feed) throws Exception{
        System.out.println("Reques body: "+feed);
        try {
            var resp = repository.save(feed);
            System.out.println(resp.getStatus());
            System.out.println("resp>: "+resp);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
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

    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
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
