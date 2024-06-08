package com.carbonara.game.object.other.spaceship.managers;

import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceShip;

import java.util.logging.Logger;

public class SpaceShipServiceLocator {
    /*
    регистарция менеджеров энергии и ресурсов
    инициализируется вместе с кораблем
    */

    private static final Logger logger = Logger.getLogger(SpaceShipServiceLocator.class.getName());

    private static EnergyControlManager energyControlManager;

    private static AbstractSpaceShip spaceShip;

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

    public static AbstractSpaceShip getSpaceShip() {
        if (spaceShip == null) logger.warning("The class is not initialized!");
        return spaceShip;
    }

    public static void setSpaceShip(AbstractSpaceShip spaceShip) {
        SpaceShipServiceLocator.spaceShip = spaceShip;
    }
}
