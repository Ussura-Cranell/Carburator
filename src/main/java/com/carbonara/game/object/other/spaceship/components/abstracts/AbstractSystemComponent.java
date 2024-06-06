package com.carbonara.game.object.other.spaceship.components.abstracts;

import com.carbonara.game.object.other.spaceship.components.interfaces.ISystemComponent;

public abstract class AbstractSystemComponent implements ISystemComponent {

    protected int occupiesUnitsSpace = 1;   // сколько компонент занимает площади
    protected int occupiesUnitsEnergy = 1;  // сколько энергии нужно для работы

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


}
