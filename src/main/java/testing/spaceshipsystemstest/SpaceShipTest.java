package testing.spaceshipsystemstest;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.managers.GUIManager;
import com.jme3.app.SimpleApplication;

public class SpaceShipTest extends SimpleApplication {
    GUIManager guiManager;
    CameraManager cameraManager;

    public static void main(String[] args) {
        SpaceShipTest app = new SpaceShipTest();
        app.start();
        GlobalSimpleApplication.setApp(app);
    }

    @Override
    public void simpleInitApp() {
        guiManager = new GUIManager(this);
        cameraManager = new CameraManager(this);

        GUIDebugManager.init(guiNode);

        stateManager.attach(new CreateScene());
    }
}
