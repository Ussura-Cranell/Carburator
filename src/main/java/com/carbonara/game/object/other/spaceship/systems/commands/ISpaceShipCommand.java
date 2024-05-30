package com.carbonara.game.object.other.spaceship.systems.commands;

import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public interface ISpaceShipCommand {
    public Class<? extends AbstractSystem> getClassSystem();
    void execute();
    void setSystem(AbstractSystem system);
}
