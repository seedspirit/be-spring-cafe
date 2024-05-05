package codesquad.springcafe.model.article.dto;

import lombok.Getter;

@Getter
public class ArticleModificationDto {

    private final String title;
    private final String content;

    public ArticleModificationDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
