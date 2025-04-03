package codesquad.codestagram.repository;

import codesquad.codestagram.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByArticle_Id(Long articleId);
    @Query("SELECT COUNT(r) > 0 FROM Reply r WHERE r.article.id = :articleId AND r.user.id != :userId")
    boolean existsOtherUserReplyByArticleId(@Param("articleId") Long articleId, @Param("userId") Long userId);
}
