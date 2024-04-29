package codesquad.springcafe.model.article.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleProfileDto {
    private final long sequence;
    private final LocalDateTime publishTime;
    private final String userId;
    private final String writerNickname;
    private final String title;
    private final String content;
    private final boolean isDeleted;
    private final String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일 HH:mm";

    public ArticleProfileDto(long sequence, LocalDateTime publishTime, String userId, String writerNickname, String title, String content, boolean isDeleted) {
        this.sequence = sequence;
        this.publishTime = publishTime;
        this.userId = userId;
        this.writerNickname = writerNickname;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
    }

    public long getSequence() {
        return sequence;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public String getUserId() {
        return userId;
    }

    public String getFormattedPublishTime(){
        return publishTime.format(DateTimeFormatter.ofPattern(TIME_FORMATTING_PATTERN));
    }

    public String getWriterNickname() {
        return writerNickname;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isDeleted() {
        return isDeleted;
    }
}
