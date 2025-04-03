package codesquad.codestagram.repository;

import codesquad.codestagram.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByDeleted(boolean deleted);
}
