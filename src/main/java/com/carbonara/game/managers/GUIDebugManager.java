package com.carbonara.game.managers;

import com.carbonara.game.settings.GameSettings;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;

public class GUIDebugManager {

    private static final Vector3f INIT_PREFERRED_SIZE = new Vector3f(300.0f, 150, 1.0f);
    private static final Vector3f INIT_PANEL_TOP_LEFT =
            new Vector3f(0, GameSettings.getAppSettings().getHeight(), 1);
    private static final Vector3f INIT_PANEL_TOP_RIGHT =
            new Vector3f(GameSettings.getAppSettings().getWidth() - INIT_PREFERRED_SIZE.getX(),
                    GameSettings.getAppSettings().getHeight(), 1);
    private static final Vector3f INIT_PANEL_BOTTOM_LEFT =
            new Vector3f(0.0f, INIT_PREFERRED_SIZE.getY(), 1);
    private static final Vector3f PANEL_BOTTOM_RIGHT =
            new Vector3f(GameSettings.getAppSettings().getWidth() - INIT_PREFERRED_SIZE.getX(),
                    INIT_PREFERRED_SIZE.getY(), 1);

    private static Container container;

    public static void init(Node guiNode) {
        GUIDebugManager.container = new Container();
        container.setPreferredSize(GUIDebugManager.INIT_PREFERRED_SIZE);
        container.setLocalTranslation(GUIDebugManager.INIT_PANEL_TOP_RIGHT);

        guiNode.attachChild(container);
    }

    public static Container getContainer() {
        return container;
    }
}
