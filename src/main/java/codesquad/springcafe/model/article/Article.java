package codesquad.springcafe.model.article;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
public class Article {
    private final AtomicLong sequenceFactory = new AtomicLong();
    private long sequence;
    private String writer;
    private LocalDateTime publishTime;
    private String title;
    private String content;

    public Article() {
        this.writer = "익명";
        this.publishTime = LocalDateTime.now();
    }

    public Article(LocalDateTime publishTime, String title, String content) {
        this.writer = "익명";
        this.publishTime = publishTime;
        this.title = title;
        this.content = content;
    }
}
