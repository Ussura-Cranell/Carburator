package com.carbonara.game.managers;

import com.jme3.app.SimpleApplication;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

public class GUIManager {

    private static SimpleApplication app;

    public GUIManager(SimpleApplication app){
        GUIManager.app = app;
        initializeGUIGlobals();
    }

    private void initializeGUIGlobals(){
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