package com.carbonara.game.object.spaceship.components.abstracts;

import com.carbonara.game.object.spaceship.components.interfaces.ISystemComponent;

public abstract class AbstractSystemComponent implements ISystemComponent {

    protected int occupiesUnitsSpace = 1;   // сколько компонент занимает площади

    @Override
    public int getOccupiesUnitsSpace() {
        return this.occupiesUnitsSpace;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
