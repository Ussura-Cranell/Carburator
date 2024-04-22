package com.carbonara.game.scene;

import com.carbonara.game.managers.BulletManagerTest;
import com.carbonara.game.managers.GUIManager;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.*;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.*;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;
import com.simsilica.lemur.GuiGlobals;

public class TestRoomScene {
    SimpleApplication app;

    public TestRoomScene(SimpleApplication app){
        this.app = app;
    }
    public void loadTestRoom(){

        //app.getViewPort().setBackgroundColor(ColorRGBA.White);

        // настройка камеры и курсора
        app.getFlyByCamera().setMoveSpeed(10f);
        GuiGlobals.getInstance().setCursorEventsEnabled(false);
        GUIManager.cameraUnlock(true);
        GUIManager.cursorVisible(false);

        Node lineRGB = new Node("lineRGB");

        Line lineXShape = new Line(new Vector3f(0, 0, 0), new Vector3f(2, 0, 0));
        Line lineYShape = new Line(new Vector3f(0, 0, 0), new Vector3f(0, 2, 0));
        Line lineZShape = new Line(new Vector3f(0, 0, 0), new Vector3f(0, 0, 2));

        Geometry lineXGeometry = new Geometry("lineX", lineXShape);
        Geometry lineYGeometry = new Geometry("lineY", lineYShape);
        Geometry lineZGeometry = new Geometry("lineZ", lineZShape);

        Material lineXMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Material lineYMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Material lineZMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");

        lineXGeometry.setMaterial(lineXMaterial);
        lineYGeometry.setMaterial(lineYMaterial);
        lineZGeometry.setMaterial(lineZMaterial);

        lineXMaterial.setColor("Color", ColorRGBA.Red);
        lineYMaterial.setColor("Color", ColorRGBA.Green);
        lineZMaterial.setColor("Color", ColorRGBA.Blue);

        lineRGB.attachChild(lineXGeometry);
        lineRGB.attachChild(lineYGeometry);
        lineRGB.attachChild(lineZGeometry);

        app.getRootNode().attachChild(lineRGB);

        // создание сетки с физическим компонентом
        Node grid  = new Node("grid");
        float size = 25;
        float offsetXZAxes = 2;

        Quad quadShape = new Quad(size, size);
        quadShape.scaleTextureCoordinates(new Vector2f(size, size));
        Geometry quadGeometry = new Geometry("Quad",quadShape);
        quadGeometry.rotate(-FastMath.DEG_TO_RAD*90, 0, 0);
        quadGeometry.move(offsetXZAxes, 0, size + offsetXZAxes);

        Texture texture = app.getAssetManager().loadTexture("Textures/UNTITLED.png");
        texture.setWrap(Texture.WrapMode.Repeat);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", texture);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        quadGeometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        quadGeometry.setMaterial(mat);

        grid.attachChild(quadGeometry);
        app.getRootNode().attachChild(grid);

        // создаем форму коллизии и менеджера управления физикой объекта
        CollisionShape quadCollisionShape = CollisionShapeFactory.createMeshShape(quadGeometry);
        RigidBodyControl landscape = new RigidBodyControl(quadCollisionShape, 0);

        quadGeometry.addControl(landscape);
        app.getRootNode().attachChild(quadGeometry);

        BulletManagerTest.getBulletAppState().getPhysicsSpace().add(landscape);
    }
}
