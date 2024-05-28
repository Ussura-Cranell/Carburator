package com.carbonara.game.object.other.spaceship.managers;

public class SpaceShipServiceLocator {
    /* регистарция менеджеров энергии и ресурсов */
    private static EnergyControlManager energyControlManager;

    public static void initialize(){
        energyControlManager = new EnergyControlManager();
    }
    public static EnergyControlManager getEnergyControlManager() {
        return energyControlManager;
    }
}
