package de.coding.kata.nim.rest;

import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.service.GameService;
import de.coding.kata.nim.service.PlayerService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = {"Game"})
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

    @ApiOperation(value = "Start a game by providing a player name")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Game has been created")
    })
    @SuppressWarnings("unused")
    @PostMapping("/start")
    @ResponseStatus(HttpStatus.CREATED)
    public Game createGame(@PathVariable("playerName") @ApiParam(required = true, value = "Player name to start the game for") final String playerName) {
        log.debug("Starting new game for player {}", playerName);

        final Player player = playerService.getOrCreatePlayerForName(playerName);
        return gameService.startGame(player);
    }

    @ApiOperation(value = "Play a round by taking matches")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Turn has been played"),
            @ApiResponse(code = 400, message = "Game is unknown, player is not known (or it's not her term), game has ended")
    })
    @SuppressWarnings("unused")
    @PostMapping("/{gameId}/take/{numberOfMatches}")
    @ResponseStatus(HttpStatus.OK)
    public Game takeMatches(@PathVariable("playerName") @ApiParam(required = true, value = "Name of the player") final String playerName,
                            @PathVariable("gameId") @ApiParam(required = true, value = "Id of the game") final String gameId,
                            @PathVariable("numberOfMatches") @ApiParam(required = true, value = "Number of matches to take") final int numberOfMatches) {
        log.debug("{} is about to take {} matches for game {}", playerName, numberOfMatches, gameId);

        final Player player = playerService.getOrCreatePlayerForName(playerName);
        final Game game = gameService.getGameById(gameId);

        return gameService.playRound(game, player, numberOfMatches);
    }

}
