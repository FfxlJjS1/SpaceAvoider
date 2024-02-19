package com.project.spaceavoider.bodies.ships;

public class ship_assembly {
    private int shipResource;
    private int missileResource;

    public ship_assembly(int shipResource, int missileResource) {
        this.shipResource = shipResource;
        this.missileResource = missileResource;
    }

    public int getShipResource() {
        return shipResource;
    }

    public void setShipResource(int shipResource) {
        this.shipResource = shipResource;
    }

    public int getMissileResource() {
        return missileResource;
    }

    public void setMissileResource(int missileResource) {
        this.missileResource = missileResource;
    }
}
