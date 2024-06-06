package testing.guiimageloadtest;

import com.carbonara.game.logic.CheckMemory;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.SimpleApplication;
import com.jme3.math.ColorRGBA;

public class GUIImageLoadTest extends SimpleApplication {
    GUIManager guiManager;
    CameraManager cameraManager;
    private static SimpleApplication app;

    public static void main(String[] args) {
        GUIImageLoadTest guiImageLoadTest = new GUIImageLoadTest();
        setManualSetting(guiImageLoadTest, false);
        enableStatistics(guiImageLoadTest, true);
        guiImageLoadTest.start();

        app = guiImageLoadTest;
        GlobalSimpleApplication.setApp(app);
    }
    @Override
    public void simpleInitApp() {
        app.getViewPort().setBackgroundColor(ColorRGBA.Magenta);

        guiManager = new GUIManager(this);
        cameraManager = new CameraManager(this);

        GUIDebugManager.init(getGuiNode());

        getStateManager().attach(new GUIImageLoadTestGameGuardian());
    }

    private static void setManualSetting(SimpleApplication app, boolean value){
        app.setSettings(GameSettings.getAppSettings());
        app.setShowSettings(value);
    }

    private static void enableStatistics(SimpleApplication app, boolean value){
        app.setDisplayStatView(value);
        app.setDisplayFps(value);

        app.getStateManager().attach(new CheckMemory());
    }
    public static SimpleApplication getApp() {
        return app;
    }
}
