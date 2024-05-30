package com.carbonara.game.object.other.spaceship.systems.interfaces;

import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public interface IRegisterSystem {
    void registerSystem(Class<? extends AbstractSystem> classSystem, AbstractSystem system);
}
