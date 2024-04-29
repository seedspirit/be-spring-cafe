package codesquad.springcafe.model.comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommentPreviewDto {
    private final long sequence;
    private final String writer;
    private final String content;
    private final LocalDateTime writtenTime;
    private final String TIME_FORMATTING_PATTERN = "yyyy년 MM월 dd일 HH:mm";

    public CommentPreviewDto(long sequence, String writer, String content, LocalDateTime writtenTime) {
        this.sequence = sequence;
        this.writer = writer;
        this.content = content;
        this.writtenTime = writtenTime;
    }

    public long getSequence() {
        return sequence;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public String getFormattedWrittenTime() {
        return writtenTime.format(DateTimeFormatter.ofPattern(TIME_FORMATTING_PATTERN));
    }
}
