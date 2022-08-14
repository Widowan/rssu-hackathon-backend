package com.hypnotoad.gameresults;

import com.hypnotoad.auth.token.TokenDto;
import com.hypnotoad.games.GameId;
import com.hypnotoad.responses.FailResponse;
import com.hypnotoad.responses.Response;
import com.hypnotoad.responses.SuccessResponse;
import com.hypnotoad.responses.gameresults.AllGameResultsResponse;
import com.hypnotoad.responses.gameresults.GameTotalResultResponse;
import com.hypnotoad.responses.gameresults.LeaderboardResponse;
import com.hypnotoad.users.ExistingUsername;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;

@RestController
public class GameResultController {
    final GameResultService gameResultService;

    public GameResultController(GameResultService gameResultService) {
        this.gameResultService = gameResultService;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<FailResponse> mismatchHandler(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(new FailResponse("Bad Request"));
    }

    @GetMapping("/api/v2/gameResult/get/byUser/{username}/game/{gameId}")
    public ResponseEntity<Response> getAllGameResultsByUsernameAndGameId(
            @Valid ExistingUsername username, @Valid GameId gameId) {
        var r = gameResultService.getResultsByUsernameAndGameId(
            username.username(), gameId.gameId());
        return ResponseEntity.ok().body(new AllGameResultsResponse(r));
    }

    @GetMapping("/api/v2/gameResult/get/byUser/{username}")
    public ResponseEntity<Response> getAllGameResultsByUsername(@Valid ExistingUsername username) {
        var r = gameResultService.getResultsByUsername(username.username());
        return ResponseEntity.ok().body(new AllGameResultsResponse(r));

    }

    @GetMapping("/api/v2/gameResult/auth/token/{token}/add/{result}")
    public ResponseEntity<Response> addGameResult(@Valid TokenDto token,
            @Valid GameResultNoTimestampDto result) {
        gameResultService.addUserGameResult(token.token(), result);
        return ResponseEntity.ok(new SuccessResponse("ok"));
    }

    @GetMapping("/api/v2/gameResult/get/leaderboard/game/{gameId}")
    public ResponseEntity<Response> getLeaderboard(@Valid GameId gameId) {
        var r = gameResultService.getLeaderboard(gameId.gameId());
        return ResponseEntity.ok(new LeaderboardResponse(r));
    }

    @GetMapping("/api/v2/gameResult/get/gameTotalResult/user/{username}/game/{gameId}")
    public ResponseEntity<Response> getUserGameTotalResult(@Valid ExistingUsername username,
            @Valid GameId gameId) {
        var r = gameResultService.getTotalResult(
            username.username(), gameId.gameId());
        return ResponseEntity.ok().body(new GameTotalResultResponse(r));
    }
}
