package codesquad.codestagram.repository;

import codesquad.codestagram.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByArticle_IdAndDeleted(Long articleId, boolean deleted);
    @Query("""
        SELECT COUNT(r) > 0 
        FROM Reply r 
        WHERE r.deleted = false 
            AND r.article.id = :articleId 
            AND r.user.id != :userId
        """)
    boolean existsNotDeletedOtherUserReplyByArticleId(@Param("articleId") Long articleId, @Param("userId") Long userId);
}
