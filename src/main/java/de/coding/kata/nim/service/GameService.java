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

@Slf4j
@Service
public class GameService {

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

    public Game getGameById(final String gameId) {
        if(StringUtils.isEmpty(gameId)) throw new GameNotFoundException();
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new);
    }

    public Game startGame(final Player player) {
        if(player == null || !player.isValid()) throw new IllegalArgumentException("Player must be valid");

        final Game game = gameRepository.save(new Game(player, computerPlayer, INITIAL_MATCH_COUNT));

        log.debug("Started game {}", game);
        return game;
    }

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
