package com.carbonara.game.scene;

import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.managers.BulletAppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

public class DebugRoom{
    private Node debugSpace;

    public DebugRoom(Node debugSpace){

        this.debugSpace = debugSpace;

        // к каждой новой сцене идёт свой физический обработчик (подприложение)
        BulletAppState bulletAppState = new BulletAppState();
        BulletAppStateManager.registerBulletAppState(this.debugSpace, bulletAppState);
        GameLauncher.getApp().getStateManager().attach(BulletAppStateManager.getBulletAppState(this.debugSpace));

        // включение отладки физического обработчика
        bulletAppState.setDebugEnabled(true);

        // создаём цветные направляющие вдоль каждой оси
        createRGBUnitAxes(this.debugSpace);

        // создаём сетку по координатам (2;0;2) и вращением на 90 против часовой по оси X
        createGridSurface(this.debugSpace, 50,
                new Vector3f(2.0f, 0.0f, 2.0f),
                new Vector3f(-FastMath.DEG_TO_RAD*90.0f, 0.0f, 0.0f));

    }
    private void createGridSurface(Node attachNode, float size, Vector3f position, Vector3f rotation){
        /*
        Метод создаёт сетку в пространстве с добавленной физикой.
        Эта "физика" много где используется, например при добавлении объекта на сцену
        у которого есть такой параметр как физика, объект берет физический контроллер
        прямо из сцены (из BulletAppStateManager который рабоатет с Node)"
        */

        float lineCount = size;

        Quad gridSurfaceShape;
        Spatial gridSurfaceSpatial;
        Texture gridSurfaceTexture;
        Material gridSurfaceMaterial;

        // создание сетки размера sizeX на sizeY
        gridSurfaceShape = new Quad(size, size);
        gridSurfaceShape.scaleTextureCoordinates(new Vector2f(lineCount, lineCount));

        // создание геометрии на основе сетки
        gridSurfaceSpatial = new Geometry("gridSurfaceSpatial", gridSurfaceShape);
        gridSurfaceSpatial.setLocalTranslation(position.add(new Vector3f(0.0f, 0.0f, size)));
        gridSurfaceSpatial.rotate(rotation.getX(), rotation.getY(), rotation.getZ());

        // загрузка текстуры сетки и режима "повторение"
        gridSurfaceTexture = GameLauncher.getApp().getAssetManager().loadTexture("Textures/UNTITLED.png");
        gridSurfaceTexture.setWrap(Texture.WrapMode.Repeat);

        // загрузка материала с текстурой, установка режима альфа канала
        gridSurfaceMaterial = new Material(GameLauncher.getApp().getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        gridSurfaceMaterial.setTexture("ColorMap", gridSurfaceTexture);
        gridSurfaceMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        gridSurfaceSpatial.setQueueBucket(RenderQueue.Bucket.Transparent);
        gridSurfaceSpatial.setMaterial(gridSurfaceMaterial);

        // создание контроллера физики и его прикрепление к объекту gridSurfaceSpatial
        CollisionShape gridSurfaceCollisionShape = CollisionShapeFactory.createMeshShape(gridSurfaceSpatial);
        RigidBodyControl gridSurfaceRigidBodyControl = new RigidBodyControl(gridSurfaceCollisionShape, 0);
        gridSurfaceSpatial.addControl(gridSurfaceRigidBodyControl);

        // ищём в менеджере управления физики нашу сцену
        BulletAppState bulletAppState = BulletAppStateManager.getBulletAppState(attachNode);

        // добавляем к сцене обработчик физики gridSurfaceRigidBodyControl
        bulletAppState.getPhysicsSpace().add(gridSurfaceRigidBodyControl);

        // прикрепляем сетку к нашему ноду
        attachNode.attachChild(gridSurfaceSpatial);
    }
    private void createRGBUnitAxes(Node attachNode){

        /* Метод создаёт 3 отрезка вдоль каждой координатной оси нода attachNode разных цветов*/

        Node lineRGB = new Node("lineRGB");

        Line lineXShape = new Line(new Vector3f(0, 0, 0), new Vector3f(2, 0, 0));
        Line lineYShape = new Line(new Vector3f(0, 0, 0), new Vector3f(0, 2, 0));
        Line lineZShape = new Line(new Vector3f(0, 0, 0), new Vector3f(0, 0, 2));

        Geometry lineXGeometry = new Geometry("lineX", lineXShape);
        Geometry lineYGeometry = new Geometry("lineY", lineYShape);
        Geometry lineZGeometry = new Geometry("lineZ", lineZShape);

        Material lineXMaterial = new Material(GameLauncher.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Material lineYMaterial = new Material(GameLauncher.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        Material lineZMaterial = new Material(GameLauncher.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");

        lineXGeometry.setMaterial(lineXMaterial);
        lineYGeometry.setMaterial(lineYMaterial);
        lineZGeometry.setMaterial(lineZMaterial);

        lineXMaterial.setColor("Color", ColorRGBA.Red);
        lineYMaterial.setColor("Color", ColorRGBA.Green);
        lineZMaterial.setColor("Color", ColorRGBA.Blue);

        lineRGB.attachChild(lineXGeometry);
        lineRGB.attachChild(lineYGeometry);
        lineRGB.attachChild(lineZGeometry);

        attachNode.attachChild(lineRGB);
    }
    public Node getDebugSpace(){
        return this.debugSpace;
    }
}
