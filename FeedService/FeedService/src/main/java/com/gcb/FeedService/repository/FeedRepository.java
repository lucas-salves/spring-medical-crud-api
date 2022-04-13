package com.gcb.FeedService.repository;

import com.gcb.FeedService.entity.Feed;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface FeedRepository extends CrudRepository<Feed, String>{
    
}
