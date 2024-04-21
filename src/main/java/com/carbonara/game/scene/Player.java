package com.carbonara.game.scene;

import com.carbonara.game.managers.BulletManagerTest;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.Joystick;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Cylinder;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

import java.util.Map;
import java.util.logging.Logger;

public class Player extends BaseAppState {
    private static final Logger logger = Logger.getLogger(Player.class.getName());

    CharacterControl player;
    Geometry playerCylinderGeometry;
    Vector3f initPhysicsLocation;
    Node scene;

    Label labelPlayerPosition;
    Label labelCameraPosition;
    Label labelCameraRotation;

    public Player(Node scene, Vector3f initPhysicsLocation){
        this.scene = scene;
        this.initPhysicsLocation = initPhysicsLocation;
    }

    @Override
    protected void initialize(Application application) {

        float radius = 1;
        float height = 3;

        // физика игрока
        CapsuleCollisionShape playerCollisionShape = new CapsuleCollisionShape(radius,height);
        player = new CharacterControl(playerCollisionShape, 0.05f);
        player.setJumpSpeed(20);
        player.setFallSpeed(30);
        player.setGravity(30);

        player.setPhysicsLocation(initPhysicsLocation);
        BulletManagerTest.getBulletAppState().getPhysicsSpace().add(player);

        // отображение игрока
        int axisSamples = 5;
        int radialSamples = 5;
        Cylinder playerCylinderShape = new Cylinder(axisSamples, radialSamples, radius, height, true, false);
        playerCylinderGeometry = new Geometry("A shape", playerCylinderShape);
        playerCylinderGeometry.rotate(FastMath.DEG_TO_RAD*90, 0, 0);
        Material mat = new Material(application.getAssetManager(),
                "Common/MatDefs/Misc/ShowNormals.j3md");
        playerCylinderGeometry.setMaterial(mat);
        ((SimpleApplication)application).getRootNode().attachChild(playerCylinderGeometry);

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

        labelCameraPosition = new Label("camera position");
        labelCameraPosition.setTextVAlignment(VAlignment.Center);
        labelCameraPosition.setTextHAlignment(HAlignment.Center);
        playerInfoElements.addChild(labelCameraPosition);

        labelCameraRotation = new Label("camera rotation");
        labelCameraRotation.setTextVAlignment(VAlignment.Center);
        labelCameraRotation.setTextHAlignment(HAlignment.Center);
        playerInfoElements.addChild(labelCameraRotation);

        ((SimpleApplication) application).getGuiNode().attachChild(playerInfoElements);
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {

        // getApplication().getInputManager().clearMappings();
        // getApplication().getInputManager().

        if (getApplication().getInputManager().getJoysticks() != null)
        for (Joystick joystick : getApplication().getInputManager().getJoysticks()){
            logger.info("joystick: " + joystick.getName());
        }
    }

    @Override
    protected void onDisable() {

    }

    static float timer = 0;
    static float timer2 = 0;

    @Override
    public void update(float tpf) {

        // цилиндр игрока следует за его физикой
        playerCylinderGeometry.setLocalTranslation(player.getPhysicsLocation());

        // String textlabelPlayerPosition = "PlayerPosition: " + player.getPhysicsLocation();
        // String textlabelCameraPosition = "CameraPosition: " + getApplication().getCamera().getLocation();
        // String textlabelCameraRotation = "CameraRotation: " + getApplication().getCamera().getRotation();

        float x = player.getPhysicsLocation().getX();
        float y = player.getPhysicsLocation().getY();
        float z = player.getPhysicsLocation().getZ();
        labelPlayerPosition.setText("PlayerPosition:\n (%3.3f, %3.3f. %3.3f)".formatted(x, y, z));

        x = getApplication().getCamera().getLocation().getX();
        y = getApplication().getCamera().getLocation().getY();
        z = getApplication().getCamera().getLocation().getZ();
        labelCameraPosition.setText("CameraPosition:\n (%3.3f, %3.3f. %3.3f)".formatted(x, y, z));

        x = getApplication().getCamera().getRotation().getX();
        y = getApplication().getCamera().getRotation().getY();
        z = getApplication().getCamera().getRotation().getZ();
        labelCameraRotation.setText("CameraRotation:\n (%3.3f, %3.3f. %3.3f)".formatted(x, y, z));

        timer += tpf;
        timer2 += tpf;

        if (timer >= 0.5){
            timer = 0;
            logger.info("Player Physics Location : " + player.getPhysicsLocation());
        }

        if (timer2 >= 2){
            timer2 = 0;
            player.setPhysicsLocation(player.getPhysicsLocation().add(0, 10, 0));
        }
    }
}
