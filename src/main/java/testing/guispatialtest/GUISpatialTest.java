package testing.guispatialtest;

import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.managers.GUIManager;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

public class GUISpatialTest extends SimpleApplication {

    GUIManager guiManager;
    CameraManager cameraManager;

    public static void main(String[] args) {
        GUISpatialTest app = new GUISpatialTest();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        guiManager = new GUIManager(this);
        cameraManager = new CameraManager(this);

        GUIDebugManager.init(guiNode);

        stateManager.attach(new CreateScene());
    }
}
