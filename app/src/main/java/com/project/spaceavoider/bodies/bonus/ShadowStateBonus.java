package com.project.spaceavoider.bodies.bonus;

import android.content.Context;

import com.project.spaceavoider.R;
import com.project.spaceavoider.gamesetting.GameView;

import java.util.Random;

public class ShadowStateBonus extends BonusBody{
    public ShadowStateBonus(Context context) {
        Random rand = new Random();

        initSizeAndPosition(rand.nextInt(GameView.maxX) - size/2, 1, 2);
        initSpeed();
        initBitmap(context, R.drawable.shadow_health);
    }

    @Override
    public void executeBonus(BonusState bonusState) {
        bonusState.setCurrentShadowStateInterval((short) 0);
        bonusState.setShadowState(true);
    }
}
