package com.carbonara.game.object.other.spaceship.components.reactor;

import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.other.spaceship.systems.ReactorControlSystem;

public abstract class AbstractReactor extends AbstractSystemComponent {
    @Override
    public String getTypeSystem() {
        return ReactorControlSystem.class.getName();
    }
}
