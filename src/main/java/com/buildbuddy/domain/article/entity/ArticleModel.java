package com.buildbuddy.domain.article.entity;

import java.time.LocalDateTime;

public interface ArticleModel {

    Integer getArticleId();
    String getUsername();
    String getTitle();
    String getPost();
    String getStatus();
    byte[] getImage();
    LocalDateTime getCreatedTime();
    LocalDateTime getLastUpdateTime();

}
