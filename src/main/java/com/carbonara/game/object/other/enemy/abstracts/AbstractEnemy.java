package com.carbonara.game.object.other.enemy.abstracts;

import com.carbonara.game.object.other.interfaces.ITakeDamage;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;
import com.carbonara.game.object.other.spaceship.components.weapon.AbstractWeapon;
import com.carbonara.game.object.other.spaceship.components.weapon.Weapon;
import com.jme3.math.Vector3f;

import java.util.*;


public class AbstractEnemy implements ITakeDamage {
    protected float StatusSpaceship = 100.0f;     // - здоровье корабля противника
    protected float StatusEnergyShields = 25.0f; // - прочность щитов противника
    protected float resources = 5; // - ресурсы падающие с врага
    protected Random random = new Random();
    protected Vector3f position = new Vector3f(
            random.nextInt(-50,50),
            random.nextInt(-50,50),
            random.nextInt(-50,50));

    public Vector3f getPosition() {
        return position;
    }

    protected void setPosition(Vector3f value){
        position = value;
    }

    @Override
    public String toString() {
        return "AbstractEnemy{" +
                ", id=" + this.hashCode() +
                ", StatusSpaceship=" + StatusSpaceship +
                ", StatusEnergyShields=" + StatusEnergyShields +
                ", position=" + position +
                '}';
    }

    // получить урон от орудия
    @Override
    public void takeDamage(AbstractWeapon weapon){
        // проверка дальности орудия

        float distance = position.distance(AbstractSpaceship.getAbstractSpaceShip().getSpaceShipSpatial()
                .getLocalTranslation());

        // System.out.println("дальность: " + distance);
        // System.out.println("радиус орудия: " + weapon.getRange());

        if (distance <= weapon.getRange()){

            float damage = weapon.getDamage();

            // наносится чистый урон по кораблю
            this.StatusSpaceship -= damage;
            if (this.StatusSpaceship <= 0) {
                weapon.setTarget(Optional.empty());
                AbstractSpaceship.getAbstractSpaceShip().addShipResources(resources);
            }
            /*
            String message = "";
            message += "weapon_id: " + weapon.hashCode() + " target: " + weapon.getTarget().hashCode() + " distance: " + distance
                    + " damage: " + damage + " strength remaining: " + this.getStatusSpaceship();
            System.out.println(message);*/
        }
    }

    public float getStatusSpaceship() {
        return StatusSpaceship;
    }

    public float getStatusEnergyShields() {
        return StatusEnergyShields;
    }

    float timer = 0.0f;

    private final AbstractWeapon weapon = new Weapon(){
        @Override
        public void initialize() {
            this.damage = 5.0f;
            this.speed = random.nextFloat(1,2);
            this.setDamageType(DamageType.KINETIC);
        }
    };

    public void attack(float v){
        // System.out.println("attack 2");
        timer += v;
        if (timer >= weapon.getSpeed()) {
            timer = 0;
            AbstractSpaceship.getAbstractSpaceShip().takeDamage(weapon);
        }
    }

    private float desiredDistance = 100.0f; // Желаемое расстояние до корабля
    private float speed = 10.0f; // Скорость движения врага
    private float mapBoundary = 500.0f; // Пределы карты

    public void moving(float tpf) {
        // Получаем текущую позицию корабля
        Vector3f shipPosition = AbstractSpaceship.getAbstractSpaceShip().getSpaceShipSpatial().getLocalTranslation();
        // Получаем текущую позицию врага
        Vector3f enemyPosition = this.getPosition();

        // Вычисляем вектор направления от врага к кораблю
        Vector3f directionToShip = shipPosition.subtract(enemyPosition);
        float distanceToShip = directionToShip.length();

        // Проверяем расстояние до корабля и корректируем направление
        if (distanceToShip > desiredDistance) {
            directionToShip.normalizeLocal(); // Нормализуем вектор направления
        } else {
            directionToShip.negateLocal().normalizeLocal(); // Отдаляемся от корабля
        }

        // Вычисляем смещение на текущий кадр
        Vector3f displacement = directionToShip.mult(speed * tpf);

        // Обновляем позицию врага
        enemyPosition.addLocal(displacement);

        // Ставим врага на один уровень по Y с кораблем
        enemyPosition.y = shipPosition.y;

        // Сохраняем новую позицию врага
        this.position.set(enemyPosition);
    }
}

