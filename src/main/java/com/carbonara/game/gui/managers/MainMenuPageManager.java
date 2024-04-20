package com.carbonara.game.gui.managers;

import com.carbonara.game.gui.pages.MainMenuPage;
import com.carbonara.game.gui.pages.NewGamePage;
import com.carbonara.game.gui.pages.SettingsPage;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;


public class MainMenuPageManager extends BaseAppState {
    @Override
    protected void initialize(Application application) {

        GUIManager.cameraUnlock(false);
        GUIManager.cursorVisible(true);

        application.getStateManager().attach(new MainMenuPage());
        application.getStateManager().attach(new NewGamePage());
        application.getStateManager().attach(new SettingsPage());

        application.getStateManager().getState(MainMenuPage.class).setEnabled(false);
        application.getStateManager().getState(NewGamePage.class).setEnabled(false);
        application.getStateManager().getState(SettingsPage.class).setEnabled(false);

        application.getStateManager().getState(MainMenuPage.class).setEnabled(true);

        // ###
        windowResolutionTEst(application, true);
    }

    @Override
    protected void cleanup(Application application) {
        application.getStateManager().detach(application.getStateManager().getState(MainMenuPage.class));
        application.getStateManager().detach(application.getStateManager().getState(NewGamePage.class));
        application.getStateManager().detach(application.getStateManager().getState(SettingsPage.class));
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    private void windowResolutionTEst(Application app, boolean enable){
        if (enable) {
            Container panel;

            panel = new Container();
            panel.setPreferredSize(new Vector3f(100, 40, 0));
            panel.setLocalTranslation(0,
                    GameSettings.getAppSettings().getWindowHeight(), 0);

            Label label = new Label("click update");
            label.setFontSize(10.0f);
            panel.addChild(label);

            Button button = new Button("update");
            button.setFontSize(10.0f);
            button.addClickCommands(button1 -> {
                label.setText("Size: " + GameSettings.getAppSettings().getWindowWidth() + "x" +
                        GameSettings.getAppSettings().getHeight());
            });
            panel.addChild(button);

            ((SimpleApplication) app).getGuiNode().attachChild(panel);
        }
    }
}
