package org.example.model;


import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Player> players;
    private int currentPlayerIndex;

    public Game(List<Player> playerNames) {
        players = new ArrayList<>();
        for (Player player : playerNames) {
            players.add(player);
        }
        currentPlayerIndex = 0;
    }

    public void lancer(int quilles) {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.lancer(quilles);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();

        printScoreBoard();
        if (isGameOver()){
            System.out.println("Fin de la partie.");
        }
    }

    public void printScoreBoard() {
        System.out.println("\n===== SCORE BOARD =====");
        for (Player player : players) {
            player.printPlayerScore();
        }
        System.out.println("=======================\n");
    }
    public boolean isGameOver() {
        for (Player player : players) {
            if (!player.isGameOver()) {
                return false;
            }
        }
        return true;
    }

}
