package com.carbonara.game.object.other;

import com.carbonara.game.object.other.enemy.abstracts.VaultEnemies;

import java.util.logging.Logger;

/*
    Это ServiceLocator для всех классов связанных с Enemy
 */

public class EvilClass {
    private static final Logger logger = Logger.getLogger(EvilClass.class.getName());
    private static VaultEnemies vaultEnemies;

    public static void initialize(){
        vaultEnemies = new VaultEnemies();
    }

    public static VaultEnemies getVaultEnemies() {
        if (vaultEnemies == null) logger.warning("The class is not initialized!");
        return vaultEnemies;
    }
}
