package com.carbonara.game.object.other.spaceship.components.weapon;

import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.other.spaceship.systems.WeaponControlSystem;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public abstract class AbstractWeapon extends AbstractSystemComponent {
    @Override
    public Class<? extends AbstractSystem> getClassSystem() {
        return WeaponControlSystem.class;
    }
}
