package codesquad.springcafe.db.article;

import codesquad.springcafe.model.article.Article;
import codesquad.springcafe.model.article.dto.ArticleModificationDto;
import codesquad.springcafe.model.article.dto.ArticleProfileDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Component
public class MemoryArticleDatabase implements ArticleDatabase {
    private final List<Article> articleDatabase = new ArrayList<>();

    public void addArticle(Article article) {
        articleDatabase.add(article);
    }

    @Override
    public void update(long sequence, ArticleModificationDto updatedArticle) {
        return;
    }

    public List<ArticleProfileDto> findAllArticles(){
        return null;
    }

    public Optional<ArticleProfileDto> findArticleBySequence(long sequence){
        return null;
    }

    public void clearDatabase(){
        articleDatabase.clear();
    }


    public int getTotalArticleNumber(){
        return articleDatabase.size();
    }
}