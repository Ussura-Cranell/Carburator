package com.carbonara.game.object.spaceship.systems.commands.flight;

import com.carbonara.game.object.spaceship.systems.FlightControlSystem;
import com.carbonara.game.object.spaceship.systems.abstracts.AbstractSystem;
import com.carbonara.game.object.spaceship.systems.commands.AbstractSpaceShipCommand;
import com.jme3.math.Vector3f;

public class TeleportationByCoordinatesCommand extends AbstractSpaceShipCommand {
    private FlightControlSystem flightControlSystem;
    private Vector3f coordinates;
    public TeleportationByCoordinatesCommand(Vector3f coordinates){
        this.coordinates = coordinates;
    }
    @Override
    public String getTypeSystem() {
        return FlightControlSystem.class.getName();
    }

    @Override
    public void execute() {
        flightControlSystem.teleportationByCoordinates(coordinates);
    }

    @Override
    public void setSystem(AbstractSystem system) {
        if (system instanceof FlightControlSystem) flightControlSystem = (FlightControlSystem) system;
        else throw new RuntimeException("The system for executing the command is not specified correctly!");
    }
}
