package com.carbonara.game.gui.spaceship.systems.commnads;

import com.carbonara.game.object.gameobjects.states.TESTINGACTION.interaction.interfaces.IActionCommand;
import com.carbonara.game.object.other.EvilClass;
import com.carbonara.game.object.other.enemy.abstracts.AbstractEnemy;
import com.carbonara.game.object.other.enemy.abstracts.Enemy;

public class CreateOneRandomEnemyCommand implements IActionCommand{

    @Override
    public void execute() {
        AbstractEnemy enemy = new Enemy();
        EvilClass.getVaultEnemies().addEnemy(enemy);
    }
}