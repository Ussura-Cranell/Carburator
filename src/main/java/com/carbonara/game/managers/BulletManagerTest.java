package com.carbonara.game.managers;

import com.jme3.bullet.BulletAppState;

public class BulletManagerTest {
    /*
    * Необходимо создавать разные BulletManagerTest для разных физических пространств
    * */
    private static BulletAppState bulletAppState;

    public static BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public static void setBulletAppState(BulletAppState bulletManager) {
        BulletManagerTest.bulletAppState = bulletManager;
    }
}
