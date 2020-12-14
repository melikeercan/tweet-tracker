package com.shortcut.interview.exceptions;

import java.util.Map;

import org.springframework.util.StringUtils;

public class Twitter4JException extends BaseException {

  public Twitter4JException(Class className, String... searchParamsMap) {
    super(Twitter4JException
        .generateMessage(className.getSimpleName(),
            toMap(String.class, String.class, searchParamsMap)));
  }

  private static String generateMessage(String entity, Map<String, String> searchParams) {
    return StringUtils.capitalize(entity) + " Twitter4j api error " + searchParams;
  }

}

