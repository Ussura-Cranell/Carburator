package com.carbonara.game.object.other.spaceship.systems;

import com.carbonara.game.gui.spaceship.systems.flightcontrolsystempage.FlightControlSystemPage;
import com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage.ScanningControlSystemPage;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;
import com.carbonara.game.object.other.spaceship.managers.SpaceShipServiceLocator;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;
import com.carbonara.game.scene.OuterSpaceScene;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class FlightControlSystem extends AbstractSystem {
    /* система управления полётом */

    private FlightControlSystemPage flightControlSystemPage;
    public FlightControlSystem(){
        flightControlSystemPage = SpaceShipServiceLocator.getPageKeeper().getSpaceshipSystemPage(FlightControlSystemPage.class);
    }

    public void teleportationByCoordinates(Vector3f coordinates){
        this.spatial.setLocalTranslation(coordinates);
        logger.info("Spatial \"%s\" moved by coordinates (%s)".formatted(
                this.spatial.getName(), coordinates.toString()));
    }

    private Vector3f direction = Vector3f.ZERO;
    private float speed = 15.0f;
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public void moveDirection(Vector3f direction, float tpf){
        spatial.setLocalTranslation(spatial.getLocalTranslation().add(direction.normalize().mult(tpf * speed)));
    }

    @Override
    public void update(float v) {
        if (!getGamePause()){
            flightControlSystemPage.update(v);
            moveDirection(direction, v);
        }
    }
}
