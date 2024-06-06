package com.carbonara.game.object.other.spaceship.components.interfaces;

import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public interface ISystemComponent {
    int getOccupiesUnitsSpace();
    int getOccupiesUnitsEnergy();
    Class<? extends AbstractSystem> getClassSystem();
    String getClassSimpleName();
}
