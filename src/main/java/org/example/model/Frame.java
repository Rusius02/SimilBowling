package org.example.model;

import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter@Setter
public class Frame {
    private List<Integer> shots;
    private int score;

    /**
     * Constructeur de Frame : initialisation du score à zero et instanciation de la liste des shots
     *
     */
    public Frame() {
        this.shots = new ArrayList<>();
        this.score = 0;
    }

    /**
     * Ajout d'un lancer (shot) à la frame, on vérifie que l'on ne dépasse pas le nombre de quilles présentes puis
     * on ajoute le lancer et on recalcule le score.
     * Si on dépasse le nombre de quilles alors on affiche une dialog box pour nous avertir
     *
     * @param quilles Nombre de quilles tombées.
     * @param isLastFrame Boolean qui vérifie si c'est la dernière frame
     */
    public void addShot(int quilles, boolean isLastFrame) {
        int totalQuilles = shots.stream().mapToInt(Integer::intValue).sum();

        if (((shots.size() < 3 && (totalQuilles + quilles <= 15)) ||
                (isLastFrame && isStrike() && shots.size() < 4))) {
            shots.add(quilles);
            calculateScore();
        } else {
            afficherErreur("Impossible d'ajouter ce lancer, total des quilles dépassé !");
        }
    }
    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText("Lancer invalide !");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void calculateScore() {
        score = 0;
        for (int shot : shots) {
            score += shot;
        }
    }

    /**
     * Vérifie si la frame est finie, 3 cas de figures :
     * 1. On a effectué 3 lancers
     * 2. on a fait un spare
     * 3. on a fait un strike
     */
    public boolean isComplete() {
        return (shots.size() == 3 || isStrike() || isSpare());
    }
    /**
     * Vérifie si on a fait un strike
     */
    public boolean isStrike() {
        return shots.size() >= 1 && shots.get(0) == 15;
    }
    /**
     * Vérifie si on a effectué un spare
     */
    public boolean isSpare() {
        if (shots.size() < 2) {
            return false;
        }

        int sum = shots.stream().limit(3).mapToInt(Integer::intValue).sum();

        return sum == 15;
    }

    public int getShotScore(int index) {
        if (index >= 0 && index < shots.size()) {
            return shots.get(index);
        }
        return 0;
    }

    @Override
    public String toString() {
        return shots.toString();
    }
}
