package com.project.spaceavoider.bodies.bonus;

import android.content.Context;

import com.project.spaceavoider.gamesetting.GameView;
import com.project.spaceavoider.R;

import java.util.Random;

public class FreezeBonus extends BonusBody {
    public FreezeBonus(Context context) {
        Random rand = new Random();

        initSizeAndPosition(rand.nextInt(GameView.maxX) - size/2, 1, 2);
        initSpeed();
        initBitmap(context, R.drawable.snowflake);
    }

    @Override
    public void executeBonus(BonusState bonusState) {
        bonusState.setCurrentFreezeBonusInterval((short) 0);
        bonusState.setFreezeState(true);
    }
}
