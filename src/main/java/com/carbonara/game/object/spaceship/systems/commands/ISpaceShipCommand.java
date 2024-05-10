package com.carbonara.game.object.spaceship.systems.commands;

import com.carbonara.game.object.spaceship.systems.abstracts.AbstractSystem;

public interface ISpaceShipCommand {
    String getTypeSystem();
    void execute();
    void setSystem(AbstractSystem system);
}
