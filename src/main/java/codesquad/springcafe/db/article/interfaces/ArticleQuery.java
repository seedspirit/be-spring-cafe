package codesquad.springcafe.db.article.interfaces;

import codesquad.springcafe.model.article.dto.ArticlePreviewDto;
import codesquad.springcafe.model.article.dto.ArticleProfileDto;

import java.util.List;
import java.util.Optional;

public interface ArticleQuery {
    public List<ArticlePreviewDto> findAllArticles();
    public Optional<ArticleProfileDto> findArticleBySequence(long sequence);
    public int getTotalArticleNumber();
}
