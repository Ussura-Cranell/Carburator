package com.carbonara.game.object.other.spaceship.systems;

import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;
import com.jme3.math.Vector3f;

public class FlightControlSystem extends AbstractSystem {
    /* система управления полётом */

    public void teleportationByCoordinates(Vector3f coordinates){
        this.spatial.setLocalTranslation(coordinates);
        logger.info("Spatial \"%s\" moved by coordinates (%s)".formatted(
                this.spatial.getName(), coordinates.toString()));
    }
}
