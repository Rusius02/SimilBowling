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
                int remainingShots = 3;
                int j = i + 1;
                while (remainingShots > 0 && j < frames.size()) {
                    Frame nextFrame = frames.get(j);
                    for (int shot = 0; shot < 2 && remainingShots > 0; shot++) {
                        totalScore += nextFrame.getShotScore(shot);
                        remainingShots--;
                    }
                    j++;
                }

                if (i == frames.size() - 1) {
                    if (remainingShots > 0) {
                        totalScore += remainingShots * 15;
                    }
                } else if (i == frames.size() - 2) {
                    if (remainingShots > 0) {
                        totalScore += remainingShots * 15;
                    }
                }
            } else if (f.isSpare() && i + 1 < frames.size()) {
                Frame nextFrame = frames.get(i + 1);
                totalScore += nextFrame.getShotScore(0);
            }
        }
        return totalScore;
    }


    public boolean isGameOver() {
        if (frames.size() < 5) {
            return false;
        }

        Frame lastFrame = frames.get(4);

        if (!lastFrame.isStrike() && !lastFrame.isSpare()) {
            return lastFrame.isComplete();
        }

        if (lastFrame.isStrike()) {
            return lastFrame.getShots().size() == 4;
        }

        if (lastFrame.isSpare()) {
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
