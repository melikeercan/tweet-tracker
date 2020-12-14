package com.shortcut.interview.repositories;

import java.util.List;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.shortcut.interview.entities.nodes.Tweet;

@RepositoryRestResource(path = "tweets")
public interface TweetRepository extends Repository<Tweet, String> {
  void save(Tweet tweet);

  List<Tweet> findAll();
}
