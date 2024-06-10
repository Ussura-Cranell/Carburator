package com.carbonara.game.logic;

import com.carbonara.game.gui.menu.pages.LoadingPage;
import com.carbonara.game.gui.spaceship.systems.commnads.CreateOneRandomEnemyCommand;
import com.carbonara.game.managers.*;
import com.carbonara.game.object.other.EvilClass;
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
    private OuterSpaceScene outerSpaceScene;                            // пространство корабля
    private Node spaceshipSceneNode;
    private Node outerSpaceSceneNode;

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

        EvilClass.initialize(); // логика обработки врагов

        // ---
        spaceshipScene = new SpaceshipScene();
        spaceshipScene.initialize();
        spaceshipSceneNode = spaceshipScene.createScene();

        // ---
        outerSpaceScene = new OuterSpaceScene();
        outerSpaceScene.initialize();
        outerSpaceSceneNode = outerSpaceScene.createScene();

        app.getRootNode().attachChild(spaceshipSceneNode);
        app.getRootNode().attachChild(outerSpaceSceneNode);

        app.getStateManager().detach(app.getStateManager().getState(LoadingPage.class));

        SoundManager.get("main_environment").ifPresent(AudioNode::play);

        (new CreateOneRandomEnemyCommand()).execute();
        (new CreateOneRandomEnemyCommand()).execute();
        (new CreateOneRandomEnemyCommand()).execute();
    }

    @Override
    protected void cleanup(Application application) {

        SoundManager.get("main_environment").ifPresent(AudioNode::stop);

        logger.info("cleanup");

        app.getRootNode().detachChild(spaceshipSceneNode);
        app.getRootNode().detachChild(outerSpaceSceneNode);

        newPauseGameManager.cleanup();

        // удаление физики
        spaceshipScene.cleanup();
        outerSpaceScene.cleanup();
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

        // логика обработки врагов
        EvilClass.getVaultEnemies().update(tpf);
    }
}

