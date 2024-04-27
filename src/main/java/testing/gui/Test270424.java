package testing.gui;

import com.jme3.app.SimpleApplication;

public class Test270424 extends SimpleApplication {
    public static void main(String[] args) {
        Test270424 app = new Test270424();
        app.setShowSettings(false);
        app.setSettings(Settings.getAppSettings());
        app.start();
    }
    @Override
    public void simpleInitApp() {

    }
}
