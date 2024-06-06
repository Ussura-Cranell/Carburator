package com.carbonara.game.object.other.spaceship.components.weapon;

import com.carbonara.game.object.other.enemy.abstracts.AbstractEnemy;
import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.other.spaceship.systems.WeaponControlSystem;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public abstract class AbstractWeapon extends AbstractSystemComponent {
    @Override
    public Class<? extends AbstractSystem> getClassSystem() {
        return WeaponControlSystem.class;
    }

    protected AbstractEnemy target;     // - установка цели для конкретного орудия
    protected float speed = 0.0f;       // - скорость
    protected float accuracy = 0.0f;    // - точность
    protected float damage = 0.0f;      // - урон

    protected float range = 0.0f;       // - диапазон атаки

    public AbstractEnemy getTarget() {
        return target;
    }

    public void setTarget(AbstractEnemy target) {
        this.target = target;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }
}
