package org.example.model;

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
        if (shots.size() < 3 || (isLastFrame && isStrike() && shots.size() < 4)) {
            shots.add(quilles);
        }
        calculateScore();
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
        // Un strike est un lancer avec 15 quilles tombées, et cela doit être le premier lancer de la frame
        return shots.size() >= 1 && shots.get(0) == 15;
    }

    public boolean isSpare() {
        // Un spare est lorsque 15 quilles sont tombées en 2 lancers
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
