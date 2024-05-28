package com.carbonara.game.logic;

import com.carbonara.game.gui.menu.pages.LoadingPage;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.managers.NewPauseGameManager;
import com.carbonara.game.managers.ServiceLocatorManagers;
import com.carbonara.game.scene.OuterSpaceScene;
import com.carbonara.game.scene.SpaceshipScene;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.scene.Node;

import java.util.logging.Logger;

public class NewSceneGuardian extends BaseAppState {

    /*
    * Класс в котором хранятся все игровые объекты
    * Возможно должна предпологаться какая-то логика загрузки
    * */

    private static Logger logger = Logger.getLogger(NewSceneGuardian.class.getName());
    private NewPauseGameManager newPauseGameManager;                    // менеджер для паузы игры
    private SpaceshipScene spaceshipScene;                              // пространство игрока
    private Node spaceshipSceneNode;
    private final Node outerSpaceScene = OuterSpaceScene.createScene(); // простанство кораблей

    private SimpleApplication app;

    @Override
    protected void initialize(Application application) {

        logger.info("initialize");

        GUIManager.setCursorVisible(false);
        CameraManager.cameraUnlock(true);

        app = (SimpleApplication) application;

        newPauseGameManager = ServiceLocatorManagers.getNewPauseGameManager();
        newPauseGameManager.initialize();

        // добавление физики
        spaceshipScene = new SpaceshipScene();
        spaceshipScene.initialize();

        spaceshipSceneNode = spaceshipScene.createScene();

        app.getRootNode().attachChild(spaceshipSceneNode);
        app.getRootNode().attachChild(outerSpaceScene);

        app.getStateManager().detach(app.getStateManager().getState(LoadingPage.class));
    }

    @Override
    protected void cleanup(Application application) {

        logger.info("cleanup");

        app.getRootNode().detachChild(spaceshipSceneNode);
        app.getRootNode().detachChild(outerSpaceScene);

        newPauseGameManager.cleanup();

        // удаление физики
        spaceshipScene.cleanup();
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {
        newPauseGameManager.update(tpf);
    }
}

