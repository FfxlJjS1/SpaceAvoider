package com.project.spaceavoider.bodies;

import android.content.Context;

import com.project.spaceavoider.R;
import com.project.spaceavoider.bodies.ships.Ship;

public class Missile extends SpaceBody {
    protected boolean enemyMissile;

    public Missile(Context context, Ship ship) {
        strengthCount = ship.getMissileDamage();
        maxStrengthCount = ship.getMissileDamage();

        enemyMissile = ship.isEnemyShip();

        if (enemyMissile) {
            initSizeAndPosition(ship.x + ship.size / 2 - 1,
                    ship.y - 1,
                    2);
            initSpeed();
            initBitmap(context, R.drawable.asteroid);
        } else {
            initSizeAndPosition(ship.x + ship.size / 2 - 1,
                    ship.y + size + 1,
                    2);
            initSpeed();
            initBitmap(context, R.drawable.missile);
        }
    }

    @Override
    public void update() {
        if (!enemyMissile)
            y -= speed;
        else
            y += speed;
    }

    @Override
    protected void initSpeed() {
        speed = 1f;
    }
}