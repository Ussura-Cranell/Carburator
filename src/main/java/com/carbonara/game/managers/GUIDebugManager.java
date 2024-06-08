package com.carbonara.game.managers;

import com.carbonara.game.settings.GameSettings;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;

import java.util.Optional;

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

    private static Optional<Container> container = Optional.empty();
    private static boolean isInitialize = false;

    public static void init(Node guiNode) {
        container = Optional.of(new Container());

        container.get().setPreferredSize(GUIDebugManager.INIT_PREFERRED_SIZE);
        container.get().setLocalTranslation(GUIDebugManager.INIT_PANEL_TOP_RIGHT);
        container.get().setAlpha(0.0f);

        guiNode.attachChild(container.get());



        isInitialize = true;
    }

    public static Optional<Container> getContainer() {
        return container;
    }
    public static void setEnable(boolean value){
        container.ifPresent(container -> {
            if (value) container.setAlpha(1.0f);
            else container.setAlpha(0.0f);
        });
    }

    public static void clearContainer(){
        container.ifPresent(Container::detachAllChildren);
    }
}
