package com.project.spaceavoider.bodies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.project.spaceavoider.gamesetting.GameDifficult;
import com.project.spaceavoider.gamesetting.GameView;

public class Body {
    protected static float maxAccelerateMaxSpeed = 2.3f, maxAccelerateMinSpeed = 2f;
    protected float x, y, size, speed;
    protected Bitmap bitmap;

    // Определение картинки
    protected final void initBitmap(Context context, int bitmapId) {
        if (bitmapId == 0)
            return;

        Bitmap cBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapId);

        bitmap = Bitmap.createScaledBitmap(
                cBitmap, (int) (size * GameView.unitW), (int) (size * GameView.unitH), false);

        cBitmap.recycle();
    }

    protected final void initSizeAndPosition(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    protected void initSpeed() {
    }

    // Рисование картинка объекта
    public final void drawObject(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x * GameView.unitW, y * GameView.unitH, paint);
    }

    // Определение пересечения с объектом по коордитане x, y и размеру
    public final boolean isCollision(float objectX, float objectY, float objectSize) {
        return !((objectX > (x + size) || x > (objectX + objectSize)) ||
                ((y + size) < objectY) || y > (objectY + objectSize));
    }

    // Метод обновления данных на переопределение
    public void update() {}

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }
}