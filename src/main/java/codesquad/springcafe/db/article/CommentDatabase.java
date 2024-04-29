package codesquad.springcafe.db.article;

import codesquad.springcafe.model.comment.CommentPreviewDto;
import codesquad.springcafe.model.comment.CommentCreationDto;

import java.util.List;

public interface CommentDatabase {
    public void addComment(CommentCreationDto commentData);
    public void delete(long sequence);
    public List<CommentPreviewDto> getCommentsOfArticle(long articleSequence);
}
