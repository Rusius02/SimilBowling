package org.example.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.model.Game;
import org.example.model.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameWindow extends Application {
    private Stage stage;
    private Game game;
    private int currentPlayerIndex = 0;
    private Label scoreLabel;
    private TextField inputField;
    private Label playerLabel;
    private final List<Player> players = new ArrayList<>();
    private GridPane scoreBoard;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setTitle("SimilBowling");

        showHomeScreen();

        primaryStage.show();
    }

    /**
     *  Écran d'accueil avec "Jouer" et "Quitter"
     */
    private void showHomeScreen() {
        Button playButton = new Button("Jouer");
        playButton.setOnAction(e -> showPlayerSetupScreen());

        Button quitButton = new Button("Quitter");
        quitButton.setOnAction(e -> stage.close());

        VBox layout = new VBox(20, playButton, quitButton);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20px;");

        Scene scene = new Scene(layout, 300, 200);
        stage.setScene(scene);
    }

    /**
     *  Écran de saisie des noms des joueurs
     */
    private void showPlayerSetupScreen() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20px;");

        TextField playerInput = new TextField();
        playerInput.setPromptText("Nom du joueur");

        Button addPlayerButton = new Button("Ajouter joueur");
        Button startGameButton = new Button("Démarrer partie");
        startGameButton.setDisable(true);

        Label playersListLabel = new Label("Joueurs :");

        List<String> playerNames = new ArrayList<>();

        addPlayerButton.setOnAction(e -> {
            String name = playerInput.getText().trim();
            if (!name.isEmpty()) {
                playerNames.add(name);
                playersListLabel.setText("Joueurs : " + String.join(", ", playerNames));
                playerInput.clear();
                if (playerNames.size() >= 1) startGameButton.setDisable(false);  // ✅ 1 joueur minimum maintenant
            }
        });

        startGameButton.setOnAction(e -> {
            for (String name : playerNames) {
                players.add(new Player(name));
            }
            game = new Game(players);
            showGameScreen();
        });

        Button backButton = new Button("Retour");
        backButton.setOnAction(e -> showHomeScreen());

        layout.getChildren().addAll(playerInput, addPlayerButton, playersListLabel, startGameButton, backButton);
        stage.setScene(new Scene(layout, 400, 300));
    }

    /**
     *  Interface de jeu (saisie des scores + Score Board)
     */
    private void showGameScreen() {
        scoreLabel = new Label("Score: 0");
        inputField = new TextField();
        inputField.setPromptText("Quilles tombées");

        playerLabel = new Label("Tour de : " + players.get(0).getName());

        Button lancerButton = new Button("Lancer");
        lancerButton.setOnAction(e -> lancerQuilles());

        scoreBoard = new GridPane();
        scoreBoard.setHgap(10);
        scoreBoard.setVgap(10);
        updateScoreBoard();

        VBox layout = new VBox(10, playerLabel, scoreLabel, inputField, lancerButton, scoreBoard);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
    }

    /**
     * Gestion des lancers
     */
    private void lancerQuilles() {
        try {
            int quilles = Integer.parseInt(inputField.getText().trim());
            if (quilles <=15 && quilles > 0){
                scoreLabel.setTextFill(Color.BLACK);
                Player currentPlayer = players.get(currentPlayerIndex);
                game.lancer(quilles);

                scoreLabel.setText("Score: " + currentPlayer.calculateTotalScore());
                inputField.clear();

                updateScoreBoard();

                if (game.isGameOver()) {
                    showEndGameDialog();
                    return;
                }

                currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                playerLabel.setText("Tour de : " + players.get(currentPlayerIndex).getName());
            } else {
                scoreLabel.setTextFill(Color.RED);
                scoreLabel.setText("Veuillez entrer un nombre entre 0 et 15 !");
            }
        } catch (NumberFormatException e) {
            scoreLabel.setText("Entrée invalide !");
        }
    }

    /**
     * Mise à jour du Score Board
     */
    private void updateScoreBoard() {
        scoreBoard.getChildren().clear();

        for (int i = 1; i <= 5; i++) {
            Label frameLabel = new Label("Frame " + (i));
            scoreBoard.add(frameLabel, i, 0);
        }

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Label nameLabel = new Label(player.getName());
            scoreBoard.add(nameLabel, 0, i + 1);

            for (int j = 0; j < player.getFrames().size(); j++) {
                String frameText = player.getFrames().get(j).getShots().toString();
                Label frameLabel = new Label(frameText);
                scoreBoard.add(frameLabel, j + 1, i + 1);
            }
        }
    }

    /**
     *  Fin de partie avec annonce du vainqueur
     */
    private void showEndGameDialog() {
        Player winner = players.stream()
                .max(Comparator.comparingInt(Player::calculateTotalScore))
                .orElse(null);
        String winnerText = (winner != null) ? "Vainqueur : " + winner.getName() + " avec " + winner.calculateTotalScore() + " points !" : "Égalité !";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de Partie");
        alert.setHeaderText("La partie est terminée !");
        alert.setContentText(winnerText);

        ButtonType newGame = new ButtonType("Nouvelle Partie");
        ButtonType exit = new ButtonType("Quitter");

        alert.getButtonTypes().setAll(newGame, exit);
        alert.showAndWait().ifPresent(response -> {
            if (response == newGame) {
                players.clear();
                showPlayerSetupScreen();
            } else {
                stage.close();
            }
        });
    }
}
