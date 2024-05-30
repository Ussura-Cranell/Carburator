package com.carbonara.game.gui.spaceship.systems;

import com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage.ScanningControlSystemPage;
import com.carbonara.game.gui.spaceship.systems.terminalcontrolsystempage.TerminalControlSystemPage;
import com.jme3.scene.Node;

import java.util.HashMap;

public class ScreenPageKeeper {
    private final HashMap< Class<? extends AbstractSpaceshipSystemPage>, AbstractSpaceshipSystemPage> screenPages = new HashMap<>();
    public ScreenPageKeeper(){}
    public ScreenPageKeeper(float scale, Node... screenNodes){
        if (screenNodes.length < 8) throw new RuntimeException("Not enough nodes for all systems!");
        addSpaceshipSystemPage(
                WeaponControlSystemPage.class,
                new WeaponControlSystemPage(screenNodes[0], scale));

        addSpaceshipSystemPage(
                ShieldControlSystemPage.class,
                new ShieldControlSystemPage(screenNodes[1], scale));

        addSpaceshipSystemPage(
                ScanningControlSystemPage.class,
                new ScanningControlSystemPage(screenNodes[2], scale));

        addSpaceshipSystemPage(
                StorageControlSystemPage.class,
                new StorageControlSystemPage(screenNodes[3], scale));

        addSpaceshipSystemPage(
                RepairControlSystemPage.class,
                new RepairControlSystemPage(screenNodes[4], scale));

        addSpaceshipSystemPage(
                ReactorControlSystemPage.class,
                new ReactorControlSystemPage(screenNodes[5], scale));

        addSpaceshipSystemPage(
                TerminalControlSystemPage.class,
                new TerminalControlSystemPage(screenNodes[6], scale));

        addSpaceshipSystemPage(
                FlightControlSystemPage.class,
                new FlightControlSystemPage(screenNodes[7], scale));
    }
    public void addSpaceshipSystemPage(
            Class<? extends AbstractSpaceshipSystemPage> classSpaceshipSystemPage,
            AbstractSpaceshipSystemPage abstractSpaceshipSystemPage){
        screenPages.put(classSpaceshipSystemPage, abstractSpaceshipSystemPage);
    }

    public <T extends AbstractSpaceshipSystemPage> T getSpaceshipSystemPage(Class<? extends AbstractSpaceshipSystemPage> classSpaceshipSystemPage){
        return (T) screenPages.get(classSpaceshipSystemPage);
    }

    public void soutAllPages(){
        for (AbstractSpaceshipSystemPage page : screenPages.values()){
            System.out.println(page.getClass().getSimpleName());
        }
    }

}
