package com.carbonara.game.main;

import com.carbonara.game.gui.menu.managers.MainMenuPageManager;
import com.carbonara.game.logic.CheckMemory;
import com.carbonara.game.logic.NewSceneGuardian;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.settings.GameSettings;
import com.carbonara.game.tools.audio.AudioInitializer;
import com.jme3.app.SimpleApplication;

public class GameLauncher extends SimpleApplication {
    GUIManager guiManager;
    CameraManager cameraManager;
    private static SimpleApplication app;

    public static void main(String[] args) {
        GameLauncher gameLauncher = new GameLauncher();
        setManualSetting(gameLauncher, false);
        enableStatistics(gameLauncher, false);
        gameLauncher.start();

        GameLauncher.app = gameLauncher;
        GlobalSimpleApplication.setApp(app);
    }
    @Override
    public void simpleInitApp() {
        // инициализация звуков
        (new AudioInitializer()).initialize(assetManager);

        guiManager = new GUIManager(this);
        cameraManager = new CameraManager(this);

        // stateManager.attach(new MainMenuPageManager());
        // stateManager.attach(new NewSceneGuardian());
        // stateManager.attach(new NewGamePage());
        stateManager.attach(new IntroPlayer());
    }

    private static void setManualSetting(SimpleApplication app, boolean value){
        app.setSettings(GameSettings.getAppSettings());
        app.setShowSettings(value);
    }

    private static void enableStatistics(SimpleApplication app, boolean value){
        app.setDisplayStatView(value);
        app.setDisplayFps(value);

        // debug
        // отображение потребления памяти приложением
        app.getStateManager().attach(new CheckMemory());
    }
    public static SimpleApplication getApp() {
        return GameLauncher.app;
    }
}
