package codesquad.springcafe.model.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentCreationDto {
    private final long articleSequence;
    private String writer;
    private final String content;

    public CommentCreationDto(long articleSequence, String content) {
        this.articleSequence = articleSequence;
        this.content = content;
    }

}
