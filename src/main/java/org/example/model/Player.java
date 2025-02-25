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

    /**
     * Constructeur de Joueur (Player)
     *
     * @param name Nom d'un joueur (peut-être un pseudo)
     */
    public Player(String name) {
        this.name = name;
        this.frames = new ArrayList<>();
        frames.add(new Frame());
    }

    /**
     * On récupère la frame dans laquelle on est puis on ajoute notre lancer
     * si la frame est terminée lorsque l'on a ajouté notre lancer alors on passe à la frame suivante
     * A la fin on recalcule le score pour être à jour
     *
     * @param quilles Nombre de quilles tombées.
     */
    public void lancer(int quilles) {
        Frame currentFrame = frames.get(frames.size() - 1);

        boolean isLastFrame = (frames.size() == 5);
        currentFrame.addShot(quilles, isLastFrame);

        if (currentFrame.isComplete() && frames.size() < 5) {
            frames.add(new Frame());
        }

        calculateTotalScore();
    }
    /**
     * Calcule le score total que le joueur a fait, le calcul des shots bonus lors de la réalisation de strike
     * est le point compliqué du programme, il faut gérer le fait qu'à la fin il n'y a plus de frame mais on a
     * quand même besoin de bonus
     *
     */
    public int calculateTotalScore() {
        totalScore = 0;
        for (int i = 0; i < frames.size(); i++) {
            Frame f = frames.get(i);
            totalScore += f.getScore();

            if (f.isStrike()) {
                int bonusShots = 3;
                int j = i + 1;
                while (bonusShots > 0 && j < frames.size()) {
                    Frame nextFrame = frames.get(j);
                    for (int shot = 0; shot < 2 && bonusShots > 0; shot++) {
                        totalScore += nextFrame.getShotScore(shot);
                        bonusShots--;
                    }
                    j++;
                }

                if (i == frames.size() - 1) {
                    if (bonusShots > 0) {
                        totalScore += bonusShots * 15;
                    }
                } else if (i == frames.size() - 2) {
                    if (bonusShots > 0) {
                        totalScore += bonusShots * 15;
                    }
                }
            } else if (f.isSpare() && i + 1 < frames.size()) {
                Frame nextFrame = frames.get(i + 1);
                totalScore += nextFrame.getShotScore(0);
            }
        }
        return totalScore;
    }

    /**
     * Vérifie si le joueur a terminé tous ses lancers
     * Si on n'est pas à la dernière frame alors on a pas terminé
     * Si on est à la dernière frame : -Si pas strike/spare alors on vérifie que tous les lancers sont faits
     *                                 -Si strike/spare on a droit à un lancer bonus (4 au lieu de 3)
     *
     */
    public boolean isGameOver() {
        if (frames.size() < 5) {
            return false;
        }

        Frame lastFrame = frames.get(4);

        if (!lastFrame.isStrike() && !lastFrame.isSpare()) {
            return lastFrame.isComplete();
        }

        if (lastFrame.isStrike() || lastFrame.isSpare()) {
            return lastFrame.getShots().size() == 4;
        }

        return false;
    }

    public void printPlayerScore() {
        System.out.println(name + " - Score total : " + totalScore);
        for (int i = 0; i < frames.size(); i++) {
            System.out.printf("Frame %d: %s | Score: %d\n", i + 1, frames.get(i), frames.get(i).getScore());
        }
    }

}
