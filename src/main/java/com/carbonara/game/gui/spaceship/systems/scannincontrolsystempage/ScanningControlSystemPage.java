package com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage;

import com.carbonara.game.gui.spaceship.systems.AbstractSpaceshipSystemPage;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.object.other.spaceship.managers.testing.Enemy;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;
import testing.guispaceshiptesting.GUISpaceshipTest;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ScanningControlSystemPage extends AbstractSpaceshipSystemPage {
    private Radar radar;
    private TargetPanel targetPanel;

    public ScanningControlSystemPage(Node point, float scale) {
        super(point, scale);
    }

    @Override
    public void initialize() {
        sizeX = 0.797655f * mul;
        sizeY = 0.563830f * mul;

        this.screen = new Container();
        this.screen.setPreferredSize(new Vector3f(sizeX, sizeY, 1f));

        Container labelContainer = new Container();
        labelContainer.setPreferredSize(new Vector3f(100, 50, 1));

        Label label = new Label(getClass().getSimpleName());
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(scale * 15);

        labelContainer.addChild(label);

        this.screen.addChild(labelContainer);

        radar = new Radar(new Vector2f(sizeX, sizeY));
        this.screen.attachChild(radar.getRadarContainer());

        targetPanel = new TargetPanel(new Vector2f(sizeX, sizeY));
        this.screen.attachChild(targetPanel.getTargetPanelContainer());
        // createRadar(screen);
    }

    private void createRadar(Node page){
        float sizeX = 300.0f;
        float sizeY = 300.0f;

        Container radarContainer = new Container();

        radarContainer.setPreferredSize(new Vector3f(sizeX, sizeY, 1f));
        radarContainer.setLocalTranslation(new Vector3f(sizeX/4, - sizeY/4, 1f));

        // Container labelContainer = new Container();
        // labelContainer.setPreferredSize(new Vector3f(100, 50, 1));

        Label label = new Label("RADAR");
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(scale * 15);

        Geometry box = new Geometry("box", new Box(5, 5, 1));
        box.setLocalTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
        Material material = new Material(GUISpaceshipTest.getApp().getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Red);
        box.setMaterial(material);
        radarContainer.attachChild(box);

        box = new Geometry("box", new Box(5, 5, 1));
        box.setLocalTranslation(new Vector3f(sizeX, 0.0f, 0.0f));
        box.setMaterial(material);
        radarContainer.attachChild(box);

        box = new Geometry("box", new Box(5, 5, 1));
        box.setLocalTranslation(new Vector3f(0.0f, - sizeY, 0.0f));
        box.setMaterial(material);
        radarContainer.attachChild(box);

        box = new Geometry("box", new Box(5, 5, 1));
        box.setLocalTranslation(new Vector3f(sizeX, - sizeY, 0.0f));
        box.setMaterial(material);
        radarContainer.attachChild(box);

        Geometry line = new Geometry("line", new Line(
                new Vector3f(0.0f, 0.0f, 0.0f),
                new Vector3f(sizeX, 0.0f, 0.0f)));
        line.setMaterial(material);
        radarContainer.attachChild(line);

        line = new Geometry("line", new Line(
                new Vector3f(sizeX, 0.0f, 0.0f),
                new Vector3f(sizeX, -sizeY, 0.0f)));
        line.setMaterial(material);
        radarContainer.attachChild(line);

        line = new Geometry("line", new Line(
                new Vector3f(sizeX, -sizeY, 0.0f),
                new Vector3f(0.0f, -sizeY, 0.0f)));
        line.setMaterial(material);
        radarContainer.attachChild(line);

        line = new Geometry("line", new Line(
                new Vector3f(0.0f, -sizeY, 0.0f),
                new Vector3f(0.0f, 0.0f, 0.0f)));
        line.setMaterial(material);
        radarContainer.attachChild(line);

        //labelContainer.addChild(label);
        //radarContainer.addChild(labelContainer);
        radarContainer.addChild(label);

        page.attachChild(radarContainer);
    }

    private Random random = new Random();
    private Spatial createSpatial(){
        Spatial spatial = new Geometry("box", new Box(1.0f, 1.0f, 1.0f));
        Material mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(),
                "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Red);
        mat.getAdditionalRenderState().setWireframe(true);
        spatial.setMaterial(mat);
        spatial.setLocalTranslation(
                random.nextFloat(-100f, 100f),
                random.nextFloat(-100f, 100f),
                random.nextFloat(-100f, 100f));

        return spatial;
    }

    public Radar getRadar() {
        return radar;
    }

    public TargetPanel getTargetPanel() {
        return targetPanel;
    }
}
