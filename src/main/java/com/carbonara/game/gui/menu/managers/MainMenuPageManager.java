package com.carbonara.game.gui.menu.managers;

import com.carbonara.game.gui.menu.pages.MainMenuPage;
import com.carbonara.game.gui.menu.pages.NewGamePage;
import com.carbonara.game.gui.menu.pages.SettingsPage;
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
    private Container panel;
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

        ((SimpleApplication) application).getGuiNode().detachChild(this.panel);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    private void windowResolutionTEst(Application app, boolean enable){
        if (enable) {


            this.panel = new Container();
            this.panel.setPreferredSize(new Vector3f(100, 40, 0));
            this.panel.setLocalTranslation(0,
                    GameSettings.getAppSettings().getWindowHeight(), 0);

            Label label = new Label("click update");
            label.setFontSize(10.0f);
            this.panel.addChild(label);

            Button button = new Button("update");
            button.setFontSize(10.0f);
            button.addClickCommands(button1 -> {
                label.setText("Size: " + GameSettings.getAppSettings().getWindowWidth() + "x" +
                        GameSettings.getAppSettings().getHeight());
            });
            this.panel.addChild(button);

            ((SimpleApplication) app).getGuiNode().attachChild(this.panel);
        }
    }
}
