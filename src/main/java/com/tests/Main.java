package com.tests;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;

public class Main extends SimpleApplication {

    public final static String TITLE = "TESTING";
    public final static int RESOLUTION_WIDTH = 1200;
    public final static int RESOLUTION_HEIGHT = 800;

    private GUIManager guiManager;

    public static void main(String[] args) {
        Main app = new Main();
        app.setSettings(windowSetting());
        app.start();

    }

    @Override
    public void simpleInitApp() {
        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        // инициализация guimanager
        guiManager = new GUIManager(guiNode);
        guiManager.setGUIPage(Mode.MAIN_MENU);

        System.out.println(guiManager.getGuiPage().name());
    }

    private static AppSettings windowSetting(){
        AppSettings defaultSetting = new AppSettings(true);
        defaultSetting.setTitle(TITLE);
        defaultSetting.setVSync(true);
        defaultSetting.setResolution(RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
        defaultSetting.setFrequency(60);
        return defaultSetting;
    }

}
