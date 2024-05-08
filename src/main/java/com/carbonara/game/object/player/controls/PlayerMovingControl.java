package com.carbonara.game.object.player.controls;

import com.carbonara.game.main.GameLauncher;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.jme3.renderer.Camera;

import java.io.IOException;

public class PlayerMovingControl implements Control {

    PlayerMovingControl(Camera camera){
        this.camera = camera;
    }
    private Spatial playerSpatial;
    private CharacterControl playerCharacterControl;
    private Camera camera; // для направления движения

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        this.playerSpatial = spatial;
        this.playerCharacterControl = spatial.getControl(CharacterControl.class);

        addButtonControl();
    }
    private boolean left = false, right = false, up = false, down = false, jump = false;
    private boolean isRunning = false;
    public void addButtonControl(){
        GameLauncher.getApp().getInputManager().addMapping("CharLeft"       ,new KeyTrigger(KeyInput.KEY_A));
        GameLauncher.getApp().getInputManager().addMapping("CharRight"      ,new KeyTrigger(KeyInput.KEY_D));
        GameLauncher.getApp().getInputManager().addMapping("CharForward"    ,new KeyTrigger(KeyInput.KEY_W));
        GameLauncher.getApp().getInputManager().addMapping("CharBackward"   ,new KeyTrigger(KeyInput.KEY_S));
        GameLauncher.getApp().getInputManager().addMapping("CharJump"       ,new KeyTrigger(KeyInput.KEY_SPACE));
        GameLauncher.getApp().getInputManager().addMapping("CharRun"       ,new KeyTrigger(KeyInput.KEY_LSHIFT));

        GameLauncher.getApp().getInputManager().addListener(actionListener, "CharLeft",
                "CharRight",
                "CharForward",
                "CharBackward",
                "CharJump",
                "CharRun");
    }

    private final ActionListener actionListener = (s, b, v) -> {
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
            case "CharRun":
                isRunning = b;
                break;
        }
    };

    Vector3f playerWalkDirectionVector = new Vector3f(0.0f ,0.0f, 0.0f);

    private boolean flag_movingControl = false;

    @Override
    public void update(float v) {
        if (flag_movingControl) {

            float playerSpeed = 10;

            if (isRunning && playerCharacterControl.onGround()) playerSpeed *= 1.5f;

            Vector3f camDir = camera.getDirection().clone();
            Vector3f camLeft = camera.getLeft().clone();
            camDir.y = 0;
            camLeft.y = 0;
            playerWalkDirectionVector.set(0, 0, 0);

            if (up) playerWalkDirectionVector.addLocal(camDir);

            if (down) playerWalkDirectionVector.addLocal(camDir.negate());

            if (left) playerWalkDirectionVector.addLocal(camLeft);

            if (right) playerWalkDirectionVector.addLocal(camLeft.negate());

            if (jump && playerCharacterControl.onGround()) playerCharacterControl.jump();

            // characterCamera(flagSwitchCameraCharacter);
            playerCharacterControl.setWalkDirection(playerWalkDirectionVector.normalize().mult(playerSpeed * v));
        }
    }

    public void setFlag_movingControl(boolean flag_movingControl) {
        this.flag_movingControl = flag_movingControl;

        // чтобы если игрок при движении переключился на полёт, движение игрока прекратилось
        playerCharacterControl.setWalkDirection(Vector3f.ZERO);
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
