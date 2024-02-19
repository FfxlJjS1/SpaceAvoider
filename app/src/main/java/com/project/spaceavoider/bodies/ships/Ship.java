package com.project.spaceavoider.bodies.ships;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.project.spaceavoider.bodies.Missile;
import com.project.spaceavoider.bodies.SpaceBody;
import com.project.spaceavoider.gamesetting.GameView;

import java.util.ArrayList;

public class Ship extends SpaceBody {
    protected ArrayList<Missile> missiles = new ArrayList<>(); // Ракеты корабля
    protected short maxMissileTimeOut, currentMissileTimeOut; // Откат выстрела
    protected short missileDamage;
    protected boolean enemyShip;

    @Override
    public void draw(Canvas canvas, Paint paint, boolean isVisibleStrength) {
        drawObject(canvas, paint);
        drawMissiles(canvas, paint);

        drawStrength(canvas, paint, isVisibleStrength);
        drawTimeOut(canvas, paint);
    }

    protected final void drawMissiles(Canvas canvas, Paint paint) { // Рисовка ракет корабля
        for (Missile missile : missiles)
            missile.draw(canvas, paint, false);
    }

    protected final void drawTimeOut(Canvas canvas, Paint paint) { // Рисовка отката выстрела
        if (currentMissileTimeOut >= maxMissileTimeOut)
            return;

        paint.setStrokeWidth(6);

        paint.setColor(Color.BLUE);
        canvas.drawLine(x * GameView.unitW, y * (GameView.unitH) - toStateUpLine + 8,
                (x + size * currentMissileTimeOut / maxMissileTimeOut) * GameView.unitW,
                y * (GameView.unitH) - toStateUpLine + 8,
                paint);

        currentMissileTimeOut++;
    }

    protected final void shotMissile(Context context) {
        if (currentMissileTimeOut < maxMissileTimeOut)
            return;

        Missile missile = new Missile(context, this);

        missiles.add(missile);

        currentMissileTimeOut = 0;
    }

    @Override
    public void update() {
        for (Missile missile : missiles)
            missile.update();

        if (currentMissileTimeOut < maxMissileTimeOut)
            currentMissileTimeOut++;
    }

    // Обновление движения корабля и его выстрелов
    protected void update(Context context) {
    }

    public ArrayList<Missile> getMissiles() {
        return missiles;
    }

    public void setMissiles(ArrayList<Missile> missiles) {
        this.missiles = missiles;
    }

    public boolean isEnemyShip() {
        return enemyShip;
    }

    public short getMissileDamage() {
        return missileDamage;
    }
}