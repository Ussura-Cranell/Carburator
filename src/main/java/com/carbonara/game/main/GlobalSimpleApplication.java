package com.carbonara.game.main;

import com.jme3.app.SimpleApplication;

public class GlobalSimpleApplication {
    private static SimpleApplication app;

    public static SimpleApplication getApp() {
        return app;
    }

    public static void setApp(SimpleApplication app) {
        GlobalSimpleApplication.app = app;
    }
}
