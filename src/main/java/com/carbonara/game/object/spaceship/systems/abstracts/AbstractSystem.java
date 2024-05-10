package com.carbonara.game.object.spaceship.systems.abstracts;

import com.carbonara.game.object.spaceship.components.abstracts.AbstractSystemComponent;
import com.carbonara.game.object.spaceship.systems.interfaces.ISystem;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public abstract class AbstractSystem implements Control, ISystem {
    protected Logger logger;

    protected Spatial spatial;

    @Override
    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        this.spatial = spatial;

        logger = Logger.getLogger(getClass().getName());
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

    Set<AbstractSystemComponent> systemComponents = new HashSet<>();
    protected int totalSpaceUnits = 1; // кол-во условных единиц площади для размещения AbstractSystemComponent
    protected int unitsSpaceSpent = 0; // кол-во потраченых единиц площади для размещения компонетов

    @Override
    public void registerSystemComponent(AbstractSystemComponent component) {
        // если такого компонента нет в системе
        if (!systemComponents.contains(component))
            // если площадь для размещения компонента ещё осталась
            if (totalSpaceUnits-unitsSpaceSpent-component.getOccupiesUnitsSpace() >= 0){
                unitsSpaceSpent += component.getOccupiesUnitsSpace();
                systemComponents.add(component);
            } else logger.warning("There is not enough space to accommodate the \"%s\" component!".formatted(component.getName()));
        else logger.warning("You cannot place the same \"%s\" in the system twice!".formatted(component.getName()));
    }

    @Override
    public void unregisterSystemComponent(AbstractSystemComponent component) {
        // если такой компонент есть в системе
        if (systemComponents.contains(component)) {
            unitsSpaceSpent -= component.getOccupiesUnitsSpace(); // освобождаем площадь для размещения компонентов
            systemComponents.remove(component);
        } else logger.warning("This component is not registered in the system!");
    }

    @Override
    public Set<AbstractSystemComponent> getSystemComponents() {
        return systemComponents;
    }
}
