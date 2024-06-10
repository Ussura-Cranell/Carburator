package com.carbonara.game.object.other.spaceship.systems;

import com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage.ScanningControlSystemPage;
import com.carbonara.game.object.other.spaceship.managers.SpaceShipServiceLocator;
import com.carbonara.game.object.other.spaceship.systems.abstracts.AbstractSystem;

public class ScanningControlSystem extends AbstractSystem{
    private ScanningControlSystemPage scanningControlSystemPage;
    public ScanningControlSystem(){
        scanningControlSystemPage = SpaceShipServiceLocator.getPageKeeper().getSpaceshipSystemPage(ScanningControlSystemPage.class);
    }

    @Override
    public void update(float v) {

        if (!getGamePause()){

            scanningControlSystemPage.update(v);
        }
    }
}
