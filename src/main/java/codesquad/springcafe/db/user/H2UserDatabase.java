package codesquad.springcafe.db.user;

import codesquad.springcafe.model.user.User;
import codesquad.springcafe.model.user.dto.UserProfileEditDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class H2UserDatabase implements UserDatabase {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;


    public H2UserDatabase(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public void addUser(User user) {
        simpleJdbcInsert.execute(new BeanPropertySqlParameterSource(user));
    }

    @Override
    public void update(String userId, UserProfileEditDto userProfileEditDto) {
        String sql = "update users set nickname=?, email=? where userid=?";
        jdbcTemplate.update(
                sql,
                userProfileEditDto.getNickname(),
                userProfileEditDto.getEmail(),
                userProfileEditDto.getUserId());
    }

    @Override
    public List<User> findAllUsers() {
        return jdbcTemplate.query("SELECT * FROM users", userRowMapper());
    }

    @Override
    public Optional<User> findUserByUserId(String userId) {
        List<User> result = jdbcTemplate.query("select * from users where userId = ?",
                userRowMapper(), userId);
        return result.stream().findAny();
    }

    @Override
    public void clearDatabase() {
        jdbcTemplate.update("delete from users");
    }

    @Override
    public int getTotalUserNumber() {
        return jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            String userId = rs.getString("userId");
            String nickname = rs.getString("nickname");
            String password = rs.getString("password");
            String email = rs.getString("email");
            LocalDateTime registerTime = rs.getTimestamp("registerTime").toLocalDateTime();

            User user = new User();
            user.setUserId(userId);
            user.setEmail(email);
            user.setNickname(nickname);
            user.setPassword(password);
            user.setRegisterTime(registerTime);
            return user;
        };
    }
}
