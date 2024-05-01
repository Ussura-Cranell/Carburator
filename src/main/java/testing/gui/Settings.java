package testing.gui;

import com.jme3.system.AppSettings;

public class Settings  {
    private static final String TITLE = "testing#270424";
    private static final int RESOLUTION_WIDTH = 700;
    private static final int RESOLUTION_HEIGHT = 400;
    private static final int FREQUENCY = 60; // кадры в секунду

    private static final AppSettings appSettings = new AppSettings(true);

    static {
        appSettings.setTitle(TITLE);
        appSettings.setResolution(RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
        appSettings.setFrequency(FREQUENCY);
    }

    public static AppSettings getAppSettings(){
        return appSettings;
    }
}
