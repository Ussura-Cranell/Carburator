package com.carbonara.game.main;

import com.carbonara.game.settings.GameSettings;
import com.jme3.app.SimpleApplication;

import java.util.logging.Logger;

public class GameLauncher extends SimpleApplication {

    static {
        Logger.getLogger(GameLauncher.class.getName());
    }

    public static void main(String[] args) {

        GameLauncher gameLauncher = new GameLauncher();
        GameSettings.initialize(gameLauncher);

        gameLauncher.setSettings(GameSettings.getAppSettings());
        gameLauncher.setShowSettings(false);
        gameLauncher.setDisplayStatView(false);
        gameLauncher.setDisplayFps(false);

        gameLauncher.start();
    }
    @Override
    public void simpleInitApp() {

        GameSettings.initializeGUIGlobals();

        stateManager.attach(new IntroPlayer());
    }
}
