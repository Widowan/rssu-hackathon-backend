package com.hypnotoad.games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.List;

@Service
public class GameService {
    final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public Game getById(int id) {
        return gameRepository.findById(id).get();
    }

    public Game getByName(String name) {
        var r = gameRepository.findByName(name);
        if (r == null)
            throw new ValidationException("Game with this name doesn't exists");
        return r;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
}
