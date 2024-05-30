package com.carbonara.game.object.other.spaceship.managers.testing;

import com.jme3.scene.Spatial;

public class Enemy {
    private Spatial model;
    public Enemy(Spatial model){
        this.model = model;
    }

    public Spatial getModel() {
        return model;
    }
}
