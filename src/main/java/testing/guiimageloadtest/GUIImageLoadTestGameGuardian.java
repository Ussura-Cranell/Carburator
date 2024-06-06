package testing.guiimageloadtest;

import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceShip;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;
import testing.guispaceshiptesting.GUISpaceshipScene;

import java.util.logging.Logger;

public class GUIImageLoadTestGameGuardian extends BaseAppState {

    private static final Logger logger = Logger.getLogger(com.carbonara.game.logic.NewSceneGuardian.class.getName());
    private final Node outerSpaceScene = GUISpaceshipScene.createScene();
    private final Node screenNodes = new Node("screenNodes");
    private SimpleApplication app;
    private AbstractSpaceShip spaceship;

    @Override
    protected void initialize(Application application) {

        logger.info("initialize");

        GUIManager.setCursorVisible(true);
        CameraManager.cameraUnlock(false);

        app = (SimpleApplication) application;

        TestingImageGUI testingImageGUI = new TestingImageGUI(app);
        testingImageGUI.initialize();
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

}
