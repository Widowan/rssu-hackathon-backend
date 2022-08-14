package com.hypnotoad.gameresults;

import com.hypnotoad.auth.token.TokenRepository;
import com.hypnotoad.games.GameRepository;
import com.hypnotoad.users.User;
import com.hypnotoad.users.UserDto;
import com.hypnotoad.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Slf4j
public class GameResultService {
    final GameResultRepository gameResultRepository;
    final GameRepository gameRepository;
    final UserRepository userRepository;
    final ModelMapper modelMapper;
    final Clock clock;
    final TokenRepository tokenRepository;

    public GameResultService(GameResultRepository gameResultRepository, GameRepository gameRepository, UserRepository userRepository, ModelMapper modelMapper, Clock clock, TokenRepository tokenRepository) {
        this.gameResultRepository = gameResultRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.clock = clock;
        this.tokenRepository = tokenRepository;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    List<GameResultDto> getResultsByUsernameAndGameId(String username, int id) {
        var game = gameRepository.findById(id).get();
        var user = userRepository.findByUsername(username);
        return gameResultRepository.findAllByUserAndGame(user, game).stream()
            .map(r -> modelMapper.map(r, GameResultDto.class))
            .toList();
    }

    List<GameResultDto> getResultsByUsername(String username) {
        var user = userRepository.findByUsername(username);
        return gameResultRepository.findAllByUser(user).stream()
            .map(r -> modelMapper.map(r, GameResultDto.class))
            .toList();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void addUserGameResult(User user, GameResultNoTimestampDto result) {
        var game = gameRepository.findById(result.getId()).get();
        var gr = GameResult.builder()
            .game(game)
            .score(result.getScore())
            .dateTimestamp(result.getDateTimestamp())
            .timeElapsed(result.getTimeElapsed())
            .result(result.getResult())
            .user(user)
            .build();
        gameResultRepository.save(gr);
    }

    void addUserGameResult(String token, GameResultNoTimestampDto result) {
        var user = userRepository.findUserByToken_TokenAndToken_ExpiryTimeAfter(
            token, LocalDateTime.now(clock));
        addUserGameResult(user, result);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    List<GameTotalResultDto> getLeaderboard(int gameId) {
        var game = gameRepository.findById(gameId).get();
        var r = gameResultRepository.getLeaderboardByGameIdAndAfterRawIntTimestamp(game, 0);
        return r.stream()
            .map(gtrr -> GameTotalResultDto.builder()
                .gameId(gtrr.getGameId())
                .score(gtrr.getScore())
                .user(modelMapper.map(
                    userRepository.findById(gtrr.getUserId()).get(),
                    UserDto.class))
                .build())
            .toList();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    GameTotalResultDto getTotalResult(User user, int gameId) {
        var game = gameRepository.findById(gameId).get();
        var r = gameResultRepository.getGameTotalResultByUserAndGameAndDateTimestampIsAfter(
            user, game, 0);
        return GameTotalResultDto.builder()
            .gameId(r.getGameId())
            .score(r.getScore())
            .user(modelMapper.map(
                userRepository.findById(r.getUserId()).get(),
                UserDto.class))
            .build();
    }

    GameTotalResultDto getTotalResult(String username, int gameId) {
        var user = userRepository.findByUsername(username);
        return getTotalResult(user, gameId);
    }
}
