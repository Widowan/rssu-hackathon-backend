package com.hypnotoad.games;

import com.hypnotoad.responses.Response;
import com.hypnotoad.responses.game.AllGamesResponse;
import com.hypnotoad.responses.game.GameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@RestController
public class GameController {
    final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/api/v2/games/get/game/{gameId}")
    public ResponseEntity<Response> getGameById(@Valid GameId gameId) {
        var r = gameService.getById(gameId.gameId());
        return ResponseEntity.ok().body(new GameResponse(r));
    }

    @GetMapping("/api/v2/games/get/name/{name}")
    public ResponseEntity<Response> getGameByName(@Valid
            @NotBlank(message = "Name cannot be empty")
            @Size(max = 128, message = "Name cannot be longer than 128 characters")
            @PathVariable
            String name) {
        var r = gameService.getByName(name);
        return ResponseEntity.ok().body(new GameResponse(r));
    }

    @GetMapping("/api/v2/games/get/all")
    public ResponseEntity<Response> getAllGames() {
        var r = gameService.getAllGames();
        return ResponseEntity.ok().body(new AllGamesResponse(r));
    }
}
