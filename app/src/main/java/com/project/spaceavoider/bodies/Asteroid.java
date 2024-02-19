package com.project.spaceavoider.bodies;

import android.content.Context;

import com.project.spaceavoider.R;
import com.project.spaceavoider.gamesetting.GameView;

import java.util.Random;

public class Asteroid extends SpaceBody {
    public Asteroid() {
    }

    public Asteroid(Context context) {
        Random random = new Random();
        BodiesParams.AsteroidParams asteroidParams =
                GameView.bodiesParams.getAsteroidParams();

        strengthCount = asteroidParams.getStrengthCount();
        maxStrengthCount = asteroidParams.getMaxStrengthCount();

        pointsForIt = asteroidParams.getPointsForIt();

        initSizeAndPosition(random.nextInt(GameView.maxX - (int) asteroidParams.getSize()),
                1, asteroidParams.getSize());
        initSpeed();
        initBitmap(context, asteroidParams.getBitmapId());
    }

    @Override
    public final void update() {
        y += speed;
    }

    @Override
    protected void initSpeed() {
        Random rand = new Random();
        BodiesParams.AsteroidParams asteroidParams = GameView.bodiesParams.getAsteroidParams();

        speed = asteroidParams.getMinSpeed() +
                (asteroidParams.getMaxSpeed() - asteroidParams.getMinSpeed()) * rand.nextFloat();
    }
}