package codesquad.springcafe.db.article;

import codesquad.springcafe.model.comment.CommentCreationDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

public class H2CommentDatabase implements CommentDatabase {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2CommentDatabase(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    @Override
    public void addComment(CommentCreationDto commentData) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(commentData));
    }

    @Override
    public void delete(long sequence) {

    }
}
