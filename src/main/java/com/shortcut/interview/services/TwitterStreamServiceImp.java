package com.shortcut.interview.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.shortcut.interview.entities.dao.TweetBuilder;
import com.shortcut.interview.entities.nodes.Country;
import com.shortcut.interview.entities.nodes.Tweet;
import com.shortcut.interview.entities.relationships.TweetCountryRelationship;
import com.shortcut.interview.exceptions.EntityNotFoundException;
import com.shortcut.interview.exceptions.Twitter4JException;
import com.shortcut.interview.repositories.TweetRepository;
import com.shortcut.interview.utils.ConfigurationSingleton;
import com.shortcut.interview.utils.LocationUtils;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

@Service
public class TwitterStreamServiceImp extends BaseServiceImp implements TwitterStreamService {
  protected final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final TweetRepository tweetRepository;

  private final CountryServiceImp countryService;
  private TwitterStream twitterStream;

  public TwitterStreamServiceImp(TweetRepository tweetRepository,
      CountryServiceImp countryService) {
    this.tweetRepository = tweetRepository;
    this.countryService = countryService;
  }

  public TwitterStream establishConnection() {
    try {
      Configuration config = ConfigurationSingleton.getInstance().getConfiguration();
      twitterStream = new TwitterStreamFactory(config).getInstance();
    } catch (Exception ex) {
      throw new Twitter4JException(TwitterStream.class, "establishConnection");
    }
    return twitterStream;
  }

  public HttpStatus stopSteaming() {
    try {
      twitterStream.shutdown();
    } catch (Exception ex) {
      throw new Twitter4JException(TwitterStream.class, "stopSteaming");
    }

    return HttpStatus.OK;
  }

  public void startStreaming(String[] hashtags) {
    TwitterStream twitterStream = establishConnection();

    StatusListener statusListener = new StatusListener() {
      @Override
      public void onStatus(Status status) {
        if (status != null) {
          processTweet(status);
        }
      }

      @Override
      public void onDeletionNotice(StatusDeletionNotice sdn) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void onTrackLimitationNotice(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void onScrubGeo(long l, long l1) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void onStallWarning(StallWarning sw) {
        throw new UnsupportedOperationException("Not supported yet.");
      }

      @Override
      public void onException(Exception ex) {
        logger.error("onException()", ex);
        throw new Twitter4JException(StatusListener.class, "exception", ex.getMessage());
      }
    };

    try {
      FilterQuery fq = new FilterQuery();
      fq.language("en");
      fq.track(hashtags);

      twitterStream.addListener(statusListener);
      logger.info("Setting up filters. Keywords = {}", String.join(",", hashtags));
      twitterStream.filter(fq);
    } catch (Exception ex) {
      throw new Twitter4JException(TwitterStream.class, "startStreaming",
          String.join(",", hashtags));
    }
  }

  private void processTweet(Status status) {
    String location = status.getUser().getLocation();
    List<Country> countryList = countryService.findAll();
    String countryName = LocationUtils.findCountry(location, countryList);
    Country country = countryService.findByName(countryName);
    if (country == null) {
      throw new EntityNotFoundException(Country.class, "name", countryName);
    }
    Status retweetedStatus = status.getRetweetedStatus();
    Integer retweetCount = retweetedStatus == null ? 0 : retweetedStatus.getRetweetCount();

    TweetBuilder builder = new TweetBuilder(status.getText())
        .setCreatedAt(status.getCreatedAt())
        .setRetweetCount(retweetCount)
        .setTweetId(status.getId());

    Set<String> mentionedCountryNames = LocationUtils
        .findMentionedCountries(status.getText(), countryList);
    Set<TweetCountryRelationship> mentionedTweetsRelationship = new HashSet<>();

    if (mentionedCountryNames.size() > 0) {
      Tweet tweet = builder.build();
      TweetCountryRelationship tweetCountryRelationship = new TweetCountryRelationship(
          country, tweet, location);
      tweet.setTweetCountryRelationship(tweetCountryRelationship);
      mentionedCountryNames.forEach(mentionedCountryName -> {
        Country mentionedCountry = countryService.findByName(mentionedCountryName);
        if (mentionedCountry != null) {
          TweetCountryRelationship mentionedCountryRelationship = new TweetCountryRelationship(
              mentionedCountry, tweet);
          mentionedTweetsRelationship.add(mentionedCountryRelationship);
        }
      });

      tweet.setMentionedCountries(mentionedTweetsRelationship);
      try {
        tweetRepository.save(tweet);
      } catch (Exception ex) {
        throw new Twitter4JException(TwitterStream.class, "onStatus", tweet.getText());
      }
    }
  }

}
