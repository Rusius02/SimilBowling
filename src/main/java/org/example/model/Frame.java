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

    public Frame() {
        this.shots = new ArrayList<>();
        this.score = 0;
    }

    public void addShot(int quilles, boolean isLastFrame) {
        int totalQuilles = shots.stream().mapToInt(Integer::intValue).sum();

        if (((shots.size() < 3&& (totalQuilles + quilles <= 15)) || (isLastFrame && isStrike() && shots.size() < 4))) {
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

    public boolean isComplete() {
        return (shots.size() == 3 || isStrike() || isSpare());
    }

    public boolean isStrike() {
        return shots.size() >= 1 && shots.get(0) == 15;
    }

    public boolean isSpare() {
        return shots.size() == 2 && (shots.get(0) + shots.get(1) == 15);
    }

    public int getScore() {
        return score;
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
