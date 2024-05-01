package com.carbonara.game.logic;

import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

public class CheckMemory extends BaseAppState {

    private static final Vector3f INIT_PREFERRED_SIZE = new Vector3f(150.0f, 90, 1.0f);
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
    private static Label debugMemoryInfoLabel;
    private static Container debugMemoryInfoContainer;

    @Override
    protected void initialize(Application application) {
        CheckMemory.container = new Container();
        container.setPreferredSize(CheckMemory.INIT_PREFERRED_SIZE);
        container.setLocalTranslation(CheckMemory.INIT_PANEL_TOP_LEFT);
        container.setAlpha(0.0f);

        GameLauncher.getApp().getGuiNode().attachChild(container);

        debugMemoryInfoContainer = new Container();
        debugMemoryInfoContainer.setName("debugMemoryInfoContainer");
        debugMemoryInfoLabel = new Label("debugMemoryInfoLabel");
        debugMemoryInfoLabel.setTextHAlignment(HAlignment.Center);
        debugMemoryInfoLabel.setTextVAlignment(VAlignment.Center);

        debugMemoryInfoContainer.addChild(debugMemoryInfoLabel);
        CheckMemory.container.addChild(debugMemoryInfoContainer);
    }

    @Override
    protected void cleanup(Application application) {
        GameLauncher.getApp().getGuiNode().detachChild(container);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {

        long usedBytes = Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();

        debugMemoryInfoLabel.setText(
                """
                Memory Usage:
                %-10.3f Gb
                %-10.3f Mb
                %-10.0f Kb
                """.formatted(
                        (float) usedBytes / Math.pow(1024, 3),
                        (float) usedBytes / Math.pow(1024, 2),
                        (float) usedBytes / Math.pow(1024, 1)));

    }
}
