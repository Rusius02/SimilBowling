package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter@Setter
public class Player {
    private String name;
    private List<Frame> frames;
    private int totalScore;

    public Player(String name) {
        this.name = name;
        this.frames = new ArrayList<>();
        frames.add(new Frame());
    }

    public void lancer(int quilles) {
        Frame currentFrame = frames.get(frames.size() - 1);

        boolean isLastFrame = (frames.size() == 5);
        currentFrame.addShot(quilles, isLastFrame);

        if (currentFrame.isComplete() && frames.size() < 5) {
            frames.add(new Frame());
        }

        calculateTotalScore();
    }

    public int calculateTotalScore() {
        totalScore = 0;
        for (int i = 0; i < frames.size(); i++) {
            Frame f = frames.get(i);
            totalScore += f.getScore();

            if (f.isStrike()) {
                // Si c'est un strike, ajouter les 3 lancers suivants comme bonus
                int remainingShots = 3; // Ajouter 3 lancers après un strike
                int j = i + 1; // Frame suivant
                while (remainingShots > 0 && j < frames.size()) {
                    Frame nextFrame = frames.get(j);
                    // Ajouter les lancers du frame suivant, jusqu'à épuiser les 3 lancers restants
                    for (int shot = 0; shot < 2 && remainingShots > 0; shot++) {
                        totalScore += nextFrame.getShotScore(shot); // Ajouter le score du lancer
                        remainingShots--;
                    }
                    j++;
                }

                // Si on arrive à la dernière ou avant-dernière frame et qu'il n'y a pas assez de lancers
                // Ajouter des "lancers fictifs" de 15 pour combler les bonus manquants
                if (i == frames.size() - 1) { // Dernière frame
                    if (remainingShots > 0) {
                        totalScore += remainingShots * 15; // Compléter avec des lancers fictifs de 15
                    }
                } else if (i == frames.size() - 2) { // Avant-dernière frame
                    if (remainingShots > 0) {
                        totalScore += remainingShots * 15; // Compléter avec des lancers fictifs de 15
                    }
                }
            } else if (f.isSpare() && i + 1 < frames.size()) {
                // Si c'est un spare, ajouter le 1er lancer du frame suivant comme bonus
                Frame nextFrame = frames.get(i + 1);
                totalScore += nextFrame.getShotScore(0);
            }
        }
        return totalScore;
    }


    public boolean isGameOver() {
        if (frames.size() < 5) {
            return false; // Si on a moins de 5 frames, la partie n'est pas terminée
        }

        Frame lastFrame = frames.get(4);

        // Si la dernière frame est complète sans bonus, la partie est terminée
        if (!lastFrame.isStrike() && !lastFrame.isSpare()) {
            return lastFrame.isComplete();
        }

        // Si c'est un STRIKE, il faut 3 lancers au total pour compléter la frame
        if (lastFrame.isStrike()) {
            return lastFrame.getShots().size() == 4;
        }

        // Si c'est un SPARE, il faut 3 lancers au total aussi
        if (lastFrame.isSpare()) {
            return lastFrame.getShots().size() == 4;
        }

        return false;
    }

    public Frame getCurrentFrame() {
        return frames.get(frames.size() - 1);
    }

    public void printPlayerScore() {
        System.out.println(name + " - Score total : " + totalScore);
        for (int i = 0; i < frames.size(); i++) {
            System.out.printf("Frame %d: %s | Score: %d\n", i + 1, frames.get(i), frames.get(i).getScore());
        }
    }

}
