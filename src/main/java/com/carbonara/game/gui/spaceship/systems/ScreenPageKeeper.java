package com.carbonara.game.gui.spaceship.systems;

import com.carbonara.game.gui.spaceship.systems.commnads.DisplaySystemGUICommand;
import com.carbonara.game.gui.spaceship.systems.flightcontrolsystempage.FlightControlSystemPage;
import com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage.ScanningControlSystemPage;
import com.carbonara.game.gui.spaceship.systems.terminalcontrolsystempage.TerminalControlSystemPage;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.object.gameobjects.categories.player.general.InteractionControl;
import com.carbonara.game.object.other.spaceship.managers.SpaceShipServiceLocator;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;

import java.util.HashMap;

public class ScreenPageKeeper {
    private final HashMap< Class<? extends AbstractSpaceshipSystemPage>, AbstractSpaceshipSystemPage> screenPages = new HashMap<>();
    public ScreenPageKeeper(){}
    public ScreenPageKeeper(float scale, Node... screenNodes) {
        if (screenNodes.length < 8) throw new RuntimeException("Not enough nodes for all systems!");

    /* размеры экранов
       1. 0.333895f, 0.236017f
       2. 0.456416f, 0.322345f
       3. 0.797655f, 0.563830f
       6. 0.563868f, 0.250746f
       7. 0.350185f, 0.317938f
       8. 0.987689f, 0.346177f
    */

        addSpaceshipSystemPage(
                WeaponControlSystemPage.class,
                new WeaponControlSystemPage(screenNodes[0], scale));
        addInteractionPanel(screenNodes[0], screenPages.get(WeaponControlSystemPage.class),
                new Vector3f(0.333895f, 0.236017f, 1.0f).divide(scale * 0.000285f));

        addSpaceshipSystemPage(
                ShieldControlSystemPage.class,
                new ShieldControlSystemPage(screenNodes[1], scale));
        addInteractionPanel(screenNodes[1], screenPages.get(ShieldControlSystemPage.class),
                new Vector3f(0.456416f, 0.322345f, 1.0f).divide(scale * 0.000285f));

        addSpaceshipSystemPage(
                ScanningControlSystemPage.class,
                new ScanningControlSystemPage(screenNodes[2], scale));
        addInteractionPanel(screenNodes[2], screenPages.get(ScanningControlSystemPage.class),
                new Vector3f(0.797655f, 0.563830f, 1.0f).divide(scale * 0.000285f));

        addSpaceshipSystemPage(
                StorageControlSystemPage.class,
                new StorageControlSystemPage(screenNodes[3], scale));
        addInteractionPanel(screenNodes[3], screenPages.get(StorageControlSystemPage.class),
                new Vector3f(0.333895f, 0.236017f, 1.0f).divide(scale * 0.000285f));

        addSpaceshipSystemPage(
                RepairControlSystemPage.class,
                new RepairControlSystemPage(screenNodes[4], scale));
        addInteractionPanel(screenNodes[4], screenPages.get(RepairControlSystemPage.class),
                new Vector3f(0.456416f, 0.322345f, 1.0f).divide(scale * 0.000285f));

        addSpaceshipSystemPage(
                ReactorControlSystemPage.class,
                new ReactorControlSystemPage(screenNodes[5], scale));
        addInteractionPanel(screenNodes[5], screenPages.get(ReactorControlSystemPage.class),
                new Vector3f(0.563868f, 0.250746f, 1.0f).divide(scale * 0.000285f));

        addSpaceshipSystemPage(
                TerminalControlSystemPage.class,
                new TerminalControlSystemPage(screenNodes[6], scale));
        addInteractionPanel(screenNodes[6], screenPages.get(TerminalControlSystemPage.class),
                new Vector3f(0.350185f, 0.317938f, 1.0f).divide(scale * 0.000285f));

        addSpaceshipSystemPage(
                FlightControlSystemPage.class,
                new FlightControlSystemPage(screenNodes[7], scale));
        addInteractionPanel(screenNodes[7], screenPages.get(FlightControlSystemPage.class),
                new Vector3f(0.987689f, 0.346177f, 1.0f).divide(scale * 0.000285f));

        SpaceShipServiceLocator.setPageKeeper(this);
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

    private void addInteractionPanel(Node point, AbstractSpaceshipSystemPage systemPage, Vector3f scale){

        // создаём панели для взаимодействия

        float width = scale.getX();
        float height = scale.getY();

        System.out.println(point.getName() + " scale: " + scale);
        Quad quad = new Quad(width, height);

        Material material = new Material(GlobalSimpleApplication.getApp().getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.FrontAndBack);

        Geometry quadGeometry = new Geometry("Quad", quad);
        quadGeometry.setMaterial(material);
        quadGeometry.setLocalTranslation(new Vector3f(0, -height, 0));
        point.attachChild(quadGeometry);

        InteractionControl interactionControl = new InteractionControl();
        quadGeometry.addControl(interactionControl);

        interactionControl.addAction("execute",
                new DisplaySystemGUICommand(systemPage));
    }

    public void cleanup(){
        screenPages.values().forEach(AbstractSpaceshipSystemPage::cleanup);
        SpaceShipServiceLocator.setPageKeeper(null);
    }
}
