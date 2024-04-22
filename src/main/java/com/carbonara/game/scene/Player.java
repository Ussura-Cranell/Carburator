package com.carbonara.game.scene;

import com.carbonara.game.managers.BulletManagerTest;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;
import java.util.logging.Logger;

public class Player extends BaseAppState {
    private static final Logger logger = Logger.getLogger(Player.class.getName());

    CharacterControl player;
    Geometry playerBoxGeometry;
    Vector3f initPhysicsLocation;
    Node scene;

    Label labelPlayerPosition;
    Label labelCameraDirection;
    Label labelCameraPosition;

    Camera camera2;

    public Player(Node scene, Vector3f initPhysicsLocation){
        this.scene = scene;
        this.initPhysicsLocation = initPhysicsLocation;
    }

    @Override
    protected void initialize(Application application) {

        camera2 = application.getCamera().clone();
        camera2.setViewPort(0f, 0.3f, 0.7f, 1f);
        camera2.setLocation(new Vector3f(0, 15f, 14f));
        ViewPort viewPort2 = application.getRenderManager().createMainView("PiP", camera2);
        viewPort2.setClearFlags(true, true, true);
        viewPort2.attachScene(((SimpleApplication)application).getRootNode());
        viewPort2.setBackgroundColor(ColorRGBA.White);
        // viewPort2.

        // BulletManagerTest.getBulletAppState().setDebugEnabled(true);


        float radius = 1;
        float height = 3;

        CapsuleCollisionShape playerCollisionShape = new CapsuleCollisionShape(radius, height);
        player = new CharacterControl(playerCollisionShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setGravity(30);

        player.setPhysicsLocation(initPhysicsLocation);
        BulletManagerTest.getBulletAppState().getPhysicsSpace().add(player);

        // отображение игрока
        int axisSamples = 10;
        int radialSamples = 10;
        radius = 0.65f;
        Box playerCylinderShape = new Box(radius, height-radius*1.5f, radius);
        playerBoxGeometry = new Geometry("A shape", playerCylinderShape);

        Material mat = new Material(application.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        playerBoxGeometry.setMaterial(mat);
        ((SimpleApplication)application).getRootNode().attachChild(playerBoxGeometry);

        // playerBoxGeometry.setCullHint(Spatial.CullHint.Always);
        playerBoxGeometry.addControl(player);

        player.setPhysicsLocation(new Vector3f(14, 10, 15));

        Label label;

        float panelScaleX = 175;
        float panelScaleY = 150;

        Container playerInfoElements = new Container();
        playerInfoElements.setPreferredSize(new Vector3f(panelScaleX, panelScaleY, 1));
        playerInfoElements.setLocalTranslation(
                GameSettings.getAppSettings().getWidth() - panelScaleX,
                GameSettings.getAppSettings().getHeight(), 0);

        Container playerInfoName = new Container();
        playerInfoName.setPreferredSize(new Vector3f(panelScaleX, 25, 1));
        label = new Label("View data:");
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        playerInfoName.addChild(label);
        playerInfoElements.addChild(playerInfoName);

        labelPlayerPosition = new Label("player position");
        labelPlayerPosition.setTextVAlignment(VAlignment.Center);
        labelPlayerPosition.setTextHAlignment(HAlignment.Center);
        playerInfoElements.addChild(labelPlayerPosition);

        labelCameraDirection = new Label("camera direction");
        labelCameraDirection.setTextVAlignment(VAlignment.Center);
        labelCameraDirection.setTextHAlignment(HAlignment.Center);
        playerInfoElements.addChild(labelCameraDirection);

        labelCameraPosition = new Label("camera position");
        labelCameraPosition.setTextVAlignment(VAlignment.Center);
        labelCameraPosition.setTextHAlignment(HAlignment.Center);
        playerInfoElements.addChild(labelCameraPosition);

        ((SimpleApplication) application).getGuiNode().attachChild(playerInfoElements);

        PlayerPhysicalControl playerPhysicalControl =
                new PlayerPhysicalControl(
                        this.getApplication().getCamera(),
                        this.getApplication().getInputManager());
        playerBoxGeometry.addControl(playerPhysicalControl);

        BulletManagerTest.getBulletAppState().setDebugEnabled(true);
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

    static float timer = 0;
    @Override
    public void update(float tpf) {

        float x = player.getPhysicsLocation().getX();
        float y = player.getPhysicsLocation().getY();
        float z = player.getPhysicsLocation().getZ();
        labelPlayerPosition.setText("PlayerPosition:\n (%3.3f, %3.3f. %3.3f)".formatted(x, y, z));

        x = getApplication().getCamera().getDirection().getX();
        y = getApplication().getCamera().getDirection().getY();
        z = getApplication().getCamera().getDirection().getZ();
        labelCameraDirection.setText("CameraDirection:\n (%3.3f, %3.3f. %3.3f)".formatted(x, y, z));

        x = getApplication().getCamera().getLocation().getX();
        y = getApplication().getCamera().getLocation().getY();
        z = getApplication().getCamera().getLocation().getZ();
        labelCameraPosition.setText("CameraPosition:\n (%3.3f, %3.3f. %3.3f)".formatted(x, y, z));

        camera2.lookAt(player.getPhysicsLocation(), new Vector3f(0, 1, 0));
    }
}
