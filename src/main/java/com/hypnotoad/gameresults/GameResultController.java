package com.hypnotoad.gameresults;

import com.hypnotoad.auth.token.UserPrimitiveTokensRepository;
import com.hypnotoad.games.OldGameRepository;
import com.hypnotoad.responses.FailResponse;
import com.hypnotoad.responses.Response;
import com.hypnotoad.responses.gameresults.AllGameResultsResponse;
import com.hypnotoad.responses.gameresults.GameResultResponse;
import com.hypnotoad.responses.gameresults.GameTotalResultResponse;
import com.hypnotoad.responses.gameresults.LeaderboardResponse;
import com.hypnotoad.users.OldUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameResultController {
    OldUserRepository userRepository;
    UserPrimitiveTokensRepository userPrimitiveTokensRepository;
    OldGameResultRepository gameResultRepository;
    OldGameRepository gameRepository;
    static final Logger log = LoggerFactory.getLogger(GameResultController.class);

    @GetMapping("/api/getAllGameResultsByUser")
    public ResponseEntity<Response> getAllGameResultsByUser(@RequestParam String token) {
        log.debug("getAllGameResultsByUser issued with token {}", token);

        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid token"));
        }

        var user = userRepository.findByToken(token);
        var gameResults = gameResultRepository.findAllByUserId(user.getId());
        if (gameResults == null) {
            log.debug("Found null, expected empty list");
            return ResponseEntity.status(500).body(new FailResponse("Couldn't access the database"));
        }

        return ResponseEntity.status(200).body(new AllGameResultsResponse(gameResults));
    }

    // Okay this is basically copy-paste already, aspects ASAP
    @GetMapping("/api/getGameResultsByUser")
    public ResponseEntity<Response> getGameResultsByUser(@RequestParam String token, @RequestParam int gameId) {
        log.debug("getGameResultsByUser issued with token {}", token);

        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid token"));
        }

        var user = userRepository.findByToken(token);
        var gameResults = gameResultRepository.findAllByUserId(user.getId());
        if (gameResults == null) {
            log.debug("Found null, expected empty list");
            return ResponseEntity.status(500).body(new FailResponse("Couldn't access the database"));
        }

        gameResults = gameResults.stream().filter(gr -> gr.getGameId() == gameId).toList();
        return ResponseEntity.status(200).body(new AllGameResultsResponse(gameResults));
    }

    @GetMapping("/api/addGameResult")
    public ResponseEntity<Response> addGameResult(@RequestParam String token,
            @RequestParam int gameId, @RequestParam boolean result,
            @RequestParam int score, @RequestParam float timeElapsed
    ) {
        log.debug("addGameResult issued with token {}", token);

        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid token"));
        }

        var game = gameRepository.findById(gameId);
        if (game == null) {
            log.debug("Such game doesn't exists");
            return ResponseEntity.status(401).body(new FailResponse("Game doesn't exists"));
        }

        var user = userRepository.findByToken(token);
        var gameResult = gameResultRepository.createGameResult(
                user.getId(), gameId, result, score, timeElapsed);
        if (gameResult == null) {
            return ResponseEntity.status(500).body(new FailResponse("Couldn't create database entry"));
        }

        return ResponseEntity.status(200).body(new GameResultResponse(gameResult));
    }

    public GameResultController(OldUserRepository userRepository,
            UserPrimitiveTokensRepository userPrimitiveTokensRepository,
            OldGameResultRepository gameResultRepository,
            OldGameRepository gameRepository
    ) {
        this.gameResultRepository = gameResultRepository;
        this.userPrimitiveTokensRepository = userPrimitiveTokensRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
    }

    @GetMapping("/api/getLeaderboard")
    public ResponseEntity<Response> getLeaderboard(@RequestParam String token, @RequestParam int gameId) {
        log.debug("getLeaderboard issued with token {}", token);

        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return  ResponseEntity.status(403).body(new FailResponse("Invalid token"));
        }

        var game = gameRepository.findById(gameId);
        if (game == null) {
            log.debug("Such game doesn't exists");
            return ResponseEntity.status(401).body(new FailResponse("Game doesn't exists"));
        }

        var user = userRepository.findByToken(token);
        var leaderboard = gameResultRepository.getLeaderboard(user.getId(), gameId);
        if (leaderboard == null) {
            log.debug("Expected empty list, got null");
            return ResponseEntity.status(500).body(new FailResponse("Couldn't connect to database"));
        }

        return ResponseEntity.status(200).body(new LeaderboardResponse(leaderboard));
    }

    @GetMapping("/api/getGameTotalResult")
    public ResponseEntity<Response> getGameTotalResult(
            @RequestParam String token,
            @RequestParam int gameId,
            @RequestParam int days
    ) {
        var valid = userPrimitiveTokensRepository.validateToken(token);
        if (!valid) {
            log.debug("Provided token is invalid");
            return ResponseEntity.status(403).body(new FailResponse("Invalid token"));
        }

        var game = gameRepository.findById(gameId);
        if (game == null) {
            log.debug("Such game doesn't exists");
            return ResponseEntity.status(401).body(new FailResponse("Game doesn't exists"));
        }

        var user = userRepository.findByToken(token);
        var gameResults = gameResultRepository.findGameTotalResultForDays(
                user.getId(), gameId, days);
        if (gameResults == null) {
            log.debug("Nothing found");
            return ResponseEntity.status(401).body(new FailResponse("Nothing found"));
        }

        return ResponseEntity.status(200).body(new GameTotalResultResponse(gameResults));
    }
}
