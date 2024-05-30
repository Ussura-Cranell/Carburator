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

    private Random random = new Random();

    public Radar getRadar() {
        return radar;
    }

    public TargetPanel getTargetPanel() {
        return targetPanel;
    }
}
