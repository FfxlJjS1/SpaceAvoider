package com.project.spaceavoider.bodies.bonus;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.project.spaceavoider.bodies.Body;
import com.project.spaceavoider.gamesetting.GameView;

import java.util.Random;

public class BonusBody extends Body {
    protected static float minSpeed = 0.1f, maxSpeed= 0.4f,
            accelerateMaxSpeed = 0.00025f, accelerateMinSpeed = 0.00015f;

    // Конечный метод рисования падающих бонусов
    public final void draw(Canvas canvas, Paint paint) {
        drawObject(canvas, paint);
    }

    @Override
    public final void update() {
        y += speed;
    }

    @Override
    protected final void initSpeed() {
        Random rand = new Random();

        speed = minSpeed + (maxSpeed - minSpeed) * rand.nextFloat();
    }

    // Объекты усиления или усложнения игры
    public void executeBonus(BonusState bonusState) {
    }

    // Изменение параметов скорости всех бонусов
    public static void accelerateAllBonusSpeed() {
        Random rand = new Random();

        maxSpeed += accelerateMaxSpeed;
        minSpeed += accelerateMinSpeed;
    }

    public static void DropStatic() {
        maxSpeed = 0.4f;
        minSpeed = 0.1f;
        accelerateMaxSpeed = 0.00025f;
        accelerateMinSpeed = 0.00015f;
    }
}