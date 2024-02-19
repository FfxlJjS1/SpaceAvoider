package com.project.spaceavoider.gamesetting;

import com.project.spaceavoider.bodies.bonus.BonusBody;

public final class GameDifficult {
    private byte difficultLevel;
    private final byte pointsToDifficultRatio = 50;
    private final byte maxDifficultLevel = 50;

    public void update() {
        difficultLevel = (byte) Math.min(GameView.points / pointsToDifficultRatio+1,
                                         maxDifficultLevel);

        BonusBody.accelerateAllBonusSpeed();
    }

    public byte getDifficultLevel() {
        return difficultLevel;
    }

    public byte getMaxDifficultLevel() {
        return maxDifficultLevel;
    }

    public byte getPointsToDifficultRatio() {
        return pointsToDifficultRatio;
    }
}
