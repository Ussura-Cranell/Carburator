package com.carbonara.game.managers;

import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BulletAppStateManager {
    private static final Logger logger = Logger.getLogger(BulletAppStateManager.class.getName());
    private static final Map<Node, BulletAppState> bulletAppStates = new HashMap<>();

    public static void registerBulletAppState(Node node, BulletAppState bulletAppState) {
        bulletAppStates.put(node, bulletAppState);
    }

    public static BulletAppState getBulletAppState(Node node) {
        BulletAppState bulletAppState = bulletAppStates.get(node);
        if (bulletAppState == null)
            logger.warning("The node \"%s\" does not have a physical BulletAppSatte".formatted(node.getName()));
        return bulletAppState;
    }
}