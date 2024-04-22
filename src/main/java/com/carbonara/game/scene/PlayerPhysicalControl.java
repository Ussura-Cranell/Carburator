package com.carbonara.game.scene;

import com.jme3.bullet.control.CharacterControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.KeyInput;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;
import java.util.logging.Logger;

public class PlayerPhysicalControl implements Control {

    private static final Logger logger = Logger.getLogger(PlayerPhysicalControl.class.getName());
    Camera camera;
    InputManager inputManager;
    Spatial entity;
    CharacterControl entityCharacterControl;
    Vector3f playerWalkDirectionVector = Vector3f.ZERO;

    PlayerPhysicalControl(Camera camera, InputManager inputManager){
        this.camera = camera;
        this.inputManager = inputManager;
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void setSpatial(Spatial spatial) {

        this.entity = spatial;
        this.entityCharacterControl = spatial.getControl(CharacterControl.class);

        inputManager.addMapping("CharLeft",     new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("CharRight",    new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("CharForward",  new KeyTrigger(KeyInput.KEY_I));
        inputManager.addMapping("CharBackward", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("CharJump", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addMapping("SwitchСameraСharacter", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("PlayerPositionReset", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(actionListener, "CharLeft",
                "CharRight",
                "CharForward",
                "CharBackward",
                "CharJump",
                "SwitchСameraСharacter",
                "PlayerPositionReset");
    }

    private boolean left = false, right = false, up = false, down = false, jump = false;

    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String s, boolean b, float v) {
            switch (s){
                case "CharLeft":
                    left = b;
                    break;
                case "CharRight":
                    right = b;
                    break;
                case "CharForward":
                    up = b;
                    break;
                case "CharBackward":
                    down = b;
                    break;
                case "CharJump":
                    jump = b;
                    break;
                case "SwitchСameraСharacter":
                    if (!b) {flagSwitchCameraCharacter = !flagSwitchCameraCharacter;
                        if (flagSwitchCameraCharacter) logger.info("Захват камеры персонажем");
                        else logger.info("Камера свободна");
                    }
                    break;
                case "PlayerPositionReset":
                    if (!b)
                        entityCharacterControl.setPhysicsLocation(new Vector3f(14, 10, 15));
                    break;
            }
        }
    };

    boolean flagSwitchCameraCharacter = false;

    private void characterCamera(boolean value){
        if (value) camera.setLocation(entityCharacterControl.getPhysicsLocation().add(0, 1.5f, 0));
    }

    @Override
    public void update(float v) {

        float playerSpeed = 10;

        Vector3f camDir = camera.getDirection().clone()/*.multLocal(playerSpeed*v)*/;
        Vector3f camLeft = camera.getLeft().clone()/*.multLocal(playerSpeed*v)*/;
        camDir.y = 0;
        camLeft.y = 0;
        playerWalkDirectionVector.set(0, 0, 0);

        if (up) {
            playerWalkDirectionVector.addLocal(camDir);
        }
        if (down) {
            playerWalkDirectionVector.addLocal(camDir.negate());
        }
        if (left) {
            playerWalkDirectionVector.addLocal(camLeft);
        }
        if (right) {
            playerWalkDirectionVector.addLocal(camLeft.negate());
        }
        if (jump && entityCharacterControl.onGround()) {
            entityCharacterControl.jump();
        }

        characterCamera(flagSwitchCameraCharacter);

        entityCharacterControl.setWalkDirection(playerWalkDirectionVector.normalize().mult(playerSpeed * v));
    }

    @Override
    public void render(RenderManager renderManager, ViewPort viewPort) {

    }

    @Override
    public void write(JmeExporter jmeExporter) throws IOException {

    }

    @Override
    public void read(JmeImporter jmeImporter) throws IOException {

    }
}
