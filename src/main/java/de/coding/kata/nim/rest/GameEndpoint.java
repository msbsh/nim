package de.coding.kata.nim.rest;

import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.service.GameService;
import de.coding.kata.nim.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/players/{playerName}/games",
                produces = MediaType.APPLICATION_JSON_VALUE,
                consumes = MediaType.APPLICATION_JSON_VALUE)
public class GameEndpoint {

    private PlayerService playerService;
    private GameService gameService;

    public GameEndpoint(@Autowired final PlayerService playerService,
                        @Autowired final GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@PathVariable("playerName") final String playerName) {
        log.debug("Starting new game for player {}", playerName);

        final Player player = playerService.getOrCreatePlayerForName(playerName);
        return gameService.startGame(player);
    }

    @PostMapping("/{gameId}/take/{numberOfMatches}")
    @ResponseStatus(HttpStatus.OK)
    public Game takeMatches(@PathVariable("playerName") final String playerName,
                            @PathVariable("gameId") final String gameId,
                            @PathVariable("numberOfMatches") final int numberOfMatches) {
        log.debug("{} about to take {} matches for game {}", playerName, numberOfMatches, gameId);

        final Player player = playerService.getOrCreatePlayerForName(playerName);
        final Game game = gameService.getGameById(gameId);

        return gameService.takeMatches(game, player, numberOfMatches);
    }

}
