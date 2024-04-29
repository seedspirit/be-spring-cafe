package codesquad.springcafe.model.comment;

public class CommentCreationDto {
    private final long articleSequence;
    private String writer;
    private final String content;

    public CommentCreationDto(long articleSequence, String content) {
        this.articleSequence = articleSequence;
        this.content = content;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public long getArticleSequence() {
        return articleSequence;
    }

    public String getContent() {
        return content;
    }

    public String getWriter() {
        return writer;
    }
}
