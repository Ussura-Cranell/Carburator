package com.carbonara.Main;

import com.carbonara.GUI.MainMenu.PageManager;
import com.carbonara.Settings.MainSettings;
import com.jme3.app.SimpleApplication;

import java.util.logging.Logger;

public class RunNameOfGame extends SimpleApplication {

    private static final Logger logger = Logger.getLogger(RunNameOfGame.class.getName());

    public static void main(String[] args) {

        RunNameOfGame runNameOfGame = new RunNameOfGame();
        MainSettings.initialize(runNameOfGame);

        runNameOfGame.setSettings(MainSettings.getAppSettings());
        runNameOfGame.setShowSettings(false);
        runNameOfGame.setDisplayStatView(false);
        runNameOfGame.setDisplayFps(false);

        runNameOfGame.start();


    }
    @Override
    public void simpleInitApp() {

        MainSettings.initializeGUIGlobals();

        boolean skipIntro = false;
        if (skipIntro) stateManager.attach(new PageManager()); else stateManager.attach(new PlayIntro());
    }
}
