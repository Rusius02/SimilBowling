package org.example.tests;

import org.example.model.Game;
import org.example.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {
    private static final int NUMBER_OF_QUILLES = 15;
    private static final int NUMBER_OF_FRAME = 15;
    @Test
    void testFinDePartie() {
        List<Player> players = new ArrayList<>();
        Player player = new Player("Adri");
        players.add(player);
        Game game = new Game(players);


        for (int i = 0; i < NUMBER_OF_FRAME; i++) {
            player.lancer(NUMBER_OF_QUILLES);
        }
        player.lancer(NUMBER_OF_QUILLES);
        player.lancer(NUMBER_OF_QUILLES);
        player.lancer(NUMBER_OF_QUILLES);

        assertTrue(game.isGameOver(), "La partie doit être terminée !");
    }
}
