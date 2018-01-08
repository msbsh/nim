package de.coding.kata.nim.service;

import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.repository.GameRepository;
import de.coding.kata.nim.service.strategy.GameStrategy;
import de.coding.kata.nim.service.strategy.RandomStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@Service
public class GameService {

    private static final String COMPUTER_PLAYER_NAME = "COM";

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
        strategy = new RandomStrategy();
        computerPlayer = playerService.getOrCreatePlayerForName(COMPUTER_PLAYER_NAME);
    }

    public Game getGameById(final String gameId) {
        return gameRepository.findById(UUID.fromString(gameId))
                .orElseThrow(() -> new IllegalArgumentException("This is not the game, you are looking for"));
    }

    public Game startGame(final Player player) {
        Game g = new Game(player, computerPlayer);
        log.debug("Created game! {}", g);

        final Game game = gameRepository.save(g);
        log.debug("Started game {}", game);
        return game;
    }

    public Game takeMatches(final Game game, final Player player, final int numberOfMatches) {
        if(game.isMyTurn(player)) {
            game.takeMatches(player, numberOfMatches);
            log.debug("{} took {} matches in game {}. {} remaining", player, numberOfMatches, game.getGameId(), game.getRemainingMatches());

            final int matchesForComputerPlayer = strategy.execute(game.getRemainingMatches());
            game.takeMatches(computerPlayer, matchesForComputerPlayer);
            log.debug("{} took {} matches in game {}. {} remaining", computerPlayer, matchesForComputerPlayer, game.getGameId(), game.getRemainingMatches());
        } else {
            throw new IllegalStateException("This just isn't you time!");
        }

        return gameRepository.save(game);
    }

}
