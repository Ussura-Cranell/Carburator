package com.carbonara.game.object.other.spaceship.components.weapon;

import com.carbonara.game.object.other.EvilClass;
import com.carbonara.game.object.other.enemy.abstracts.AbstractEnemy;
import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.other.spaceship.systems.WeaponControlSystem;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

import java.util.Optional;

public abstract class AbstractWeapon extends AbstractSystemComponent {

    @Override
    public Class<? extends AbstractSystem> getClassSystem() {
        return WeaponControlSystem.class;
    }

    protected Optional<AbstractEnemy> target = EvilClass.getVaultEnemies().getClosestEnemy();     /* - установка цели для конкретного орудия
    ЕСЛИ ОРУДИЕ ДОБАВИЛИ ДО ИНИЦИАЛИЗАЦИИ, МОЖЕТ ПРОИЗОЙТИ ОШИБКА
    */
    protected float speed = 1.0f;       // - скорость
    protected float accuracy = 0.0f;    // - точность
    protected float damage = 25.0f;      // - урон

    protected float range = 100.0f;       // - диапазон атаки

    // Типы урона
    public enum DamageType {
        KINETIC, // Механический урон
        ENERGY   // Энергетический урон
    }

    public Optional<AbstractEnemy> getTarget() {
        return target;
    }

    public void setTarget(Optional<AbstractEnemy> target) {
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

    private DamageType damageType = DamageType.KINETIC; // По умолчанию механический урон

    // Метод для выбора типа урона
    public void setDamageType(DamageType type) {
        damageType = type;
        logger.info("Damage type set to: " + type);
    }


    float timer = 0.0f;
    @Override
    public void update(float v) {
        timer += v;
        //logger.info("update");
        // скорость обновления орудия грубо говоря)
        if (timer > speed) {
            // iiSystem.out.println("tick: " + getClass().getName());
            timer = 0;
            // super.update();
            if (target.isEmpty()) target = EvilClass.getVaultEnemies().getClosestEnemy();
            this.getTarget().ifPresent(enemy -> enemy.takeDamage(this));
        }
    }

    @Override
    public String toString() {
        return "AbstractWeapon{" +
                "target_id=" + target.map(enemy -> String.valueOf(enemy.hashCode())).orElse("null") +
                ", damage=" + damage +
                ", range=" + range +
                ", name='" + name + '\'' +
                '}';
    }
}
