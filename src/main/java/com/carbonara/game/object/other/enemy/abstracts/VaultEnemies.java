package com.carbonara.game.object.other.enemy.abstracts;

import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceShip;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class VaultEnemies {

    /* Класс в котором будут храниться враги и обрабатываться */
    private Optional<AbstractEnemy> closestEnemy;               // - будет отвечать за ближайшего противника
    private final Set<AbstractEnemy> enemySet = new HashSet<>();
    private Optional<AbstractSpaceShip> spaceship = Optional.empty();

    public VaultEnemies(){
        closestEnemy = Optional.empty();
    }


    public void update(float v){
        updateEnemySet();       // удаление погибших кораблей
        updateClosestEnemy();   // установка ближайшего противника
        enemyAttack(v);         // логика атаки
    }

    public void addEnemy(AbstractEnemy enemy){
        enemySet.add(enemy);
    }

    public Optional<AbstractEnemy> getClosestEnemy() {
        return closestEnemy;
    }

    public void updateClosestEnemy(){
        // обновляет ближайшего врага
        spaceship.ifPresentOrElse(
                abstractSpaceShip -> closestEnemy = enemySet.stream()
                        .min(Comparator.comparingDouble(o -> o.position.distance(abstractSpaceShip
                                .getSpaceShipSpatial().getLocalTranslation())))
                        .map(Optional::of)
                        .orElseGet(Optional::empty),
                () -> spaceship = Optional.of(AbstractSpaceShip.getAbstractSpaceShip())
        );
    }
    public void updateEnemySet(){
        enemySet.removeIf(enemy -> enemy.getStatusSpaceship() <= 0);
    }

    public Set<AbstractEnemy> getEnemySet() {
        return enemySet;
    }

    public void enemyAttack(float v){
        // System.out.println("attack");
        enemySet.forEach(enemy -> enemy.attack(v));
    }
}
