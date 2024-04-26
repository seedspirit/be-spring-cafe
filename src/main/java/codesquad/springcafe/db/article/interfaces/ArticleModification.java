package codesquad.springcafe.db.article.interfaces;

import codesquad.springcafe.model.article.Article;
import codesquad.springcafe.model.article.dto.ArticleModificationDto;

public interface ArticleModification {
    public void addArticle(Article article);
    public void update(long sequence, ArticleModificationDto modificationData);
    public void clearDatabase();
}
