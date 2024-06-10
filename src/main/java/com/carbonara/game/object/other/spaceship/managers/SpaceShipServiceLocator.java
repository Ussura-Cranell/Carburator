package com.carbonara.game.object.other.spaceship.managers;

import com.carbonara.game.gui.spaceship.systems.ScreenPageKeeper;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;

import java.util.logging.Logger;

public class SpaceShipServiceLocator {
    /*
    регистарция менеджеров энергии и ресурсов
    инициализируется вместе с кораблем
    */

    private static final Logger logger = Logger.getLogger(SpaceShipServiceLocator.class.getName());

    private static EnergyControlManager energyControlManager;

    private static ScreenPageKeeper pageKeeper;

    public static void initialize(){
        energyControlManager = new EnergyControlManager();

    }
    public static void cleanup(){
        energyControlManager = null;
    }
    public static EnergyControlManager getEnergyControlManager() {
        if (energyControlManager == null) logger.warning("The class is not initialized!");
        return energyControlManager;
    }

    public static ScreenPageKeeper getPageKeeper() {
        return pageKeeper;
    }

    public static void setPageKeeper(ScreenPageKeeper pageKeeper) {
        SpaceShipServiceLocator.pageKeeper = pageKeeper;
    }
}
