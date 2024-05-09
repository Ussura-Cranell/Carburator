package com.carbonara.game.object.spaceship.components.shield;

import com.carbonara.game.object.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.spaceship.systems.ShieldControlSystem;

public abstract class AbstractShield extends AbstractSystemComponent {
    @Override
    public String getTypeSystem() {
        return ShieldControlSystem.class.getName();
    }
}
