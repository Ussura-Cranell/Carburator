package com.carbonara.game.object.other.spaceship.systems.commands.flight;

import com.carbonara.game.object.other.spaceship.systems.FlightControlSystem;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;
import com.carbonara.game.object.other.spaceship.systems.commands.AbstractSpaceShipCommand;
import com.jme3.math.Vector3f;

public class StopDirectionCommand extends AbstractSpaceShipCommand {

    private FlightControlSystem flightControlSystem;

    @Override
    public void execute() {
        flightControlSystem.setDirection(Vector3f.ZERO);
    }

    @Override
    public Class<? extends AbstractSystem> getClassSystem() {
        return FlightControlSystem.class;
    }

    @Override
    public void setSystem(AbstractSystem system) {
        if (system instanceof FlightControlSystem) flightControlSystem = (FlightControlSystem) system;
        else throw new RuntimeException("ERROR");
    }
}
