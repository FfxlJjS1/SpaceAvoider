package com.project.spaceavoider.bodies;

import com.project.spaceavoider.R;
import com.project.spaceavoider.activities.MagazineActivity;
import com.project.spaceavoider.gamesetting.GameView;

import static com.project.spaceavoider.gamesetting.GameView.gameDifficult;

public class BodiesParams {
    private final PlayerShipParams playerShipParams;
    private final AsteroidParams asteroidParams;
    private final MagazineActive magazineActive;
    private final EnemyShip enemyShip;
    private final BigAsteroid bigAsteroid;
    private final MiniBoss miniBoss;
    private final Boss boss;
    private final BigBoss bigBoss;

    public BodiesParams() {
        playerShipParams = new PlayerShipParams();
        asteroidParams = new AsteroidParams();
        magazineActive = new MagazineActive();
        enemyShip = new EnemyShip();
        bigAsteroid = new BigAsteroid();
        miniBoss = new MiniBoss();
        boss = new Boss();
        bigBoss = new BigBoss();
    }

    public void updateParams() {
        playerShipParams.updateParams();
        asteroidParams.updateParams();
        magazineActive.updateParams();
        enemyShip.updateParams();
        bigAsteroid.updateParams();
        miniBoss.updateParams();
        boss.updateParams();
        bigBoss.updateParams();
    }

    public PlayerShipParams getPlayerShipParams() {
        return playerShipParams;
    }

    public AsteroidParams getAsteroidParams() {
        return asteroidParams;
    }

    public MagazineActive getMagazineActive() {
        return magazineActive;
    }

    public EnemyShip getEnemyShip() {
        return enemyShip;
    }

    public BigAsteroid getBigAsteroid() {
        return bigAsteroid;
    }

    public MiniBoss getMiniBoss() {
        return miniBoss;
    }

    public Boss getBoss() {
        return boss;
    }

    public BigBoss getBigBoss() {
        return bigBoss;
    }

    private class StandartParams {
        boolean isCreatingAllow;
        protected int bitmapId;
        protected float y, size;
        protected int pointsForIt;
        protected short strengthCount, maxStrengthCount;

        public StandartParams() {
        }

        public StandartParams(int bitmapId, float size, int pointsForIt, short strengthCount, short maxStrengthCount) {
            this.bitmapId = bitmapId;
            this.size = size;
            this.pointsForIt = pointsForIt;
            this.strengthCount = strengthCount;
            this.maxStrengthCount = maxStrengthCount;
        }

        public StandartParams(int bitmapId, float y, float size, int pointsForIt, short strengthCount, short maxStrengthCount) {
            this.bitmapId = bitmapId;
            this.y = y;
            this.size = size;
            this.pointsForIt = pointsForIt;
            this.strengthCount = strengthCount;
            this.maxStrengthCount = maxStrengthCount;
        }

        public void updateParams() {
        }

        public boolean isCreatingAllow() {
            return isCreatingAllow;
        }

        public int getBitmapId() {
            return bitmapId;
        }

        public float getY() {
            return y;
        }

        public float getSize() {
            return size;
        }

        public int getPointsForIt() {
            return pointsForIt;
        }

        public short getStrengthCount() {
            return strengthCount;
        }

        public short getMaxStrengthCount() {
            return maxStrengthCount;
        }
    }

    public class PlayerShipParams extends StandartParams {
        private short toStateUpLine;
        private short maxMissileTimeOut, currentMissileTimeOut;
        private short missileStrength = 1;
        private float speed;

        public PlayerShipParams() {
            super(R.drawable.ship_r0, 4f, 0, (short)3, (short)5);

            toStateUpLine = 27;
            speed = 0.5f;
            missileStrength = 1;
            maxMissileTimeOut = 120;
            currentMissileTimeOut = 100;
        }

        public void updateParams() {
            speed = 0.5f + MagazineActivity.plusSpeed;
            maxMissileTimeOut = (short) (120 + MagazineActivity.minesMaxMissileTimeOut);
            maxStrengthCount = (short) (5 + MagazineActivity.plusMaxStrengthCount);
            missileStrength = (short) (1 + MagazineActivity.plusMissileStrength);
        }

        public float getSpeed() {
            return speed;
        }

        public short getToStateUpLine() {
            return toStateUpLine;
        }

        public short getMaxMissileTimeOut() {
            return maxMissileTimeOut;
        }

        public short getCurrentMissileTimeOut() {
            return currentMissileTimeOut;
        }

        public short getMissileStrength() {
            return missileStrength;
        }
    }

    public class AsteroidParams extends StandartParams {
        private short currentAsteroidInterval, maxAsteroidInterval, upperAsteroidCreatingCount;
        private float maxSpeed, minSpeed;

        public AsteroidParams() {
            super(R.drawable.asteroid, 2f, 2, (short)1, (short)1);

            currentAsteroidInterval = 0;
            maxAsteroidInterval = 50;
            upperAsteroidCreatingCount = 5;
            minSpeed = 0.4f;
            maxSpeed = 0.9f;
        }

        @Override
        public void updateParams() {
            if (gameDifficult.getDifficultLevel() < 10 || gameDifficult.getDifficultLevel() == 35)
                isCreatingAllow = true;
            else if (isCreatingAllow)
                isCreatingAllow = false;

            if (isCreatingAllow) {
                upperAsteroidCreatingCount = (short) (5 + gameDifficult.getDifficultLevel());
                maxAsteroidInterval = (short) (50 - gameDifficult.getDifficultLevel()*2);
                minSpeed = 0.4f + gameDifficult.getDifficultLevel()*0.1f;
                maxSpeed = 0.9f + gameDifficult.getDifficultLevel()*0.16f;

                currentAsteroidInterval++;
            }
        }

        public short getCurrentAsteroidInterval() {
            return currentAsteroidInterval;
        }

        public void setCurrentAsteroidInterval(short currentAsteroidInterval) {
            this.currentAsteroidInterval = currentAsteroidInterval;
        }

        public short getMaxAsteroidInterval() {
            return maxAsteroidInterval;
        }

        public short getUpperAsteroidCreatingCount() {
            return upperAsteroidCreatingCount;
        }

        public float getMaxSpeed() {
            return maxSpeed;
        }

        public float getMinSpeed() {
            return minSpeed;
        }
    }

    public class MagazineActive extends StandartParams {
        public MagazineActive() {
            //...
        }

        @Override
        public void updateParams() {
            if (gameDifficult.getDifficultLevel() > 10)
                isCreatingAllow = true;
            else if (isCreatingAllow)
                isCreatingAllow = false;
        }
    }

    public class EnemyShip extends StandartParams {
        private float verticalSpeed, maxSpeed, minSpeed;
        private short currentEnemyShipInterval, maxEnemyShipInterval;
        private short maxMissileTimeOut, currentMissileTimeOut;
        private short missileStrength = 1;

        public EnemyShip() {
            super(R.drawable.ship_enemy_r1, 6f, 10, (short)3, (short)3);

            y = -size;
            verticalSpeed = 0.05f;
            minSpeed = 0.1f;
            maxSpeed = 0.6f;
            currentEnemyShipInterval = 180;
            maxEnemyShipInterval = 250;
            currentMissileTimeOut = 120;
            maxMissileTimeOut = 140;
        }

        @Override
        public void updateParams() {
            if (gameDifficult.getDifficultLevel() > 10)
                isCreatingAllow = true;
            else if (isCreatingAllow)
                isCreatingAllow = false;

            maxEnemyShipInterval = 250;
            strengthCount = (short) (0.2*gameDifficult.getDifficultLevel()+1);
            maxStrengthCount = (short) (0.2*gameDifficult.getDifficultLevel()+1);
            pointsForIt = (int) (5+gameDifficult.getDifficultLevel()*0.1f);
            minSpeed = gameDifficult.getDifficultLevel()*0.03f;
            maxSpeed = gameDifficult.getDifficultLevel()*0.041f;
            maxMissileTimeOut = (short) (140 - gameDifficult.getDifficultLevel());

            if (isCreatingAllow)
                currentEnemyShipInterval++;
        }


        public float getVerticalSpeed() {
            return verticalSpeed;
        }

        public short getMaxMissileTimeOut() {
            return maxMissileTimeOut;
        }

        public short getCurrentMissileTimeOut() {
            return currentMissileTimeOut;
        }

        public void setCurrentEnemyShipInterval(short currentEnemyShipInterval) {
            this.currentEnemyShipInterval = currentEnemyShipInterval;
        }

        public float getMaxSpeed() {
            return maxSpeed;
        }

        public float getMinSpeed() {
            return minSpeed;
        }

        public short getCurrentEnemyShipInterval() {
            return currentEnemyShipInterval;
        }

        public short getMaxEnemyShipInterval() {
            return maxEnemyShipInterval;
        }

        public short getMissileStrength() {
            return missileStrength;
        }
    }

    public class BigAsteroid extends StandartParams {
        private float speed;

        public BigAsteroid() {
            bitmapId = R.drawable.asteroid;
            size = GameView.maxX;

            strengthCount = 10;
            maxStrengthCount = 10;

            speed = 0.05f;
        }

        @Override
        public void updateParams() {
            if (gameDifficult.getDifficultLevel() == 10) {
                isCreatingAllow = true;

                speed = 0.05f;

                strengthCount = 18;
                maxStrengthCount = 18;
            }
            else if (gameDifficult.getDifficultLevel() == 35) {
                isCreatingAllow = true;

                speed = 0.12f;

                strengthCount = 40;
                maxStrengthCount = 40;
            }
            else if (isCreatingAllow)
                isCreatingAllow = false;
        }

        public float getSpeed() {
            return speed;
        }
    }

    public class MiniBoss extends StandartParams {
        @Override
        public void updateParams() {
            if (gameDifficult.getDifficultLevel() == 30)
                isCreatingAllow = true;
            else if (isCreatingAllow)
                isCreatingAllow = false;
        }
    }

    public class Boss extends StandartParams {
        @Override
        public void updateParams() {
            if (gameDifficult.getDifficultLevel() == 40)
                isCreatingAllow = true;
            else if (isCreatingAllow)
                isCreatingAllow = false;
        }
    }

    public class BigBoss extends StandartParams {
        @Override
        public void updateParams() {
            if (gameDifficult.getDifficultLevel() == 50)
                isCreatingAllow = true;
            else if (isCreatingAllow)
                isCreatingAllow = false;
        }
    }
}
