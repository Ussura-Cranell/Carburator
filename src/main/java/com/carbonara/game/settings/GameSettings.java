package com.carbonara.game.settings;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.*;
import com.simsilica.lemur.style.BaseStyles;

public class GameSettings {

    private static SimpleApplication app;
    private static final String TITLE = "NameOfGame";
    private static final int RESOLUTION_WIDTH = 1200;
    private static final int RESOLUTION_HEIGHT = 720;
    private static final int FREQUENCY = 60;

    private static final AppSettings appSettings = new AppSettings(true);

    static {
        appSettings.setTitle(TITLE);
        appSettings.setResolution(RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
        appSettings.setFrequency(FREQUENCY);
    }

    public static void initialize(SimpleApplication app){
        GameSettings.app = app;
    }

    public static AppSettings getAppSettings(){
        return appSettings;
    }

    public static void initializeGUIGlobals(){
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
    }

    public static void cameraUnlock(boolean value){
        app.getFlyByCamera().setEnabled(value);
    }

    public static void cursorVisible(boolean value){
        app.getInputManager().setCursorVisible(value);
    }
}
