package com.carbonara.game.gui.managers;

import com.carbonara.game.gui.pages.MainMenuPage;
import com.carbonara.game.gui.pages.SettingsPage;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

public class MainMenuPageManager extends BaseAppState {
    @Override
    protected void initialize(Application application) {

        GameSettings.cameraUnlock(false);
        GameSettings.cursorVisible(true);

        application.getStateManager().attach(new MainMenuPage());
        application.getStateManager().attach(new SettingsPage());

        application.getStateManager().getState(MainMenuPage.class).setEnabled(false);
        application.getStateManager().getState(SettingsPage.class).setEnabled(false);

        application.getStateManager().getState(MainMenuPage.class).setEnabled(true);
    }

    @Override
    protected void cleanup(Application application) {
        application.getStateManager().detach(application.getStateManager().getState(MainMenuPage.class));
        application.getStateManager().detach(application.getStateManager().getState(SettingsPage.class));
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
