package com.carbonara.game.scene;

import com.carbonara.game.logic.interaction.interfaces.IActionCommand;
import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.managers.BulletAppStateManager;
import com.carbonara.game.object.TESTINGACTION.UniversalObject;
import com.carbonara.game.object.TESTINGACTION.commands.*;
import com.carbonara.game.object.player.general.InteractionControl;
import com.carbonara.game.object.technique.TechniqueControl;
import com.carbonara.game.object.technique.commands.TurnOnCommand;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
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
import com.jme3.scene.shape.Box;
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

        // создаём кубы для тестирования
        createTestingCube(this.debugSpace);
        createTestingCube2(this.debugSpace);

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

        this.debugSpace.getClass();
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
    private void createTestingCube(Node attachNode){
        Box testingBoxShape = new Box(3, 3, 3);
        Geometry testingBoxGeometry = new Geometry("testingBoxGeometry", testingBoxShape);
        testingBoxGeometry.move(25, 25, 25);
        Material testingBoxMaterial = new Material(GameLauncher.getApp().getAssetManager(),
                "Common/MatDefs/Misc/ShowNormals.j3md");
        //testingBoxMaterial.setColor("Color", ColorRGBA.Magenta);
        testingBoxMaterial.getAdditionalRenderState().setWireframe(true);
        testingBoxGeometry.setMaterial(testingBoxMaterial);

        //CollisionShape testingBoxCollisionShape = CollisionShapeFactory.createMeshShape(testingBoxGeometry);
        CollisionShape testingBoxCollisionShape = new BoxCollisionShape(3, 3, 3);
        RigidBodyControl testingBoxRigidBodyControl = new RigidBodyControl(testingBoxCollisionShape, 1);
        testingBoxGeometry.addControl(testingBoxRigidBodyControl);

        // ищём в менеджере управления физики нашу сцену
        BulletAppState bulletAppState = BulletAppStateManager.getBulletAppState(attachNode);

        // добавляем к сцене обработчик физики gridSurfaceRigidBodyControl
        bulletAppState.getPhysicsSpace().add(testingBoxRigidBodyControl);

        // прикрепляем куб к нашему ноду
        attachNode.attachChild(testingBoxGeometry);

        // добавляем кубу возможность для взаимодействия с ним
        InteractionControl interactionControl = new InteractionControl();
        testingBoxGeometry.addControl(interactionControl);

        // добавляем ему поведение "техники"
        TechniqueControl techniqueControl = new TechniqueControl();
        testingBoxGeometry.addControl(techniqueControl);

        // ~ регистрируем действия доступные изначально
        interactionControl.addAction("turnOn", new TurnOnCommand(techniqueControl));

    }

    private void createTestingCube2(Node attachNode){
        Box testingBoxShape = new Box(1, 1, 1);
        Geometry testingBoxGeometry = new Geometry("testingBox2Geometry", testingBoxShape);
        testingBoxGeometry.move(4, 10, 25);
        Material testingBoxMaterial = new Material(GameLauncher.getApp().getAssetManager(),
                "Common/MatDefs/Misc/ShowNormals.j3md");
        //testingBoxMaterial.setColor("Color", ColorRGBA.Magenta);
        testingBoxMaterial.getAdditionalRenderState().setWireframe(true);
        testingBoxGeometry.setMaterial(testingBoxMaterial);

        //CollisionShape testingBoxCollisionShape = CollisionShapeFactory.createMeshShape(testingBoxGeometry);
        CollisionShape testingBoxCollisionShape = new BoxCollisionShape(1, 1, 1);
        RigidBodyControl testingBoxRigidBodyControl = new RigidBodyControl(testingBoxCollisionShape, 1);
        testingBoxGeometry.addControl(testingBoxRigidBodyControl);

        // ищём в менеджере управления физики нашу сцену
        BulletAppState bulletAppState = BulletAppStateManager.getBulletAppState(attachNode);

        // добавляем к сцене обработчик физики gridSurfaceRigidBodyControl
        bulletAppState.getPhysicsSpace().add(testingBoxRigidBodyControl);

        // прикрепляем куб к нашему ноду
        attachNode.attachChild(testingBoxGeometry);

        // добавляем кубу возможность для взаимодействия с ним
        InteractionControl interactionControl = new InteractionControl();
        testingBoxGeometry.addControl(interactionControl);

        // добавляем поведение универсального объекта
        UniversalObject universalObject = new UniversalObject();
        testingBoxGeometry.addControl(universalObject);

        interactionControl.addAction("Something 1", new Something1Command(universalObject));
        interactionControl.addAction("Something 2", new Something2Command(universalObject));
        interactionControl.addAction("Something 3", new Something3Command(universalObject));
        interactionControl.addAction("Something 4", new Something4Command(universalObject));
        interactionControl.addAction("Something 5", new Something5Command(universalObject));
        interactionControl.addAction("Something 6", new Something6Command(universalObject));
    }
    public Node getDebugSpace(){
        return this.debugSpace;
    }
}
