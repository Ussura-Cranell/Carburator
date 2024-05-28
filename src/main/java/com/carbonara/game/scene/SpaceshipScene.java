package com.carbonara.game.scene;
import com.carbonara.game.gui.spaceship.systems.*;
import com.carbonara.game.logic.NewSceneGuardian;
import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.managers.BulletAppStateManager;
import com.carbonara.game.managers.NewPauseGameManager;
import com.carbonara.game.managers.ServiceLocatorManagers;
import com.carbonara.game.object.gameobjects.categories.player.controls.PlayerStateManager;
import com.carbonara.game.object.gameobjects.categories.player.general.InteractionControl;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.UniversalObject;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.commands.*;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.*;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.*;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import com.simsilica.lemur.*;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class SpaceshipScene implements Observer {

    private static Logger logger = Logger.getLogger(SpaceshipScene.class.getName());
    private static BulletAppState bulletAppState;
    private PlayerStateManager playerStateManager;
    private Node scene;
    private Node screenNodes;

    public Node createScene(){

        scene = new Node("SpaceshipScene");

        createGridSurface(scene, 50,
                new Vector3f(2.0f, 0.0f, 2.0f),
                new Vector3f(-FastMath.DEG_TO_RAD*90.0f, 0.0f, 0.0f));

        // создаём персонажа на сцене
        playerStateManager = new PlayerStateManager(scene);
        // включаем его
        GameLauncher.getApp().getStateManager().attach(playerStateManager);

        createTestingCube2(scene);

        float scale = 3.5f;
        Vector3f offset = new Vector3f(25, -0.25f, 50);

        loadSpaseShipRoom(scene, offset, scale);
        loadBarrier(scene, offset.add(0.0f, 2.55f, 0.0f), scale);

        PointLight pointLight = new PointLight();
        pointLight.setRadius(4.5f*scale);
        pointLight.setPosition(offset.add(
                2.3712153f * scale,
                1.8949776f * scale,
                -3.9428482f* scale));
        scene.addLight(pointLight);

        SpotLight spotLight = new SpotLight();
        spotLight.setPosition(offset.add(
                2.3553178f  * scale,
                4.90501f    * scale,
                -3.9901752f * scale));
        spotLight.setDirection(new Vector3f(-0.008472737f, -0.8804816f, 0.47400463f));
        spotLight.setSpotInnerAngle(0.1f);
        spotLight.setSpotOuterAngle(0.8f);
        spotLight.setSpotRange(10.0f*scale);
        scene.addLight(spotLight);

        //Node screenNodes = loadScreenNodes(scene, offset, scale);
        // for (Spatial child : screenNodes.getChildren()){
        //     System.out.println("child name: " + child.getName());
        //     System.out.println("child world translation: " + child.getWorldTranslation());
        //     System.out.println("child world rotation: " + child.getWorldRotation());
        //     System.out.println();
//
        //     Node childNode = (Node) child;
        //     childNode.attachChild(createScreenBox(0.5f));
        // }

        // экраны с проставленными размерами
        // ((Node)screenNodes.getChild("Screen_1")).attachChild(createScreenBox(0.333895f, 0.236017f,  0.5f));
        // ((Node)screenNodes.getChild("Screen_2")).attachChild(createScreenBox(0.456416f, 0.322345f,  0.5f));
        // ((Node)screenNodes.getChild("Screen_3")).attachChild(createScreenBox(0.797655f, 0.56383f,   0.5f));
        // ((Node)screenNodes.getChild("Screen_4")).attachChild(createScreenBox(0.333895f, 0.236017f,  0.5f));
        // ((Node)screenNodes.getChild("Screen_5")).attachChild(createScreenBox(0.456416f, 0.322345f,  0.5f));
        // ((Node)screenNodes.getChild("Screen_6")).attachChild(createScreenBox(0.563866f, 0.250746f,  0.5f));
        // ((Node)screenNodes.getChild("Screen_7")).attachChild(createScreenBox(0.350185f, 0.317938f,  0.5f));
        // ((Node)screenNodes.getChild("Screen_8")).attachChild(createScreenBox(0.987689f, 0.346177f,  0.5f));

        /*
        1. 0.333895 0.236017 | 0.333895f / 2 = 0.1669475 | 0.236017f / 2 = 0.1180085
        2. 0.456416 0.322345 | 0.456416f / 2 = 0.2282080 | 0.322345f / 2 = 0.1611725
        3. 0.797655 0.563830 | 0.797655f / 2 = 0.3988275 | 0.563830f / 2 = 0.2819150
        6. 0.563868 0.250746 | 0.563868f / 2 = 0.2819340 | 0.250746f / 2 = 0.1253730
        7. 0.350185 0.317938 | 0.350185f / 2 = 0.1750925 | 0.317938f / 2 = 0.1589690
        8. 0.987689 0.346177 | 0.987689f / 2 = 0.4938445 | 0.346177f / 2 = 0.1730885
        */

        // WeaponControlSystemPage
        if (true){
            WeaponControlSystemPage weaponControlSystemPage =
                    new WeaponControlSystemPage(((Node)screenNodes.getChild("Screen_1")), scale);
            weaponControlSystemPage.addScreenToNode();
        } else {
            WeaponControlSystemPage weaponControlSystemPage =
                    new WeaponControlSystemPage(GameLauncher.getApp().getGuiNode(), scale);
            weaponControlSystemPage.addScreenToGUI();
        }

        // ShieldControlSystemPage
        if (true){
            ShieldControlSystemPage shieldControlSystemPage =
                    new ShieldControlSystemPage(((Node)screenNodes.getChild("Screen_2")), scale);
            shieldControlSystemPage.addScreenToNode();
        } else {
            ShieldControlSystemPage shieldControlSystemPage =
                    new ShieldControlSystemPage(GameLauncher.getApp().getGuiNode(), scale);
            shieldControlSystemPage.addScreenToGUI();
        }

        // ScanningControlSystemPage
        if (true){
            ScanningControlSystemPage scanningControlSystemPage =
                    new ScanningControlSystemPage(((Node)screenNodes.getChild("Screen_3")), scale);
            scanningControlSystemPage.addScreenToNode();
        } else {
            ScanningControlSystemPage scanningControlSystemPage =
                    new ScanningControlSystemPage(GameLauncher.getApp().getGuiNode(), scale);
            scanningControlSystemPage.addScreenToGUI();
        }

        // StorageControlSystemPage
        if (true){
            StorageControlSystemPage storageControlSystemPage =
                    new StorageControlSystemPage(((Node)screenNodes.getChild("Screen_4")), scale);
            storageControlSystemPage.addScreenToNode();
        } else {
            StorageControlSystemPage storageControlSystemPage =
                    new StorageControlSystemPage(GameLauncher.getApp().getGuiNode(), scale);
            storageControlSystemPage.addScreenToGUI();
        }

        // RepairControlSystemPage
        if (true){
            RepairControlSystemPage repairControlSystemPage =
                    new RepairControlSystemPage(((Node)screenNodes.getChild("Screen_5")), scale);
            repairControlSystemPage.addScreenToNode();
        } else {
            RepairControlSystemPage repairControlSystemPage =
                    new RepairControlSystemPage(GameLauncher.getApp().getGuiNode(), scale);
            repairControlSystemPage.addScreenToGUI();
        }

        // ReactorControlSystemPage
        if (true){
            ReactorControlSystemPage reactorControlSystemPage =
                    new ReactorControlSystemPage(((Node)screenNodes.getChild("Screen_6")), scale);
            reactorControlSystemPage.addScreenToNode();
        } else {
            ReactorControlSystemPage reactorControlSystemPage =
                    new ReactorControlSystemPage(GameLauncher.getApp().getGuiNode(), scale);
            reactorControlSystemPage.addScreenToGUI();
        }

        // TerminalControlSystemPage
        if (true){
            TerminalControlSystemPage terminalControlSystemPage =
                    new TerminalControlSystemPage(((Node)screenNodes.getChild("Screen_7")), scale);
            terminalControlSystemPage.addScreenToNode();
        } else {
            TerminalControlSystemPage terminalControlSystemPage =
                    new TerminalControlSystemPage(GameLauncher.getApp().getGuiNode(), scale);
            terminalControlSystemPage.addScreenToGUI();
        }

        // FlightControlSystemPage
        if (true){
            FlightControlSystemPage flightControlSystemPage =
                    new FlightControlSystemPage(((Node)screenNodes.getChild("Screen_8")), scale);
            flightControlSystemPage.addScreenToNode();
        } else {
            FlightControlSystemPage flightControlSystemPage =
                    new FlightControlSystemPage(GameLauncher.getApp().getGuiNode(), scale);
            flightControlSystemPage.addScreenToGUI();
        }

        return scene;
    }

    private Spatial createScreenBox(float sizeX, float sizeZ, float scale){
        float x = sizeX * scale;
        float y = 0.025f * scale;
        float z = sizeZ * scale;
        Box testingBoxShape = new Box(x, y, z);
        Geometry testingBoxGeometry = new Geometry("testingBox2Geometry", testingBoxShape);
        Material testingBoxMaterial = new Material(GameLauncher.getApp().getAssetManager(),
                "Common/MatDefs/Misc/ShowNormals.j3md");
        testingBoxMaterial.getAdditionalRenderState().setWireframe(true);
        testingBoxGeometry.setMaterial(testingBoxMaterial);
        testingBoxGeometry.rotate(FastMath.DEG_TO_RAD * -90, 0, 0);
        /*testingBoxGeometry.rotate(rotation);*/

        return testingBoxGeometry;
    }

    public void initialize(){
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(true);
        GameLauncher.getApp().getStateManager().attach(bulletAppState);

        ServiceLocatorManagers.getNewPauseGameManager().addObserver(this);
    }

    private void loadBarrier(Node scene, Vector3f position, float scale){
        Spatial barrierSpatial = GameLauncher.getApp().getAssetManager().loadModel("Models/capitanroom/barrier/barrier.j3o");
        scene.attachChild(barrierSpatial);
        barrierSpatial.setLocalTranslation(position);
        barrierSpatial.scale(scale);

       // Material testingBarrierMaterial = new Material(GameLauncher.getApp().getAssetManager(),
       //         "Common/Materials/RedColor.j3m");
        Material testingBarrierMaterial = GameLauncher.getApp().getAssetManager().loadMaterial("Common/Materials/RedColor.j3m");
        //testingBoxMaterial.setColor("Color", ColorRGBA.Magenta);
        testingBarrierMaterial.getAdditionalRenderState().setWireframe(true);
        testingBarrierMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        barrierSpatial.setMaterial(testingBarrierMaterial);

        CollisionShape gridSurfaceCollisionShape = CollisionShapeFactory.createMeshShape(barrierSpatial);
        RigidBodyControl gridSurfaceRigidBodyControl = new RigidBodyControl(gridSurfaceCollisionShape, 0);
        barrierSpatial.addControl(gridSurfaceRigidBodyControl);

        // ищём в менеджере управления физики нашу сцену
        // BulletAppState bulletAppState = BulletAppStateManager.getBulletAppState(attachNode);

        // добавляем к сцене обработчик физики gridSurfaceRigidBodyControl
        bulletAppState.getPhysicsSpace().add(gridSurfaceRigidBodyControl);

        // прикрепляем сетку к нашему ноду
        scene.attachChild(barrierSpatial);
    }
    /*private Node loadScreenNodes(Node scene, Vector3f position, float scale){
        Node screenNodes = (Node) GameLauncher.getApp().getAssetManager().loadModel("Models/screenNode/ScreenNode.j3o");
        scene.attachChild(screenNodes);
        screenNodes.setLocalTranslation(position);
        screenNodes.scale(scale);

        return screenNodes;
    }*/

    public void cleanup(){
        GameLauncher.getApp().getStateManager().detach(bulletAppState);
        bulletAppState = null;
        scene = null;
        // отключаем обработку персонажа
        GameLauncher.getApp().getStateManager().detach(playerStateManager);
    }

    /*private void loadSpaseShipRoom(Node scene, Vector3f position, float scale){
        Spatial spaceShipRoomSpatial = GameLauncher.getApp().getAssetManager().loadModel("Models/capitanroom/capitanroom.j3o");
        scene.attachChild(spaceShipRoomSpatial);
        spaceShipRoomSpatial.setLocalTranslation(position);
        spaceShipRoomSpatial.scale(scale);
    }*/

    private void loadSpaseShipRoom(Node scene, Vector3f position, float scale){
        Spatial spaceShipRoomSpatial = GameLauncher.getApp().getAssetManager().loadModel("Models/room/room.j3o");
        scene.attachChild(spaceShipRoomSpatial);
        spaceShipRoomSpatial.setLocalTranslation(position);
        spaceShipRoomSpatial.scale(scale);
        screenNodes = (Node)((Node)spaceShipRoomSpatial).getChild("ScreenNodes");
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
        // BulletAppState bulletAppState = BulletAppStateManager.getBulletAppState(attachNode);

        // добавляем к сцене обработчик физики gridSurfaceRigidBodyControl
        bulletAppState.getPhysicsSpace().add(gridSurfaceRigidBodyControl);

        // прикрепляем сетку к нашему ноду
        attachNode.attachChild(gridSurfaceSpatial);
    }

    public static BulletAppState getBulletAppState() {
        return bulletAppState;
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
        BulletAppState bulletAppState = getBulletAppState();

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

    public Node getScene() {
        return scene;
    }

    public PlayerStateManager getPlayerStateManager() {
        return playerStateManager;
    }

    @Override
    public void update(Observable o, Object arg) {
        // logger.info(o.getClass().getSimpleName() + arg);
        if (o instanceof NewPauseGameManager) {
            bulletAppState.setEnabled((boolean)arg);
            logger.info("NewPauseGameManager arg: " + arg);
        }
    }
}
