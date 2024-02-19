package com.project.spaceavoider.bodies.ships;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.project.spaceavoider.bodies.BodiesParams;
import com.project.spaceavoider.bodies.Body;
import com.project.spaceavoider.activities.MainActivity;
import com.project.spaceavoider.R;
import com.project.spaceavoider.bodies.bonus.BonusBody;
import com.project.spaceavoider.gamesetting.GameView;

public class PlayerShip extends Ship {
    public PlayerShip(Context context) {
        BodiesParams.PlayerShipParams playerShipParams =
                GameView.bodiesParams.getPlayerShipParams();

        toStateUpLine = playerShipParams.getToStateUpLine();
        enemyShip = false;

        strengthCount = GameView.bodiesParams.getPlayerShipParams().getStrengthCount();
        missileDamage = GameView.bodiesParams.getPlayerShipParams().getMissileStrength();

        initSizeAndPosition((float) GameView.maxX / 2,
                (float)GameView.maxY - playerShipParams.getSize() - 1,
                playerShipParams.getSize());
        accelerateLocalChange();
        initBitmap(context, playerShipParams.getBitmapId());
    }

    @Override
    public final void draw(Canvas canvas, Paint paint, boolean isVisibleStrength) {
        drawObject(canvas, paint);
        drawMissiles(canvas, paint);

        drawStrength(canvas, paint, isVisibleStrength);

        drawTimeOut(canvas, paint);
        drawBonusOut(canvas, paint);
    }

    // Рисование полосы отката
    public final void drawBonusOut(Canvas canvas, Paint paint) {
        if (!GameView.bonusState.isFreezeState() && !GameView.bonusState.isShadowState())
            return;

        paint.setStrokeWidth(6);

        paint.setColor(Color.GREEN);
        canvas.drawLine(x * GameView.unitW,
                y * (GameView.unitH) - toStateUpLine + 16,
                (x + size * GameView.bonusState.ratioMaxCurrent()) * GameView.unitW,
                y * (GameView.unitH) - toStateUpLine + 16,
                paint);
    }

    // Обработка действия пользователя и обновление данных снарядов и отката
    @Override
    public final void update(Context context) {
        accelerateLocalChange();

        if (MainActivity.isLeftPressed)
            x -= speed;

        if (MainActivity.isRightPressed)
            x += speed;

        if (MainActivity.isCenterPressed)
            shotMissile(context);

        if (x <= (-1)*size)
            x = GameView.maxX;
        else if (x >= GameView.maxX)
            x = (-1)*(size);

        super.update();
    }

    @Override
    protected void initSpeed() {
        speed = GameView.bodiesParams.getPlayerShipParams().getSpeed();
    }

    public void accelerateLocalChange() {
        BodiesParams.PlayerShipParams playerShipParams =
                GameView.bodiesParams.getPlayerShipParams();

        speed = playerShipParams.getSpeed();
        missileDamage = GameView.bodiesParams.getPlayerShipParams().getMissileStrength();
        maxStrengthCount = playerShipParams.getMaxStrengthCount();
        maxMissileTimeOut = playerShipParams.getMaxMissileTimeOut();

        initSpeed();
    }
}
