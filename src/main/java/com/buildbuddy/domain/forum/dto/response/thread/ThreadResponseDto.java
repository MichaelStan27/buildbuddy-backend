package com.buildbuddy.domain.forum.dto.response.thread;

import com.buildbuddy.domain.forum.entity.ThreadEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadResponseDto {

    @JsonProperty(value = "threadId")
    private Integer threadId;

    @JsonProperty(value = "post")
    private String post;

    @JsonProperty(value = "username")
    private String username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "createdTime")
    private LocalDateTime createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty(value = "lastUpdateTime")
    private LocalDateTime lastUpdateTime;

    public static ThreadResponseDto convertToDto(ThreadEntity entity){
        return ThreadResponseDto.builder()
                .threadId(entity.getId())
                .post(entity.getPost())
                .username(entity.getUser().getUsername())
                .createdTime(entity.getCreatedTime())
                .lastUpdateTime(entity.getLastUpdateTime())
                .build();
    }
}