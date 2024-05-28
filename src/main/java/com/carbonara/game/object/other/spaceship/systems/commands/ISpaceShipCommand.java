package com.carbonara.game.object.other.spaceship.systems.commands;

import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public interface ISpaceShipCommand {
    String getTypeSystem();
    void execute();
    void setSystem(AbstractSystem system);
}
