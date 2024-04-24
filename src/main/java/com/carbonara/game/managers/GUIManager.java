package com.carbonara.game.managers;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

public class GUIManager {

    public static final boolean INIT_CURSOR_VISIBLE = false; // виден ли курсор изначально
    public static final boolean INIT_CURSOR_CURSOR_EVENTS = false; // обработка событий курсора

    private static SimpleApplication app;

    public GUIManager(SimpleApplication app){
        GUIManager.app = app;
        initializeGUIGlobals();
        settingInitCursor(app.getInputManager());
    }

    private void settingInitCursor(InputManager inputManager){
        inputManager.setCursorVisible(GUIManager.INIT_CURSOR_VISIBLE);
    }

    private void initializeGUIGlobals(){
        GuiGlobals.initialize(app);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");
        GuiGlobals.getInstance().setCursorEventsEnabled(GUIManager.INIT_CURSOR_CURSOR_EVENTS);
    }

    public static void setCursorVisible(boolean value){
        app.getInputManager().setCursorVisible(value);
    }
}