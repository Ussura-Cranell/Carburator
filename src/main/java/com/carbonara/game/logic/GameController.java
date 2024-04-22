package com.carbonara.game.logic;

import com.carbonara.game.managers.BulletManagerTest;
import com.carbonara.game.scene.Player;
import com.carbonara.game.scene.TestRoomScene;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.bullet.BulletAppState;

import java.util.logging.Logger;

public class GameController extends BaseAppState {
    private static final Logger logger = Logger.getLogger(GameController.class.getName());

    @Override
    protected void initialize(Application application) {
        // инициализации физики
        BulletManagerTest.setBulletAppState(new BulletAppState());
        // BulletManagerTest.getBulletAppState().setDebugEnabled(true);

        // включение физики
        application.getStateManager().attach(BulletManagerTest.getBulletAppState());

        // загрузка сцены
        TestRoomScene testRoomScene = new TestRoomScene(((SimpleApplication)application));
        testRoomScene.loadTestRoom();
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {
        Player player = new Player((Node)((SimpleApplication)getApplication()).getRootNode().getChild("grid"),
                new Vector3f(14, 14, 14));
        getApplication().getStateManager().attach(player);
    }

    @Override
    protected void onDisable() {

    }
}
