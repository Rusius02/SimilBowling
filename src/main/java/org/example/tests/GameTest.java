package org.example.tests;

import org.example.model.Game;
import org.example.model.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {
    @Test
    void testFinDePartie() {
        List<Player> players = new ArrayList<>();
        Player player = new Player("Adri");
        players.add(player);
        Game game = new Game(players);


        for (int i = 0; i < 5; i++) {
            player.lancer(15); // 5 strikes pour finir
        }
        player.lancer(15);
        player.lancer(15);
        player.lancer(15);

        assertTrue(game.isGameOver(), "La partie doit être terminée !");
    }
}
