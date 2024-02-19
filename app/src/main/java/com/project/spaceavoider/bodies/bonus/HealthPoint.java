package com.project.spaceavoider.bodies.bonus;

import android.content.Context;

import com.project.spaceavoider.gamesetting.GameView;
import com.project.spaceavoider.R;

import java.util.Random;

public class HealthPoint extends BonusBody {
    public static float minSpeed, maxSpeed;
    public short healthCount = 1;

    public HealthPoint(Context context) {
        Random random = new Random();

        initSizeAndPosition(random.nextInt(GameView.maxX) - size / 2, 1, 2);
        initSpeed();
        initBitmap(context, R.drawable.health_point);
    }

    @Override
    public void executeBonus(BonusState bonusState) {
        bonusState.setHealthAddCount(healthCount);
        bonusState.setHealthAdd(true);
    }
}
