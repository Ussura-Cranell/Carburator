package testing.spaceshipsystemstest;

import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.object.other.spaceship.CreateTestSpaceShip;
import com.carbonara.game.object.other.spaceship.components.engine.Engine;
import com.carbonara.game.object.other.spaceship.components.reactor.Reactor;
import com.carbonara.game.object.other.spaceship.components.shield.Shield;
import com.carbonara.game.object.other.spaceship.components.weapon.Weapon;
import com.carbonara.game.object.other.spaceship.systems.FlightControlSystem;
import com.carbonara.game.object.other.spaceship.systems.ReactorControlSystem;
import com.carbonara.game.object.other.spaceship.systems.ShieldControlSystem;
import com.carbonara.game.object.other.spaceship.systems.WeaponControlSystem;
import com.carbonara.game.object.other.spaceship.systems.commands.AbstractSpaceShipCommand;
import com.carbonara.game.object.other.spaceship.systems.commands.flight.TeleportationByCoordinatesCommand;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import java.util.Random;

public class CreateScene extends BaseAppState {

    private SimpleApplication app;
    private Node spaceShipScene = new Node("SpaceShipNode");
    private CreateTestSpaceShip testSpaceShipAppState;


    @Override
    protected void initialize(Application application) {
        this.app = (SimpleApplication) application;

        CameraManager.cameraUnlock(true);
        app.getInputManager().setCursorVisible(false);

        // создаём корабль и сразу прекрипляем его к сцене
        spaceShipSpatial = createSpaceShipSpatial();
        testSpaceShipAppState = new CreateTestSpaceShip(spaceShipSpatial, spaceShipScene);
        app.getStateManager().attach(testSpaceShipAppState);

        // тестовый куб для тестирования коммандера
        addRedCube(spaceShipScene);

        // прикрепляем сцену к главному ноду
        app.getRootNode().attachChild(spaceShipScene);

        registerSystems();
    }

    private void registerSystems(){
        // добавление системы реактора
        testSpaceShipAppState.getMainControlSystem().registerSystem(
                ReactorControlSystem.class, new ReactorControlSystem());

        // добавление системы двигателей
        testSpaceShipAppState.getMainControlSystem().registerSystem(
                FlightControlSystem.class, new FlightControlSystem());

        // добавление системы орудий
        testSpaceShipAppState.getMainControlSystem().registerSystem(
                WeaponControlSystem.class, new WeaponControlSystem());

        // добавление системы щитов
        testSpaceShipAppState.getMainControlSystem().registerSystem(
                ShieldControlSystem.class, new ShieldControlSystem());

        // добавляем к кораблю кучу реакторов
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Reactor());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Reactor());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Reactor());

        // добавляем к кораблю кучу двигателей
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());

        // добавляем к кораблю кучу орудий
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Weapon());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Weapon());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Weapon());

        // добавляем к кораблю кучу щитов
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Shield());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Shield());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Shield());

        System.out.println(testSpaceShipAppState); // отобразит системы корабля + части систем
    }

    private Spatial spaceShipSpatial;
    private Spatial createSpaceShipSpatial(){
        Box boxShape = new Box(1,1,1);
        Geometry boxGeometry = new Geometry("spaceShipSpatial", boxShape);

        boxGeometry.setLocalTranslation(0, 0, 0);

        Material boxMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        boxGeometry.setMaterial(boxMaterial);

        return boxGeometry;
    }

    private void addRedCube(Node node){
        Box boxShape = new Box(2,2,2);
        Geometry boxGeometry = new Geometry("redCube", boxShape);

        float offset = 1f;
        boxGeometry.setLocalTranslation(new Vector3f(0.0f, 0.0f, 0.0f).add(offset, offset, offset));

        Material boxMaterial = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        boxMaterial.setColor("Color", ColorRGBA.Red);
        boxMaterial.getAdditionalRenderState().setWireframe(true);
        boxMaterial.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        boxGeometry.setMaterial(boxMaterial);

        node.attachChild(boxGeometry);
    }

    @Override
    protected void cleanup(Application application) {
        app.getRootNode().detachChild(spaceShipScene);
        app.getStateManager().detach(testSpaceShipAppState);
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    // testing commander
    private float testTimer = 0.0f;
    private final Random random = new Random();
    @Override
    public void update(float v) {
        testTimer += v;
        if (testTimer >= 0.5){
            testTimer = 0.0f;
            AbstractSpaceShipCommand command =
                    new TeleportationByCoordinatesCommand(new Vector3f(
                            random.nextInt(0,3),
                            random.nextInt(0,3),
                            random.nextInt(0,3)));
            testSpaceShipAppState.getMainControlSystem().executeCommand(command);
        }
    }
}
