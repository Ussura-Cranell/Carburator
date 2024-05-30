package com.carbonara.game.object.other.spaceship.components.interfaces;

import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public interface ISystemComponent {
    int getOccupiesUnitsSpace();
    Class<? extends AbstractSystem> getClassSystem();
    String getName();
}
