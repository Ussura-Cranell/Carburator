package com.carbonara.game.object.other.spaceship.components.weapon;

import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.other.spaceship.systems.WeaponControlSystem;

public abstract class AbstractWeapon extends AbstractSystemComponent {
    @Override
    public String getTypeSystem() {
        return WeaponControlSystem.class.getName();
    }
}
