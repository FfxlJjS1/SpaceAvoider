package com.project.spaceavoider.bodies;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.project.spaceavoider.gamesetting.GameView;

public class SpaceBody extends Body { // Объекты взаимодействия в бою
    protected short strengthCount, maxStrengthCount;
    protected short currentStrengthVisible = 0, maxStrengthVisible = 120;
    protected short toStateUpLine = 20;
    protected int pointsForIt;

    public void draw(Canvas canvas, Paint paint, boolean isVisibleStrength) {
        drawObject(canvas, paint);

        drawStrength(canvas, paint, isVisibleStrength);
    }

    protected final void drawStrength(Canvas canvas, Paint paint, boolean isVisibleStrength) {
        if (!isVisibleStrength || currentStrengthVisible >= maxStrengthVisible)
            return;

        paint.setAlpha(Math.min(maxStrengthVisible - currentStrengthVisible, 255));
        paint.setStrokeWidth(6);

        paint.setColor(Color.RED);
        canvas.drawLine(x * GameView.unitW, y * (GameView.unitH) - toStateUpLine,
                (x + size * strengthCount / maxStrengthCount) * GameView.unitW, y * (GameView.unitH) - toStateUpLine,
                paint);

        currentStrengthVisible++;
    }

    public final void addStrengthCount(short countStrengthAdd) {
        currentStrengthVisible = 0;

        strengthCount = (short) Math.min(strengthCount + countStrengthAdd, maxStrengthCount);
    }

    public final boolean minesStrengthCount(short countDamage) {
        if (strengthCount - countDamage < 1)
            return false;

        currentStrengthVisible = 0;
        strengthCount -= countDamage;

        return true;
    }

    public short getStrengthCount() {
        return strengthCount;
    }

    public final void addPointsForItToUser(boolean dividePoints) {
        if (!dividePoints)
            GameView.points+=pointsForIt/2;
        GameView.points+=pointsForIt/2;
    }
}
