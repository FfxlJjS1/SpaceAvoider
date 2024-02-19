package com.project.spaceavoider.bodies.bonus;

public final class BonusState {
    private boolean freezeState = false, healthAdd = false, shadowState = false;
    private short currentFreezeBonusInterval = 0, maxFreezeBonusInterval = 150,
                    currentShadowStateInterval = 0, maxShadowStateInterval = 120,
            healthAddCount = 0;

    public void updateCurrentTime() {
        if (freezeState)
            if (currentFreezeBonusInterval < maxFreezeBonusInterval)
                currentFreezeBonusInterval++;
            else {
                freezeState = false;
                currentFreezeBonusInterval = 0;
            }
        if (shadowState)
            if (currentShadowStateInterval < maxShadowStateInterval)
                currentShadowStateInterval++;
            else {
                shadowState = false;
                currentShadowStateInterval = 0;
            }
    }

    public float ratioMaxCurrent() {
        float maxCurrent = 0, nextT = 0f;

        nextT = (float)currentShadowStateInterval/(float)maxShadowStateInterval;

        if (nextT > maxCurrent)
            maxCurrent = nextT;

        nextT = (float)currentFreezeBonusInterval/(float)maxFreezeBonusInterval;

        if (nextT > maxCurrent)
            maxCurrent = nextT;

        return maxCurrent;
    }

    // Методы Freeze
    public boolean isFreezeState() {
        return freezeState;
    }

    public void setFreezeState(boolean freezeState) {
        this.freezeState = freezeState;
    }

    public short getCurrentFreezeBonusInterval() {
        return currentFreezeBonusInterval;
    }

    public void setCurrentFreezeBonusInterval(short currentFreezeBonusInterval) {
        if (currentFreezeBonusInterval < maxFreezeBonusInterval)
            this.currentFreezeBonusInterval = currentFreezeBonusInterval;
    }

    public short getMaxFreezeBonusInterval() {
        return maxFreezeBonusInterval;
    }

    public void setMaxFreezeBonusInterval(short maxFreezeBonusInterval) {
        if (maxFreezeBonusInterval > currentFreezeBonusInterval)
            this.maxFreezeBonusInterval = maxFreezeBonusInterval;
    }

    // Методы HealthAdd
    public boolean isHealthAdd() {
        return healthAdd;
    }

    public void setHealthAdd(boolean healthAdd) {
        if (!healthAdd)
            healthAddCount = 0;
        this.healthAdd = healthAdd;
    }

    public short getHealthAddCount() {
        return healthAddCount;
    }

    public void setHealthAddCount(short healthAddCount) {
        this.healthAddCount = healthAddCount;
    }

    // Методы ShadowState

    public boolean isShadowState() {
        return shadowState;
    }

    public void setShadowState(boolean shadowState) {
        this.shadowState = shadowState;
    }

    public short getCurrentShadowStateInterval() {
        return currentShadowStateInterval;
    }

    public void setCurrentShadowStateInterval(short currentShadowStateInterval) {
        this.currentShadowStateInterval = currentShadowStateInterval;
    }
}
