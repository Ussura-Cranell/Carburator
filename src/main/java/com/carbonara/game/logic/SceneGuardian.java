package com.carbonara.game.logic;

import com.carbonara.game.gui.menu.pages.LoadingPage;
import com.carbonara.game.managers.*;
import com.carbonara.game.object.gameobjects.categories.player.controls.PlayerStateManager;
import com.carbonara.game.scene.DebugRoom;
import com.carbonara.game.scene.OuterSpaceCreate;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

import java.util.logging.Logger;

@Deprecated
public class SceneGuardian extends BaseAppState {
    Logger logger = Logger.getLogger(SceneGuardian.class.getName());
    private Node scene; // сцена с игроком
    private Node outerSpace; // сцена с кораблями
    private PlayerStateManager playerStateManager;
    private PauseGameManager pauseGameManager;

    @Override
    protected void initialize(Application application) {

        // добавляем обработчика паузы
        pauseGameManager = new PauseGameManager();
        application.getStateManager().attach(pauseGameManager);

        // debug room
            application.getCamera().lookAtDirection(Vector3f.UNIT_Z, Vector3f.UNIT_Y);

            debugLabelCamera(true);

            // создаём сцену и передаём ей узел к которому она будет прикрепляться
            DebugRoom debugRoom = new DebugRoom(new Node("space"));
            this.scene = debugRoom.getDebugSpace();

            // прикрепляем узел к сцене
            ((SimpleApplication)application).getRootNode().attachChild(this.scene);

            // создаём персонажа на этой сцене
            playerStateManager = new PlayerStateManager(this.scene);
            application.getStateManager().attach(playerStateManager);

            // включаем панель отладки после общей загрузки
            GUIDebugManager.setEnable(true);

        // outer space
            OuterSpaceCreate outerSpaceCreate = new OuterSpaceCreate(new Node("outerSpace"));
            this.outerSpace = outerSpaceCreate.getOuterSpace();

            ((SimpleApplication)application).getRootNode().attachChild(this.outerSpace);
    }

    @Override
    protected void cleanup(Application application) {
        // открепляем обработчик паузы
        // application.getStateManager().getState(PauseGameManager.class).cleanup();
        // application.getStateManager().detach(application.getStateManager().getState(PauseGameManager.class));
        application.getStateManager().detach(pauseGameManager);


        // удялем игрока
        application.getStateManager().detach(playerStateManager);

        // открепляем нашу сцену от главного узла
        // this.scene.detachAllChildren();
        ((SimpleApplication)application).getRootNode().detachChild(this.scene);
        ((SimpleApplication)application).getRootNode().detachChild(this.outerSpace);

        // очищаем отладчик
        GUIDebugManager.clearContainer();

        // другой подход к удалению физики (удаляем абсолютно всю физику в приложении)
        for (BulletAppState bulletAppState: BulletAppStateManager.getAllBulletAppState()){
            application.getStateManager().detach(bulletAppState);
        }
        BulletAppStateManager.clearAllBulletAppState();
    }

    @Override
    protected void onEnable() {
        logger.info("работает");
        // включение всей физики
        BulletAppStateManager.enableAllBulletAppState(true);
        // камера может вертеться
        CameraManager.cameraUnlock(true);
        // курсор виден
        GUIManager.setCursorVisible(false);

    }

    @Override
    protected void onDisable() {

        logger.info("на паузе");
        // когда игра на паузе должно прекратится выполнение многих вещей

        /*
        физика, игрок, камера,
        */

        // отключение всей физики
        BulletAppStateManager.enableAllBulletAppState(false);
        // камере нельзя вертеться
        CameraManager.cameraUnlock(false);
        // курсор скрыт
        GUIManager.setCursorVisible(true);


    }

    // debug
    private Container debugLabelCameraContainer;
    private Label debugLabelCameraContainerLabel;
    private boolean flag_debugLabelCamera;
    private void debugLabelCamera(boolean flag_debugLabelCamera){

        this.flag_debugLabelCamera = flag_debugLabelCamera;

        if (flag_debugLabelCamera) {
            debugLabelCameraContainer = new Container();
            debugLabelCameraContainer.setName("debugLabelCamera");
            debugLabelCameraContainerLabel = new Label("debugLabelCamera");
            debugLabelCameraContainerLabel.setTextHAlignment(HAlignment.Center);
            debugLabelCameraContainerLabel.setTextVAlignment(VAlignment.Center);

            debugLabelCameraContainer.addChild(debugLabelCameraContainerLabel);
            // GUIDebugManager.getContainer().addChild(debugLabelCameraContainer);
        }
    }

    float timer = 0;
    boolean flag_loading = true;

    @Override
    public void update(float tpf) {

        // debug loading page
        if (flag_loading) {
            timer += tpf;
            if (timer >= 1) {
                flag_loading = false;
                LoadingPage loadingPage = getApplication().getStateManager().getState(LoadingPage.class);
                if (loadingPage != null) loadingPage.changeStatus();
                else logger.warning("No \"loading\" page found");
            }
        }

        if (flag_debugLabelCamera){

            debugLabelCameraContainerLabel.setText(
                    """
                    Camera position:
                    (%.3f;%.3f;%.3f)
                    Camera direction:
                    (%.3f;%.3f;%.3f)
                    """.formatted(
                            getApplication().getCamera().getLocation().getX(),
                            getApplication().getCamera().getLocation().getY(),
                            getApplication().getCamera().getLocation().getZ(),

                            getApplication().getCamera().getDirection().getX(),
                            getApplication().getCamera().getDirection().getY(),
                            getApplication().getCamera().getDirection().getZ()));
        }
    }


}
