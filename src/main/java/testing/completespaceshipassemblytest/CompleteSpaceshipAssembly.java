package testing.completespaceshipassemblytest;

import com.carbonara.game.logic.CheckMemory;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.SimpleApplication;

public class CompleteSpaceshipAssembly extends SimpleApplication {
        GUIManager guiManager;
        CameraManager cameraManager;
        private static SimpleApplication app;

        public static void main(String[] args) {
            CompleteSpaceshipAssembly guiSpaceshipTest = new CompleteSpaceshipAssembly();
            setManualSetting(guiSpaceshipTest, false);
            enableStatistics(guiSpaceshipTest, true);
            guiSpaceshipTest.start();

            app = guiSpaceshipTest;
            GlobalSimpleApplication.setApp(app);
        }
        @Override
        public void simpleInitApp() {

            guiManager = new GUIManager(this);
            cameraManager = new CameraManager(this);

            GUIDebugManager.init(getGuiNode());

            getStateManager().attach(new CSASGameGuardian());
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