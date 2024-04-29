package codesquad.springcafe.db.article;

import codesquad.springcafe.model.comment.CommentCreationDto;

public interface CommentDatabase {
    public void addComment(CommentCreationDto commentData);
    public void delete(long sequence);

}
