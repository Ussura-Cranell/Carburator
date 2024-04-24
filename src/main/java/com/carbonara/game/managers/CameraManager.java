package com.carbonara.game.managers;

import com.jme3.app.SimpleApplication;
import com.jme3.input.FlyByCamera;
import com.jme3.renderer.Camera;

public class CameraManager {
    private static SimpleApplication app;
    public static final float INIT_CAMERA_FOV = 70f; // угол обзора
    public static final float INIT_FLY_CAMERA_SPEED = 10.0f; // начальная скорость летающей камеры
    public static final boolean INIT_FLY_CAMERA_ENABLED = true; //

    public CameraManager(SimpleApplication app) {
        CameraManager.app = app;
        this.initialize(app.getFlyByCamera(), app.getCamera());
    }

    private void initialize(FlyByCamera flyByCamera, Camera camera){
        camera.setFov(CameraManager.INIT_CAMERA_FOV);

        flyByCamera.setMoveSpeed(CameraManager.INIT_FLY_CAMERA_SPEED);
        flyByCamera.setEnabled(CameraManager.INIT_FLY_CAMERA_ENABLED);
    }

    public static SimpleApplication getApp() {
        return app;
    }

    public static void cameraUnlock(boolean value){
        CameraManager.app.getFlyByCamera().setEnabled(value);
    }

    public static void flyCamSetMoveSpeed(float moveSpeed){
        CameraManager.app.getFlyByCamera().setMoveSpeed(moveSpeed);
    }
}
