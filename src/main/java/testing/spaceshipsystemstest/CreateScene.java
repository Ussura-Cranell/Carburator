package testing.spaceshipsystemstest;

import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.object.spaceship.CreateTestSpaceShip;
import com.carbonara.game.object.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.spaceship.components.engine.Engine;
import com.carbonara.game.object.spaceship.components.reactor.Reactor;
import com.carbonara.game.object.spaceship.systems.FlightControlSystem;
import com.carbonara.game.object.spaceship.systems.ReactorControlSystem;
import com.carbonara.game.object.spaceship.systems.abstracts.AbstractSystem;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

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

        // прикрепляем сцену к главному ноду
        app.getRootNode().attachChild(spaceShipScene);

        registerSystems();
    }

    private void registerSystems(){
        // добавление системы реактора
        testSpaceShipAppState.getMainControlSystem().registerSystem(
                ReactorControlSystem.class.getName(), new ReactorControlSystem());

        // добавление системы двигателей
        testSpaceShipAppState.getMainControlSystem().registerSystem(
                FlightControlSystem.class.getName(), new FlightControlSystem());

        // добавляем к кораблю кучу реакторов
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Reactor());
        AbstractSystemComponent component =  new Reactor();
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(component);
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(component);
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Reactor());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Reactor());

        // добавляем к кораблю кучу двигателей
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());
        testSpaceShipAppState.getMainControlSystem().registerSystemComponent(new Engine());

        System.out.println("Systems:");
        for (String systemName : testSpaceShipAppState.getMainControlSystem().getSystems().keySet())
            System.out.println(systemName);

        System.out.println("\nComponents:");
        for (AbstractSystem system : testSpaceShipAppState.getMainControlSystem().getSystems().values())
            for (AbstractSystemComponent systemComponent : system.getSystemComponents())
                System.out.println(systemComponent.getName());
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
}
