package de.coding.kata.nim.service;

import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.exception.GameNotFoundException;
import de.coding.kata.nim.exception.NotYourTurnException;
import de.coding.kata.nim.repository.GameRepository;
import de.coding.kata.nim.service.strategy.GameStrategy;
import de.coding.kata.nim.service.strategy.StableGeniusStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * Service for managing a game between two nim players.
 * Currently it is assumed to play against a computer
 */
@Slf4j
@Service
public class GameService {

    @Value("${game.minMatches:1}")
    private int MIN_MATCHES;

    @Value("${game.maxMatches:3}")
    private int MAX_MATCHES;

    @Value("${game.initialMatchCount:13}")
    private int INITIAL_MATCH_COUNT;

    @Value("${game.computer.name:Computer}")
    private String COMPUTER_PLAYER_NAME;

    private GameRepository gameRepository;
    private PlayerService playerService;

    private Player computerPlayer;
    private GameStrategy strategy;

    public GameService(@Autowired GameRepository gameRepository,
                       @Autowired PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    @PostConstruct
    private void initializeComputerPlayer() {
        strategy = new StableGeniusStrategy();
        computerPlayer = playerService.getOrCreatePlayerForName(COMPUTER_PLAYER_NAME);
    }

    /**
     * Retrieve a game by it's unique identifier
     * @param gameId The stringified UUID of a game
     * @return A game found by the provided id
     * @exception GameNotFoundException Exception thrown, when no game was found by the provided id
     */
    public Game getGameById(final String gameId) {
        if(StringUtils.isEmpty(gameId)) throw new GameNotFoundException();
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    /**
     * Starts a new game with the provided player
     * @param player The player for which to start a game
     * @return The game instance created
     */
    public Game startGame(final Player player) {
        if(player == null || !player.isValid()) throw new IllegalArgumentException("Player must be valid");

        final Game game = gameRepository.save(new Game(player, computerPlayer, MIN_MATCHES, MAX_MATCHES, INITIAL_MATCH_COUNT));

        log.debug("Started game {}", game);
        return game;
    }

    /**
     * Play a single round by providing game, player and number of matches to take.
     * @param game Game for which to play
     * @param player Player who takes matches
     * @param numberOfMatches Matches count for the player
     * @return The updated game status (after player and computer took matches)
     */
    public Game playRound(final Game game, final Player player, final int numberOfMatches) {
        if(!game.isMyTurn(player)) throw new NotYourTurnException();

        game.takeMatches(numberOfMatches);

        if(game.isMyTurn(computerPlayer)) {
            final int matchesForComputerPlayer = strategy.execute(game.getRemainingMatches());
            game.takeMatches(matchesForComputerPlayer);
        }

        return gameRepository.save(game);
    }

}
