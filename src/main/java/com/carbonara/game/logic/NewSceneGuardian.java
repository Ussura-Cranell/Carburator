package com.carbonara.game.logic;

import com.carbonara.game.gui.menu.pages.LoadingPage;
import com.carbonara.game.managers.*;
import com.carbonara.game.scene.OuterSpaceScene;
import com.carbonara.game.scene.SpaceshipScene;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.audio.AudioNode;
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

        // менеджер для отображения всего подряд
        GUIDebugManager.init(app.getGuiNode());
        // setEnable(false);

        newPauseGameManager = ServiceLocatorManagers.getNewPauseGameManager();
        newPauseGameManager.initialize();

        // добавление физики
        spaceshipScene = new SpaceshipScene();
        spaceshipScene.initialize();

        spaceshipSceneNode = spaceshipScene.createScene();

        app.getRootNode().attachChild(spaceshipSceneNode);
        app.getRootNode().attachChild(outerSpaceScene);

        app.getStateManager().detach(app.getStateManager().getState(LoadingPage.class));

        SoundManager.get("main_environment").ifPresent(AudioNode::play);
    }

    @Override
    protected void cleanup(Application application) {

        SoundManager.get("main_environment").ifPresent(AudioNode::stop);

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

