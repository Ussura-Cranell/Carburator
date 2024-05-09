package com.carbonara.game.object.spaceship.components.weapon;

import com.carbonara.game.object.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.spaceship.systems.WeaponControlSystem;

public abstract class AbstractWeapon extends AbstractSystemComponent {
    @Override
    public String getTypeSystem() {
        return WeaponControlSystem.class.getName();
    }
}
