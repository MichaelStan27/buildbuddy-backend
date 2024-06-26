package com.buildbuddy.domain.article.repository;

import com.buildbuddy.domain.article.entity.ArticleEntity;
import com.buildbuddy.domain.article.entity.ArticleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer>, JpaSpecificationExecutor<ArticleEntity> {

        Optional<ArticleEntity> findByIdAndUserId(Integer id, Integer userId);

        @Query(nativeQuery = true, value = "select a.article_id as articleId, u.username as username, u.user_id as userId, a.title as title, " +
                "a.post as post, a.image as image, a.created_time as createdTime, a.last_update_time as lastUpdateTime, " +
                "(case when exists (select * from article_like where article_id = a.article_id AND user_id = :userId) then true else false end) as isLikedByUser, " +
                "(select count(*) from article_like where article_id = a.article_id) AS totalLike " +
                "from article a join user u on a.user_id = u.user_id " +
                "where a.article_id = (case when :articleId is null then a.article_id else :articleId end) " +
                "and (u.username like (case when :search is null then u.username else :search end) " +
                "or a.title like (case when :search is null then a.title else :search end) " +
                "or a.post like (case when :search is null then a.post else :search end) " +
                ")")
        Page<ArticleModel> getByCustomParam(@Param("userId") Integer userId,
                                            @Param("articleId") Integer articleId,
                                            @Param("search") String search, Pageable pageable);

}
