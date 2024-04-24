package com.carbonara.game.logic;

import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIDebugManager;
import com.carbonara.game.object.controls.player.PlayerStateManager;
import com.carbonara.game.scene.DebugRoom;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.scene.Node;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;


public class SceneGuardian extends BaseAppState {

    private Node scene;

    @Override
    protected void initialize(Application application) {

        // debug
        CameraManager.cameraUnlock(true);
        application.getInputManager().setCursorVisible(false);
        debugLabelCamera(true);
        // debug

        // создаём сцену и передаём ей узел к которому она будет прикрепляться
        DebugRoom debugRoom = new DebugRoom(new Node("space"));
        this.scene = debugRoom.getDebugSpace();

        // прикрепляем узел к сцене
        ((SimpleApplication)application).getRootNode().attachChild(this.scene);

        // создаём персонажа на этой сцене
        PlayerStateManager playerStateManager = new PlayerStateManager(this.scene);
        application.getStateManager().attach(playerStateManager);

    }

    @Override
    protected void cleanup(Application application) {

        // открепляем обработку физики сцены, так как у этой сцены была физика
        application.getStateManager().detach(this.scene.getUserData(BulletAppState.class.toString()));
        // открепляем нашу сцену от главного узла
        ((SimpleApplication)application).getRootNode().detachChild(this.scene);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

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
            GUIDebugManager.getContainer().addChild(debugLabelCameraContainer);
        }
    }

    @Override
    public void update(float tpf) {
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
