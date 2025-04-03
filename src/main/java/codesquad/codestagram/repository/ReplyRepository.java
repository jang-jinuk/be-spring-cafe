package codesquad.codestagram.repository;

import codesquad.codestagram.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByArticle_IdAndDeleted(Long articleId, boolean deleted);
    boolean existsByArticleIdAndUserIdNotAndDeletedFalse(@Param("articleId") Long articleId, @Param("userId") Long userId);
}
