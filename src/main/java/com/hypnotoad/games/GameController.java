package com.hypnotoad.games;

import com.hypnotoad.responses.FailResponse;
import com.hypnotoad.responses.Response;
import com.hypnotoad.responses.game.AllGamesResponse;
import com.hypnotoad.responses.game.GameResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    OldGameRepository gameRepository;
    static final Logger log = LoggerFactory.getLogger(GameController.class);

    @GetMapping("/api/getGameById")
    public ResponseEntity<Response> getGameById(@RequestParam int id) {
        log.debug("getGameById issued with id {}", id);

        var game = gameRepository.findById(id);
        if (game == null) {
            log.debug("Such game doesn't exists");
            return ResponseEntity.status(401).body(new FailResponse("Game doesn't exists"));
        }

        return ResponseEntity.status(200).body(new GameResponse(game));
    }

    @GetMapping("/api/getGameByName")
    public ResponseEntity<Response> getGameByName(@RequestParam String name) {
        log.debug("getGameByName issued with name {}", name);

        var game = gameRepository.findByName(name);
        if (game == null) {
            log.debug("Such game doesn't exists");
            return ResponseEntity.status(401).body(new FailResponse("Game doesn't exists"));
        }

        return ResponseEntity.status(200).body(new GameResponse(game));
    }

    @GetMapping("/api/getAllGames")
    public ResponseEntity<Response> getAllGames() {
        log.debug("getAllGames issued");

        var games = gameRepository.findAll();
        if (games == null) {
            log.debug("Found null, expected empty list");
            return ResponseEntity.status(500).body(new FailResponse("Couldn't access the database"));
        }

        return ResponseEntity.status(200).body(new AllGamesResponse(games));
    }

    public GameController(OldGameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
}
