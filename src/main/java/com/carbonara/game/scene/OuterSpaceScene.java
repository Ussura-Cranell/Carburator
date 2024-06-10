package com.carbonara.game.scene;

import com.carbonara.game.gui.spaceship.systems.ScreenPageKeeper;
import com.carbonara.game.gui.spaceship.systems.commnads.CreateOneRandomEnemyCommand;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.object.other.EvilClass;
import com.carbonara.game.object.other.spaceship.CreateTestSpaceship;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;
import com.carbonara.game.object.other.spaceship.managers.SpaceShipServiceLocator;
import com.carbonara.game.object.other.spaceship.systems.FlightControlSystem;
import com.carbonara.game.object.other.spaceship.systems.ScanningControlSystem;
import com.carbonara.game.object.other.spaceship.systems.TerminalControlSystem;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class OuterSpaceScene {
    private Node scene;
    public OuterSpaceScene(){

        scene = new Node("OuterSpaceScene");

        initializeSpaceshipSystems();
    }

    public void initialize(){
        // EvilClass.initialize();
    }

    public Node createScene() {
        return scene;
    }

    private Spatial loadSpaceshipSpatial() {
        Box testingBoxShape = new Box(1, 1, 1);
        Geometry testingBoxGeometry = new Geometry("spaceshipSpatial", testingBoxShape);
        testingBoxGeometry.move(0, 10, 0);
        Material testingBoxMaterial = new Material(GlobalSimpleApplication.getApp().getAssetManager(),
                "Common/MatDefs/Misc/ShowNormals.j3md");
        testingBoxMaterial.getAdditionalRenderState().setWireframe(true);
        testingBoxGeometry.setMaterial(testingBoxMaterial);
        return testingBoxGeometry;
    }
    private static Spatial spaceshipSpatial;
    private void initializeSpaceshipSystems(){

        spaceshipSpatial = loadSpaceshipSpatial();

        AbstractSpaceship spaceship = new CreateTestSpaceship(
                spaceshipSpatial,
                scene);

        spaceship.getMainControlSystem().registerSystem(new TerminalControlSystem());
        spaceship.getMainControlSystem().registerSystem(new ScanningControlSystem());
        spaceship.getMainControlSystem().registerSystem(new FlightControlSystem());

        // УПРАВЛЕНИЕ ТЕРМИНАЛОМ
        ScreenPageKeeper pageKeeper = SpaceShipServiceLocator.getPageKeeper();

        System.out.println(spaceship);
    }

    public void cleanup(){
        AbstractSpaceship.getAbstractSpaceShip().cleanup();
        EvilClass.cleanup();
    }

    public static Spatial getSpaceshipSpatial() {
        return spaceshipSpatial;
    }
}

