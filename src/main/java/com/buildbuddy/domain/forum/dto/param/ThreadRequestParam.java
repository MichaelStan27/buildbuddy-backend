package com.buildbuddy.domain.forum.dto.param;

import com.buildbuddy.util.spesification.ParamFilter;
import com.buildbuddy.util.spesification.QueryOperator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThreadRequestParam {

    private List<Integer> threadIdList;
    private List<String> usernameList;
    private String post;

    // Pagination
    @Builder.Default
    private boolean pagination = false;

    @Builder.Default
    private Integer pageNo = 0;

    @Builder.Default
    private Integer pageSize = 10;

    @Builder.Default
    private String sortBy = "lastUpdateTime";

    @Builder.Default
    private String sortDirection = "asc";

    public List<ParamFilter> getFilters(){

        List<ParamFilter> paramFilters = new ArrayList<>();

        if(threadIdList != null && !threadIdList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                            .field("id")
                            .operator(QueryOperator.IN)
                            .values(threadIdList)
                    .build());
        }

        if(usernameList != null && !usernameList.isEmpty()){
            paramFilters.add(ParamFilter.builder()
                            .field("username")
                            .operator(QueryOperator.USERNAME)
                            .values(usernameList)
                    .build());
        }

        if(post != null){
            paramFilters.add(ParamFilter.builder()
                            .field("post")
                            .operator(QueryOperator.LIKE)
                            .value(post)
                    .build());
        }

        return paramFilters;
    }
}