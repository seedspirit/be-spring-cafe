package codesquad.springcafe.model.article.dto;

public class ArticleModificationDto {

    private final String title;
    private final String content;

    public ArticleModificationDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
