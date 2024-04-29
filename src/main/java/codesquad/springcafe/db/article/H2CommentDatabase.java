package codesquad.springcafe.db.article;

import codesquad.springcafe.model.comment.CommentCreationDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class H2CommentDatabase implements CommentDatabase {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2CommentDatabase(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("comments")
                .usingColumns("article_sequence", "writer", "content");
    }

    @Override
    public void addComment(CommentCreationDto commentData) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("article_sequence", commentData.getArticleSequence());
        parameters.put("writer", commentData.getWriter());
        parameters.put("content", commentData.getContent());
        simpleJdbcInsert.execute(new MapSqlParameterSource(parameters));
    }

    @Override
    public void delete(long sequence) {

    }
}
