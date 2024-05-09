package com.carbonara.game.object.spaceship.components.engine;

import com.carbonara.game.object.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.spaceship.systems.FlightControlSystem;

public abstract class AbstractEngine extends AbstractSystemComponent {
    @Override
    public String getTypeSystem() {
        return FlightControlSystem.class.getName();
    }
}
