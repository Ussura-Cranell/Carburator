package com.carbonara.game.object.other.spaceship.components.abstracts;

import com.carbonara.game.object.other.spaceship.components.interfaces.ISystemComponent;

import java.util.logging.Logger;

public abstract class AbstractSystemComponent implements ISystemComponent {

    protected int occupiesUnitsSpace = 1;   // сколько компонент занимает площади
    protected int occupiesUnitsEnergy = 1;  // сколько энергии нужно для работы
    protected int levelImprovement = 0; // уровень улучшений

    protected static Logger logger = Logger.getLogger(AbstractSystemComponent.class.getName());

    protected String name;

    @Override
    public int getOccupiesUnitsSpace() {
        return this.occupiesUnitsSpace;
    }

    @Override
    public int getOccupiesUnitsEnergy() {
        return this.occupiesUnitsEnergy;
    }

    @Override
    public String getClassSimpleName() {
        return getClass().getSimpleName();
    }

    public void setOccupiesUnitsSpace(int occupiesUnitsSpace) {
        this.occupiesUnitsSpace = occupiesUnitsSpace;
    }

    public void setOccupiesUnitsEnergy(int occupiesUnitsEnergy) {
        this.occupiesUnitsEnergy = occupiesUnitsEnergy;
    }

    public String getName() {
        return name;
    }

    @Override
    public void update(float v) {
        //logger.info("update");
    }

    @Override
    public void initialize() {

    }

    public void improve(){
        levelImprovement += 1;
    }
}
