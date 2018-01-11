package de.coding.kata.nim.rest;

import de.coding.kata.nim.Application;
import de.coding.kata.nim.entity.Game;
import de.coding.kata.nim.entity.Player;
import de.coding.kata.nim.repository.GameRepository;
import de.coding.kata.nim.service.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GameEndpointTest {

    private static final String PLAYER_NAME = "testPlayer";

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameService gameService;

    private MockMvc mockMvc;

    private Game game;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        gameRepository.deleteAllInBatch();

        final Player player = new Player(PLAYER_NAME);
        this.game = gameService.startGame(player);
    }

    @Test
    public void testGameStarted() throws Exception {
        mockMvc.perform(post("/api/players/" + PLAYER_NAME + "/games/start")
            .contentType(contentType))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.gameId", notNullValue()))
            .andExpect(jsonPath("$.player1.name", is(PLAYER_NAME)))
            .andExpect(jsonPath("$.player2.name", notNullValue()))
            .andExpect(jsonPath("$.currentPlayer.name", is(PLAYER_NAME)))
            .andExpect(jsonPath("$.winner", nullValue()));
    }

    @Test
    public void testPlayRound() throws Exception {
        mockMvc.perform(post("/api/players/" + PLAYER_NAME + "/games/"+ game.getGameId() +"/take/3")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.gameId", notNullValue()))
                .andExpect(jsonPath("$.player1.name", is(PLAYER_NAME)))
                .andExpect(jsonPath("$.player2.name", notNullValue()))
                .andExpect(jsonPath("$.currentPlayer.name", is(PLAYER_NAME)))
                .andExpect(jsonPath("$.winner", nullValue()));
    }

    @Test
    public void testProvokeError() throws Exception {
        game.setRemainingMatches(2);
        gameRepository.save(game);

        mockMvc.perform(post("/api/players/" + PLAYER_NAME + "/games/"+ game.getGameId() +"/take/3")
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPlayUnknownGame() throws Exception {
        mockMvc.perform(post("/api/players/" + PLAYER_NAME + "/games/MY_UNKNOWN_GAME_ID/take/3")
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGameWithUnknownUser() throws Exception {
        mockMvc.perform(post("/api/players/MY_UNKNOWN_PLAYER/games/"+ game.getGameId() +"/take/3")
                .contentType(contentType))
                .andExpect(status().isBadRequest());
    }
}
