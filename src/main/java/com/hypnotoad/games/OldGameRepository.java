package com.hypnotoad.games;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OldGameRepository {
    private final JdbcTemplate jdbc;
    static final Logger log = LoggerFactory.getLogger(OldGameRepository.class);

    private final RowMapper<OldGame> gameRowMapper;

    public OldGame findById(int id) {
        var sql = "SELECT * FROM Games WHERE id = ?";

        try {
            return jdbc.queryForObject(sql, gameRowMapper, id);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public OldGame findByName(String name) {
        var sql = "SELECT * FROM Games WHERE lower(name) = lower(?)";

        try {
            return jdbc.queryForObject(sql, gameRowMapper, name);
        } catch (DataAccessException ignored) {}

        return null;
    }

    public List<OldGame> findAll() {
        var sql = "SELECT * FROM Games";

        try {
            // Maybe I should've created `GameShort` object or something...
            return jdbc.query(sql, gameRowMapper);
        } catch (DataAccessException e) {
            log.error("Couldn't access database: ", e);
            return null;
        }
    }

    public OldGameRepository(String staticUrl, JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.gameRowMapper = (rs, rowNum) -> ImmutableOldGame.builder()
                .id(rs.getInt("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .rules(rs.getString("rules"))
                .icon(staticUrl + rs.getString("icon"))
                .build();
    }
}
