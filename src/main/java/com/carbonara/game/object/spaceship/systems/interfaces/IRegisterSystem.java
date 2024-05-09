package com.carbonara.game.object.spaceship.systems.interfaces;

import com.carbonara.game.object.spaceship.systems.abstracts.AbstractSystem;
import com.jme3.scene.control.Control;

public interface IRegisterSystem {
    void registerSystem(String nameSystem, AbstractSystem system);
}
