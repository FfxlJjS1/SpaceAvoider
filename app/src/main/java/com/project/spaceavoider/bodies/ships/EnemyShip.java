package com.project.spaceavoider.bodies.ships;

import android.content.Context;

import com.project.spaceavoider.R;
import com.project.spaceavoider.bodies.BodiesParams;
import com.project.spaceavoider.gamesetting.GameView;

import java.util.Random;

public class EnemyShip extends Ship {
    protected float verticalSpeed;
    protected boolean toLeft;

    public EnemyShip(Context context) {
        Random rand = new Random();
        BodiesParams.EnemyShip enemyShip = GameView.bodiesParams.getEnemyShip();

        this.enemyShip = true;
        this.toLeft = rand.nextBoolean();

        strengthCount = enemyShip.getStrengthCount();
        maxStrengthCount = enemyShip.getMaxStrengthCount();
        maxMissileTimeOut = enemyShip.getMaxMissileTimeOut();
        missileDamage = enemyShip.getMissileStrength();

        pointsForIt = enemyShip.getPointsForIt();

        verticalSpeed = enemyShip.getVerticalSpeed();

        currentMissileTimeOut = enemyShip.getCurrentMissileTimeOut();

        initSizeAndPosition(rand.nextInt(GameView.maxX-(int) (enemyShip.getSize())),
                enemyShip.getY(), enemyShip.getSize());
        initSpeed();
        initBitmap(context, enemyShip.getBitmapId());
    }

    @Override
    public void update(Context context) {
        enemyShipLogic(context);

        update();
    }

    @Override
    public void update() {
        y += verticalSpeed;

        missileDamage = GameView.bodiesParams.getEnemyShip().getMissileStrength();

        super.update();
    }

    protected void enemyShipLogic(Context context) {
        shotMissile(context);

        if (toLeft) {
            x -= speed;

            if (x <= (-1)*size)
                x = GameView.maxX + size;
        } else {
            x += speed;

            if (x >= GameView.maxX)
                x = (-1)*(size);
        }
    }

    @Override
    protected void initSpeed() {
        BodiesParams.EnemyShip enemyShip = GameView.bodiesParams.getEnemyShip();
        Random rand = new Random();

        speed = enemyShip.getMinSpeed() +
                (enemyShip.getMaxSpeed()- enemyShip.getMinSpeed()) * rand.nextFloat();
        verticalSpeed = enemyShip.getVerticalSpeed();
    }
}
