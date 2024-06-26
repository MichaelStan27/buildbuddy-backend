package com.buildbuddy.domain.forum.entity;

import java.time.LocalDateTime;

public interface ThreadModel {
    Integer getThreadId();
    String getPost();
    String getUsername();
    Integer getUserId();
    byte[] getUserProfile();
    Integer getTotalLike();
    long getIsLikedByUser();
    LocalDateTime getCreatedTime();
    LocalDateTime getLastUpdateTime();
}
