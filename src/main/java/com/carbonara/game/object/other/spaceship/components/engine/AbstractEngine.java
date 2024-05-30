package com.carbonara.game.object.other.spaceship.components.engine;

import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.other.spaceship.systems.FlightControlSystem;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public abstract class AbstractEngine extends AbstractSystemComponent {
    @Override
    public Class<? extends AbstractSystem> getClassSystem() {
        return FlightControlSystem.class;
    }
}
