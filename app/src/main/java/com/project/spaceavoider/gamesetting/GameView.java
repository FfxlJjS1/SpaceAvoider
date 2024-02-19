package com.project.spaceavoider.gamesetting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.project.spaceavoider.activities.GameDialogActivity;
import com.project.spaceavoider.activities.MainActivity;
import com.project.spaceavoider.bodies.Asteroid;
import com.project.spaceavoider.bodies.BigAsteroid;
import com.project.spaceavoider.bodies.BodiesParams;
import com.project.spaceavoider.bodies.Missile;
import com.project.spaceavoider.bodies.bonus.ShadowStateBonus;
import com.project.spaceavoider.bodies.ships.EnemyShip;
import com.project.spaceavoider.bodies.ships.PlayerShip;
import com.project.spaceavoider.R;
import com.project.spaceavoider.bodies.bonus.BonusBody;
import com.project.spaceavoider.bodies.bonus.BonusState;
import com.project.spaceavoider.bodies.bonus.FreezeBonus;
import com.project.spaceavoider.bodies.bonus.HealthPoint;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    public static int theHighlyPoints = 0, points = 0;

    // Объект-холст для рисования
    private final SurfaceHolder surfaceHolder;

    // Размеры
    public static int maxX = 45, maxY = 55;
    public static float unitW = 0, unitH = 0;
    private int surfaceHolderWidth, surfaceHolderHeight;

    // Параметры работы игры
    public Thread gameThread;
    public Handler setTextViewPoints;
    public static GameState gameState;
    private boolean firstTime;

    // Космические объекты
    private PlayerShip playerShip;
    private ArrayList<Asteroid> asteroids;
    private BigAsteroid bigAsteroid;
    private ArrayList<EnemyShip> enemyShips;
    private HealthPoint healthPoint;
    private FreezeBonus freezeBonus;
    private ShadowStateBonus shadowStateBonus;

    // Параметры космических объектов в зависимости от сложности
    public static GameDifficult gameDifficult;
    public static BodiesParams bodiesParams;

    // Параметры бонусов
    public static BonusState bonusState;
    private int currentTimeBonusInterval, BONUS_POINTS_INTERVAL = 500; // Параметры появления новых бонусов

    public GameView(Context context) {
        super(context);

        surfaceHolder = getHolder();
        setTextViewPoints = new Handler();
        gameState = GameState.gameRunning;

        gameThread = new Thread(this);
        gameThread.start();
    }

    // Метод запуска потока
    @Override
    public void run() {
        while (gameState != GameState.gameOver) {
            if (gameState != GameState.gameResume)
                setStartingParameters();

            gameState = GameState.gameRunning;

            while (gameState == GameState.gameRunning) {
                updateState();
                drawObjects();
                controlPlayer(10);
                checkIfNewSpaceObjects();
                checkCollision();
                controlPlayer(10);
            }

            openGameDialogActivity();
        }
    }

    // Открытие GameDialog
    public void openGameDialogActivity() {
        Intent intent = new Intent(getContext(), GameDialogActivity.class);
        intent.putExtra("gameState", gameState);

        gameState = GameState.gameChoosing;

        getContext().startActivity(intent);

        while (gameState == GameState.gameChoosing)
            controlPlayer(1000);
    }

    // Установка начальных значений
    private void setStartingParameters() {
        points = 0;

        firstTime = true;
        gameDifficult = new GameDifficult();
        bodiesParams = new BodiesParams();

        asteroids = new ArrayList<>();
        bigAsteroid = null;
        enemyShips = new ArrayList<>();

        BonusBody.DropStatic();

        healthPoint = null;
        freezeBonus = null;
        shadowStateBonus = null;

        bonusState = new BonusState();
        BONUS_POINTS_INTERVAL = 100;
        currentTimeBonusInterval = 0;
    }

    // Обновление данных и их связей
    private void updateState() {
        if (firstTime)
            return;

        gameDifficult.update();

        // Обновление бонусов
        bonusState.updateCurrentTime();

        if (bonusState.isHealthAdd()) {
            playerShip.addStrengthCount(bonusState.getHealthAddCount());
            bonusState.setHealthAdd(false);
        }

        // Обновление данных бонуса HP
        if (healthPoint != null)
            if (healthPoint.getY() < maxY - 1)
                healthPoint.update();
            else
                healthPoint = null;

        // Обновлениед данных бонуса Freeze
        if (freezeBonus != null)
            if (freezeBonus.getY() < maxY - 1)
                freezeBonus.update();
            else
                freezeBonus = null;

        if (shadowStateBonus != null)
            if (shadowStateBonus.getY() < maxY - 1)
                shadowStateBonus.update();
            else
                shadowStateBonus = null;

        // Обновление космических объектов
        bodiesParams.updateParams();

        if (!bonusState.isFreezeState()) { // Проверка состояния заморозки
            for (int asteroidId = 0; asteroidId < asteroids.size(); asteroidId++) // Движение астеройдов
                if (asteroids.get(asteroidId).getY() < maxY - 1) {
                    asteroids.get(asteroidId).update();
                } else {
                    asteroids.get(asteroidId).addPointsForItToUser(true);
                    asteroids.remove(asteroidId--);
                }

            if (bigAsteroid != null)
                bigAsteroid.update();

            for (int enemyShipId = 0; enemyShipId < enemyShips.size(); enemyShipId++) // Движение вражеских кораблей и его прилежащих объектов
                if (enemyShips.get(enemyShipId).getY() <= maxY - 3) {
                    EnemyShip enemyShip = enemyShips.get(enemyShipId);

                    enemyShip.update(getContext());

                    for (int missileId = 0; missileId < enemyShip.getMissiles().size(); missileId++)
                        if (enemyShip.getMissiles().get(missileId).getY() >= maxY - 1) {
                            enemyShip.getMissiles().get(missileId).addPointsForItToUser(true);
                            enemyShip.getMissiles().remove(missileId--);
                        }
                } else {
                    enemyShips.get(enemyShipId).addPointsForItToUser(false);
                    enemyShips.remove(enemyShipId--);
                }
        }

        playerShip.update(getContext()); // Обновление данных корабля игрока и его прилежащих объектов
        for (int missileId = 0; missileId < playerShip.getMissiles().size(); missileId++)
            if (playerShip.getMissiles().get(missileId).getY() <= 1)
                playerShip.getMissiles().remove(missileId--);

        setTextViewPoints.post(new Runnable() {
            @Override
            public void run() {
                MainActivity.PointsTextView.setText("Очки: " + points);
                MainActivity.LevelTextView.setText("Уровень: " + gameDifficult.getDifficultLevel());
            }
        });
    }

    // Рисование фона и объектов
    private void drawObjects() {
        // Ожидание доступности области рисования
        while (!surfaceHolder.getSurface().isValid() || surfaceHolder.getSurfaceFrame().width() == 0)
            controlPlayer(20);

        if (firstTime) { // Получение размеров для масштабирования рисунков при первом запуске
            firstTime = false;

            surfaceHolderWidth = surfaceHolder.getSurfaceFrame().width();
            surfaceHolderHeight = surfaceHolder.getSurfaceFrame().height();
            unitW = surfaceHolderWidth / maxX;
            unitH = surfaceHolderHeight / maxY;

            playerShip = new PlayerShip(getContext());
        }

        // Рисование фона и объектов
        Canvas canvas = surfaceHolder.lockCanvas();
        Paint paint = new Paint();

        canvas.drawColor(Color.BLACK); // заполняем фон чёрным

        // Рисуем фоновое изображение
        if (bonusState.isShadowState())
            paint.setAlpha(100);

        Bitmap cBitmap = BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.fon);
        Bitmap bitmap = Bitmap.createScaledBitmap(
                cBitmap, surfaceHolderWidth,
                surfaceHolderHeight, false);

        canvas.drawBitmap(bitmap, 0, 0, paint);

        // Прорисовка космических объектов
        playerShip.draw(canvas, paint, true);

        paint.setAlpha(255);
        for (Asteroid asteroid : asteroids)
            asteroid.draw(canvas, paint, false);

        if (bigAsteroid != null)
            bigAsteroid.draw(canvas, paint, true);

        for (EnemyShip enemyShip : enemyShips)
            enemyShip.draw(canvas, paint, true);

        if (healthPoint != null)
            healthPoint.draw(canvas, paint);

        if (freezeBonus != null)
            freezeBonus.draw(canvas, paint);

        if (shadowStateBonus != null)
            shadowStateBonus.draw(canvas, paint);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    // Действие пользователя
    private void controlPlayer(int millis) {
        try {
            gameThread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Взаимодействие космических объектов
    private void checkCollision() {
        // Воздействие корабля на мир
        for (int missileId = 0; missileId < playerShip.getMissiles().size(); missileId++)  // Взаимодействие снарядов корабля с миром
        {
            boolean missileRemoved = false;
            Missile localMissile = playerShip.getMissiles().get(missileId);

            // Взаимодействие missile с Asteroid
            for (int asteroidId = 0; asteroidId < asteroids.size(); asteroidId++)
                if (asteroids.get(asteroidId).isCollision(localMissile.getX(), localMissile.getY(), localMissile.getSize())) {
                    short localAsteroidStrength = asteroids.get(asteroidId).getStrengthCount();

                    if (!asteroids.get(asteroidId).minesStrengthCount(localMissile.getStrengthCount())) {
                        asteroids.get(asteroidId).addPointsForItToUser(false);

                        asteroids.remove(asteroidId--);
                    }
                    if (!localMissile.minesStrengthCount(localAsteroidStrength)) {
                        playerShip.getMissiles().remove(missileId--);

                        missileRemoved = true;

                        break;
                    }
                }

            if (missileRemoved)
                continue;

            if (bigAsteroid != null && bigAsteroid.isCollision(localMissile.getX(), localMissile.getY(), localMissile.getSize())) {
                short localBigAsteroid = bigAsteroid.getStrengthCount();

                if (!bigAsteroid.minesStrengthCount(localMissile.getStrengthCount())) {
                    bigAsteroid.addPointsForItToUser(false);

                    bigAsteroid = null;
                }
                if (!localMissile.minesStrengthCount(localBigAsteroid)) {
                    playerShip.getMissiles().remove(missileId--);

                    missileRemoved = true;
                }
            }

            if (missileRemoved)
                continue;

            // Взаимодействие снарядов корабля с вражескими кораблями и его прилежащими объектами
            for (int enemyShipId = 0; enemyShipId < enemyShips.size(); enemyShipId++) {
                boolean enemyShipRemoved = false;

                if (enemyShips.get(enemyShipId).isCollision(localMissile.getX(), localMissile.getY(), localMissile.getSize())) {
                    short localEnemyShipStrength = enemyShips.get(enemyShipId).getStrengthCount();

                    if (!enemyShips.get(enemyShipId).minesStrengthCount(localMissile.getStrengthCount())) {
                        enemyShips.get(enemyShipId).addPointsForItToUser(false);

                        enemyShips.remove(enemyShipId--);

                        enemyShipRemoved = true;
                    }
                    if (!localMissile.minesStrengthCount(localEnemyShipStrength)) {
                        playerShip.getMissiles().remove(missileId--);

                        missileRemoved = true;

                        break;
                    }
                }

                if (enemyShipRemoved)
                    continue;

                EnemyShip localEnemyShip = enemyShips.get(enemyShipId);

                for (int enemyShipMissileId = 0; enemyShipMissileId < localEnemyShip.getMissiles().size(); enemyShipMissileId++) {
                    if (localEnemyShip.getMissiles().get(enemyShipMissileId).isCollision(localMissile.getX(), localMissile.getY(), localMissile.getSize())) {
                        short localEnemyShipMissileStrength = localEnemyShip.getMissiles().get(enemyShipMissileId).getStrengthCount();

                        if (!localEnemyShip.getMissiles().get(enemyShipMissileId).minesStrengthCount(localMissile.getStrengthCount())) {
                            localEnemyShip.getMissiles().get(enemyShipMissileId).addPointsForItToUser(false);

                            localEnemyShip.getMissiles().remove(enemyShipMissileId--);
                        }
                        if (!localMissile.minesStrengthCount(localEnemyShipMissileStrength)) {
                            playerShip.getMissiles().remove(missileId--);

                            missileRemoved = true;

                            break;
                        }
                    }
                }

                if (missileRemoved)
                    break;
            }

            if (missileRemoved)
                continue;

            // Будущие взаимодействие снарядов с косм. объектами
        }

        // Воздействие астеройдов на мир
        for (int asteroidId = 0; asteroidId < asteroids.size(); asteroidId++) {
            if (!bonusState.isShadowState() && asteroids.get(asteroidId).isCollision(playerShip.getX(), playerShip.getY(), playerShip.getSize())) {
                short localAsteroidStrength = asteroids.get(asteroidId).getStrengthCount();

                if (!asteroids.get(asteroidId).minesStrengthCount(playerShip.getStrengthCount())) {
                    asteroids.get(asteroidId).addPointsForItToUser(false);

                    asteroids.remove(asteroidId--);
                }
                if (!playerShip.minesStrengthCount(localAsteroidStrength)) {
                    gameState = GameState.gameOver;
                }
            }
        }

        //
        if (bigAsteroid != null) {
            if (playerShip.isCollision(bigAsteroid.getX(), bigAsteroid.getY(), bigAsteroid.getSize())) {
                short localBigAsteroidStrength = bigAsteroid.getStrengthCount();

                if (!bigAsteroid.minesStrengthCount(playerShip.getStrengthCount())) {
                    bigAsteroid.addPointsForItToUser(false);

                    bigAsteroid = null;
                }
                if (!playerShip.minesStrengthCount(localBigAsteroidStrength)) {
                    gameState = GameState.gameOver;
                }
            }
        }

        // Воздействие кораблей противникак на мир
        for (EnemyShip enemyShip : enemyShips) {
            for (int enemyShipMissileId = 0; enemyShipMissileId < enemyShip.getMissiles().size(); enemyShipMissileId++)
                if (!bonusState.isShadowState() &&
                        enemyShip.getMissiles().get(enemyShipMissileId).isCollision(playerShip.getX(), playerShip.getY(), playerShip.getSize())) {
                    short localEnemyMissileStrength = enemyShip.getMissiles().get(enemyShipMissileId).getStrengthCount();

                    if (!enemyShip.getMissiles().get(enemyShipMissileId).minesStrengthCount(playerShip.getStrengthCount())) {
                        enemyShip.getMissiles().get(enemyShipMissileId).addPointsForItToUser(false);

                        enemyShip.getMissiles().remove(enemyShipMissileId--);
                    }
                    if (!playerShip.minesStrengthCount(localEnemyMissileStrength)) {
                        gameState = GameState.gameOver;
                    }
                }
        }

        // Взаимодействие корабля с бонусами
        // Взаимодействие корабля с HP
        if (healthPoint != null)
            if (healthPoint.isCollision(playerShip.getX(), playerShip.getY(), playerShip.getSize())) {
                healthPoint.executeBonus(bonusState);
                healthPoint = null;
            }

        // Взаимодействия корабля с FreezeBonus
        if (freezeBonus != null)
            if (freezeBonus.isCollision(playerShip.getX(), playerShip.getY(), playerShip.getSize())) {
                freezeBonus.executeBonus(bonusState);
                freezeBonus = null;
            }

        // Взаимодействия корабля с ShadowState
        if (shadowStateBonus != null)
            if (shadowStateBonus.isCollision(playerShip.getX(), playerShip.getY(), playerShip.getSize())) {
                shadowStateBonus.executeBonus(bonusState);
                shadowStateBonus = null;
            }
    }

    // Создание новых космических объектов
    private void checkIfNewSpaceObjects() {
        if (bonusState.isFreezeState())
            return;

        Random rand = new Random();

        if (currentTimeBonusInterval >= BONUS_POINTS_INTERVAL) { // Создание Бонуса
            BONUS_POINTS_INTERVAL = rand.nextInt(200) + 200;

            switch (rand.nextInt(4)) {
                case 0: // Freeze
                    if (freezeBonus == null)
                        freezeBonus = new FreezeBonus(getContext());
                    break;
                case 1: // HP
                    if (healthPoint == null)
                        healthPoint = new HealthPoint(getContext());
                    break;
                case 2: // ShadowState
                    if (shadowStateBonus == null)
                        shadowStateBonus = new ShadowStateBonus(getContext());
                case 3: // None
                    break;
            }

            currentTimeBonusInterval = 0;
        } else
            currentTimeBonusInterval++;

        if (bodiesParams.getAsteroidParams().isCreatingAllow()
                && bodiesParams.getAsteroidParams().getCurrentAsteroidInterval() >=
                bodiesParams.getAsteroidParams().getMaxAsteroidInterval()) { // Создание астеройдов
            int randomCount = rand.nextInt(bodiesParams.getAsteroidParams().getUpperAsteroidCreatingCount()) + 1;

            for (int i = 0; i < randomCount; i++) {
                Asteroid asteroid = new Asteroid(getContext());
                asteroids.add(asteroid);
            }

            bodiesParams.getAsteroidParams().setCurrentAsteroidInterval((short) 0);
        }

        if (bodiesParams.getBigAsteroid().isCreatingAllow() &&
                bigAsteroid == null) {
            bigAsteroid = new BigAsteroid(getContext());
        }

        if (bodiesParams.getEnemyShip().isCreatingAllow() &&
                bodiesParams.getEnemyShip().getCurrentEnemyShipInterval() >=
                    bodiesParams.getEnemyShip().getMaxEnemyShipInterval()) {
                EnemyShip enemyShip = new EnemyShip(getContext());

                enemyShips.add(enemyShip);

                bodiesParams.getEnemyShip().setCurrentEnemyShipInterval((short) 0);
            }
    }
}