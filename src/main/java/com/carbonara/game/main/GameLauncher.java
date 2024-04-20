package com.carbonara.game.main;

import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.SimpleApplication;

import java.util.logging.Logger;

public class GameLauncher extends SimpleApplication {

    static {
        Logger.getLogger(GameLauncher.class.getName());
    }

    GUIManager guiManager;

    public static void main(String[] args) {
        GameLauncher gameLauncher = new GameLauncher();
        setManualSetting(gameLauncher, true);
        enableStatistics(gameLauncher, false);
        gameLauncher.start();
    }
    @Override
    public void simpleInitApp() {

        guiManager = new GUIManager(this);

        // code
        stateManager.attach(new IntroPlayer());

        // debug
        //stateManager.attach(new LoadingPage());
    }

    private static void setManualSetting(SimpleApplication app ,boolean value){
        app.setSettings(GameSettings.getAppSettings());
        app.setShowSettings(value);
    }

    private static void enableStatistics(SimpleApplication app ,boolean value){
        app.setDisplayStatView(value);
        app.setDisplayFps(value);
    }
}
