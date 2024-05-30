package com.carbonara.game.object.other.spaceship.components.shield;

import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.other.spaceship.systems.ShieldControlSystem;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public abstract class AbstractShield extends AbstractSystemComponent {
    @Override
    public Class<? extends AbstractSystem> getClassSystem() {
        return ShieldControlSystem.class;
    }
}
