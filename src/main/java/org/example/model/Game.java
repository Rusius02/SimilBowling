package org.example.model;


import java.util.ArrayList;
import java.util.List;

public class Game {
    private final List<Player> players;
    private int currentPlayerIndex;

    /**
     * Constructeur de Partie (Game).
     *
     * @param playerNames Liste des joueurs pour cette partie, pas de possibilité de rajouter des nouveaux joueurs
     * en cours de partie.
     */
    public Game(List<Player> playerNames) {
        players = new ArrayList<>();
        players.addAll(playerNames);
        currentPlayerIndex = 0;
    }

    /**
     * Lance une boule et met à jour l'état du jeu.
     *
     * @param quilles Nombre de quilles tombées.
     */
    public void lancer(int quilles) {
        Player currentPlayer = players.get(currentPlayerIndex);
        currentPlayer.lancer(quilles);

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        //Le code ci-dessous m'a surtout servi dans un premier temps avant l'intégration d'une interface graphique
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
    /**
     * Vérifie si le jeu est terminé en regardant si tous les joueurs ont fini leurs lancers pour toutes les frames.
     *
     * @return boolean which says if the game is over or no.
     */
    public boolean isGameOver() {
        for (Player player : players) {
            if (!player.isGameOver()) {
                return false;
            }
        }
        return true;
    }

}
