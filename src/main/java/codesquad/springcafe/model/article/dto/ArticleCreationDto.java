package codesquad.springcafe.model.article.dto;

import codesquad.springcafe.model.article.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArticleCreationDto {
    private final String title;
    private final String content;
    private final LocalDateTime publishTime;

    public ArticleCreationDto(String title, String content) {
        this.title = title;
        this.content = content;
        this.publishTime = LocalDateTime.now();
    }

    public Article toEntity(){
        return new Article(publishTime, title, content);
    }
}
