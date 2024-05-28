package com.carbonara.game.managers;

public class ServiceLocatorManagers {
    private static NewPauseGameManager newPauseGameManager = new NewPauseGameManager();

    public static NewPauseGameManager getNewPauseGameManager() {
        return newPauseGameManager;
    }
}
