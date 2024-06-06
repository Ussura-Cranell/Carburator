package com.carbonara.game.object.other.spaceship.systems;

import com.carbonara.game.object.other.spaceship.managers.SpaceShipServiceLocator;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;
import com.carbonara.game.object.other.spaceship.systems.commands.AbstractSpaceShipCommand;
import com.carbonara.game.object.other.spaceship.systems.interfaces.IRegisterSystem;
import com.carbonara.game.object.other.spaceship.components.abstracts.AbstractSystemComponent;
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
    private final HashMap<Class<? extends AbstractSystem>, AbstractSystem> systems = new HashMap<>();
    Spatial spaceShipSpatial;

    @Override
    public void registerSystem(Class<? extends AbstractSystem> classSystem, AbstractSystem system) {
        /* регистрирует системы корабля */

        // если на корабле уже висит такая система, генерируется ошибка
        if (this.spaceShipSpatial.getControl(system.getClass()) != null)
            throw new RuntimeException("The controller \"%s\" has already been added!"
                    .formatted(system.getClass().getName()));

        this.spaceShipSpatial.addControl(system);
        this.systems.put(classSystem, system);
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
        // при подключении MainControlSystem к кораблю

        this.spaceShipSpatial = spatial;
        SpaceShipServiceLocator.initialize(); // инициализировать работу ServiceLocator
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
        if (systems.containsKey(systemComponent.getClassSystem()))
            systems.get(systemComponent.getClassSystem()).registerSystemComponent(systemComponent);
        else logger.warning("There is no control system for the \"%s\" component!".formatted(systemComponent.getClassSimpleName()));
    }

    public HashMap<Class<? extends AbstractSystem>, AbstractSystem> getSystems() {
        return systems;
    }

    public void executeCommand(AbstractSpaceShipCommand command){
        // проверяем подключена ли соответствующая система для выполнения команды
        if (systems.containsKey(command.getClassSystem())){
            command.setSystem(systems.get(command.getClassSystem()));
            command.execute();
        }
    }
}
