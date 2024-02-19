package com.project.spaceavoider.bodies;

import android.content.Context;

import com.project.spaceavoider.gamesetting.GameView;

import java.util.Random;

public class BigAsteroid extends Asteroid {
    public BigAsteroid(Context context) {
        pointsForIt = GameView.gameDifficult.getPointsToDifficultRatio() + 20;
        strengthCount = GameView.bodiesParams.getBigAsteroid().getStrengthCount();
        maxStrengthCount = GameView.bodiesParams.getBigAsteroid().getMaxStrengthCount();

        initSizeAndPosition(0, (-1)*GameView.bodiesParams.getBigAsteroid().getSize(), GameView.maxX);
        initSpeed();
        initBitmap(context, GameView.bodiesParams.getBigAsteroid().getBitmapId());

        toStateUpLine = (short) ((size+1)*-1*GameView.unitH);
    }

    public void initSpeed() {
        speed = GameView.bodiesParams.getBigAsteroid().getSpeed();
    }
}
