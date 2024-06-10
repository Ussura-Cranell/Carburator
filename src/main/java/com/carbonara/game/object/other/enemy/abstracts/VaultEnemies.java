package com.carbonara.game.object.other.enemy.abstracts;

import com.carbonara.game.gui.spaceship.systems.ScreenPageKeeper;
import com.carbonara.game.object.other.EvilClass;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;

import java.util.*;

public class VaultEnemies {

    /* Класс в котором будут храниться враги и обрабатываться */
    private Optional<AbstractEnemy> closestEnemy;               // - будет отвечать за ближайшего противника
    private final Set<AbstractEnemy> enemySet = new HashSet<>();
    private Optional<AbstractSpaceship> spaceship = Optional.empty();
    private ScreenPageKeeper pageKeeper;

    public VaultEnemies(){
        closestEnemy = Optional.empty();
    }

    public void update(float v){
        updateEnemySet();       // удаление погибших кораблей
        updateClosestEnemy();   // установка ближайшего противника
        enemyMoving(v);         // логика передвижения
        enemyAttack(v);         // логика атаки
    }

    public void addEnemy(AbstractEnemy enemy){
        enemySet.add(enemy);
    }

    public Optional<AbstractEnemy> getClosestEnemy() {
        return closestEnemy;
    }

    public void updateClosestEnemy() {
        // System.out.println("updateClosestEnemy");
        // обновляет ближайшего врага
        spaceship.ifPresentOrElse(
                abstractSpaceShip -> closestEnemy = enemySet.stream()
                        .min(Comparator.comparingDouble(o -> o.position.distance(abstractSpaceShip
                                .getSpaceShipSpatial().getLocalTranslation())))
                        .map(Optional::of)
                        .orElseGet(Optional::empty),
                () -> spaceship = Optional.of(AbstractSpaceship.getAbstractSpaceShip())
        );

        // Обработка vaultEnemies
        closestEnemy = Optional.ofNullable(this)
                .map(VaultEnemies::getClosestEnemy)
                .orElse(Optional.empty());
    }

    public void updateEnemySet(){
        // System.out.println("updateEnemySet");
        enemySet.removeIf(enemy -> enemy.getStatusSpaceship() <= 0);
    }

    public Set<AbstractEnemy> getEnemySet() {
        return enemySet;
    }

    public void enemyAttack(float v){
        // System.out.println("attack 1");
        // System.out.println(Arrays.asList(enemySet));
        enemySet.forEach(enemy -> enemy.attack(v));
    }

    public ScreenPageKeeper getPageKeeper() {
        return pageKeeper;
    }

    public void setPageKeeper(ScreenPageKeeper pageKeeper) {
        this.pageKeeper = pageKeeper;
    }

    public void enemyMoving(float v){
        // System.out.println("attack");
        //for (AbstractEnemy enemy : enemySet) enemy.moving(v);
        enemySet.forEach(enemy -> enemy.moving(v));
    }
}
