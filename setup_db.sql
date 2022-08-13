BEGIN;
CREATE TABLE IF NOT EXISTS Users(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(64) NOT NULL UNIQUE,
    password_hash CHAR(128)   NOT NULL,
    avatar        TEXT            NULL
);
CREATE TABLE IF NOT EXISTS UserTokens(
    -- TODO: Make userid pk and update tokens instead of creating new
    user_id     INTEGER  NOT NULL REFERENCES Users(id),
    token       CHAR(32) NOT NULL PRIMARY KEY,
    -- TODO: Replace integer with proper timestamp
    expiry_time BIGINT   NOT NULL
);
CREATE TABLE IF NOT EXISTS Games(
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL,
    rules       TEXT NOT NULL,
    icon        TEXT     NULL
);
CREATE TABLE IF NOT EXISTS GameResults(
    id             SERIAL  PRIMARY KEY,
    user_id        INTEGER NOT NULL REFERENCES Users(id),
    game_id        INTEGER NOT NULL REFERENCES Games(id),
    result         BOOLEAN     NULL,
    score          INTEGER NOT NULL,
    time_elapsed   FLOAT   NOT NULL,
    date_timestamp INTEGER NOT NULL DEFAULT extract(epoch FROM now())::Integer
);
CREATE TABLE IF NOT EXISTS Achievements(
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS AcquiredAchievements(
    user_id        INTEGER NOT NULL REFERENCES Users(id),
    achievement_id INTEGER NOT NULL REFERENCES Achievements(id),
    acquire_time   BIGINT  NOT NULL,
    PRIMARY KEY (user_id, achievement_id)
);
COMMIT;
