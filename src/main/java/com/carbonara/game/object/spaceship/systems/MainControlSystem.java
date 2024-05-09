package com.carbonara.game.object.spaceship.systems;

import com.carbonara.game.object.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.spaceship.systems.interfaces.IRegisterSystem;
import com.carbonara.game.object.spaceship.systems.abstracts.AbstractSystem;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class MainControlSystem implements Control, IRegisterSystem {
    /* отвечает за управление другими системами */

    private static final Logger logger = Logger.getLogger(MainControlSystem.class.getName());
    private final HashMap<String, AbstractSystem> systems = new HashMap<>();
    Spatial spaceShipSpatial;
    @Override
    public void registerSystem(String nameSystem, AbstractSystem system) {
        /* регистрирует системы корабля */

        // если на корабле уже висит такая система, генерируется ошибка
        if (this.spaceShipSpatial.getControl(system.getClass()) != null)
            throw new RuntimeException("The controller \"%s\" has already been added!"
                    .formatted(system.getClass().getName()));

        this.spaceShipSpatial.addControl(system);
        this.systems.put(nameSystem, system);
    }

    public void unregisterSystem(String nameSystem){
        if (this.systems.get(nameSystem)!=null) {
            this.spaceShipSpatial.removeControl(this.systems.get(nameSystem));
            this.systems.remove(nameSystem);
        } else logger.warning("The \"%s\" system is not registered!".formatted(nameSystem));
    }

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        this.spaceShipSpatial = spatial;
    }

    @Override
    public void update(float v) {

    }

    @Override
    public void render(RenderManager renderManager, ViewPort viewPort) {

    }

    @Override
    public void write(JmeExporter jmeExporter) throws IOException {

    }

    @Override
    public void read(JmeImporter jmeImporter) throws IOException {

    }

    public void registerSystemComponent(AbstractSystemComponent systemComponent){
        if (systems.containsKey(systemComponent.getTypeSystem()))
            systems.get(systemComponent.getTypeSystem()).registerSystemComponent(systemComponent);
        else logger.warning("There is no control system for the \"%s\" component!".formatted(systemComponent.getName()));
    }

    public HashMap<String, AbstractSystem> getSystems() {
        return systems;
    }
}
