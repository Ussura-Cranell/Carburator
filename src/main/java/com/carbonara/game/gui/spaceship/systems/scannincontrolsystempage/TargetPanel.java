package com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.object.other.enemy.abstracts.AbstractEnemy;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.HAlignment;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.VAlignment;

import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class TargetPanel {
    private static final Logger logger = Logger.getLogger(TargetPanel.class.getName());

    private static final float SIZE = 500.0f - 25 * 2;
    private static final float OFFSET_X = 25 * 2 + 500.0f;
    private static float OFFSET_Y = 123;
    private static final float MAP_BOUNDARY = 500.0f;

    private Container targetPanelContainer;
    private Container targetIconContainer;
    private Container targetBoxContainer;
    private Vector2f sizePage;
    private List<Target> activeTargets = new ArrayList<>();
    private List<Target> inactiveTargets = new ArrayList<>();

    public TargetPanel(Vector2f sizePage) {
        this.sizePage = sizePage;
        targetPanelContainer = new Container();

        if (OFFSET_Y == 123) OFFSET_Y = (sizePage.getY() - SIZE) / 2 - 25.0f;

        targetPanelContainer.setLocalTranslation(OFFSET_X, -OFFSET_Y, 1);
        initialize();
    }

    private void initialize() {
        addBoundaries();
        createBoxContainer();
        createTargetIcon();
    }

    private void createTargetIcon() {
        targetIconContainer = createIconContainer(25.0f, 7.0f, ColorRGBA.Black, ColorRGBA.Red);
    }

    private Container createIconContainer(float size, float radius, ColorRGBA color1, ColorRGBA color2) {
        Container iconContainer = new Container();
        iconContainer.setPreferredSize(new Vector3f(size, size, 1.0f));

        Material mat1 = createMaterial(color1);
        Material mat2 = createMaterial(color2);

        iconContainer.attachChild(createRotatedGeometry(size / 2, mat1));
        iconContainer.attachChild(createRotatedGeometry(size / 2 - radius / 2, mat2));

        return iconContainer;
    }

    private Material createMaterial(ColorRGBA color) {
        Material mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        return mat;
    }

    private Geometry createRotatedGeometry(float size, Material material) {
        Geometry geom = new Geometry("line", new Box(size, size, 1));
        geom.rotate(0, 0, FastMath.DEG_TO_RAD * 45);
        geom.move(0, 2*5, 0);
        geom.setMaterial(material);
        return geom;
    }

    private void addBoundaries() {
        float radius = 2.0f;
        float sizeX = SIZE / 2;
        float sizeY = SIZE + 50.0f;

        Material mat = createMaterial(ColorRGBA.Yellow);

        // Add boundaries
        addBoundary(sizeX / 2, radius, mat, sizeX / 2, 0.0f);
        addBoundary(sizeX / 2, radius, mat, sizeX / 2, -sizeY);
        addBoundary(radius, sizeY / 2, mat, 0.0f, -sizeY / 2);
        addBoundary(radius, sizeY / 2, mat, sizeX, -sizeY / 2);

        // Add corner points
        Material mat2 = createMaterial(ColorRGBA.Red);
        addBoundary(radius * 2, radius * 2, mat2, 0.0f, 0.0f);
        addBoundary(radius * 2, radius * 2, mat2, sizeX, -sizeY);
    }

    private void addBoundary(float width, float height, Material mat, float posX, float posY) {
        Geometry line = new Geometry("line", new Box(width, height, 1));
        line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        targetPanelContainer.attachChild(line);
    }

    private void createBoxContainer() {
        float sizeX = SIZE / 2;
        float sizeY = 30.0f;
        float radius = 1.5f;

        targetBoxContainer = new Container();
        targetBoxContainer.setPreferredSize(new Vector3f(sizeX, 50.0f, 1.0f));

        Material mat = createMaterial(ColorRGBA.Yellow);

        // Add box boundaries
        addBoundaryToBoxContainer(sizeX / 2 - sizeY / 4 + radius, radius, mat, sizeX / 2 - sizeY / 4 - radius / 2, radius);
        addBoundaryToBoxContainer(sizeX / 2 - sizeY / 4 + radius, radius, mat, sizeX / 2 - sizeY / 4 - radius / 2, -sizeY - radius * 2);
        addBoundaryToBoxContainer(radius, sizeY / 2 + radius, mat, -radius / 2, -sizeY / 2);
        addBoundaryToBoxContainer(radius, sizeY / 2 + radius, mat, sizeX - sizeY / 2 - radius / 2, -sizeY / 2);

        targetBoxContainer.setLocalTranslation(new Vector3f(sizeY / 4 + radius, -sizeY / 4 - radius, 1.0f));
    }

    private void addBoundaryToBoxContainer(float width, float height, Material mat, float posX, float posY) {
        Geometry line = new Geometry("line", new Box(width, height, 1));
        line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        targetBoxContainer.attachChild(line);
    }

    public void updateTargets(Vector3f spaceshipPosition, List<AbstractEnemy> enemies) {
        clearOldTargets();

        float stepY = 43.0f; // Высота каждого элемента плюс некоторое расстояние для отступа
        float currentY = 0.0f; // Начальная координата Y

        for (int i = 0; i < enemies.size() && i < 11; i++) {
            AbstractEnemy enemy = enemies.get(i);
            Target target = getTarget();
            updateTarget(target, spaceshipPosition, enemy, currentY);
            currentY -= stepY;
        }
    }

    private void clearOldTargets() {
        for (Target target : activeTargets) {
            targetPanelContainer.detachChild(target.getDistanceLabel());
            targetPanelContainer.detachChild(target.getIconContainer());
            targetPanelContainer.detachChild(target.getNameTargetLabel());
            targetPanelContainer.detachChild(target.getBoxContainer());
            inactiveTargets.add(target);
        }
        activeTargets.clear();
    }

    private Target getTarget() {
        if (inactiveTargets.isEmpty()) {
            return new Target();
        } else {
            return inactiveTargets.remove(inactiveTargets.size() - 1);
        }
    }

    private void updateTarget(Target target, Vector3f spaceshipPosition, AbstractEnemy enemy, float currentY) {
        target.getNameTargetLabel().setText(enemy.getClass().getSimpleName());
        target.getNameTargetLabel().setLocalTranslation(50.0f, currentY - 12.5f, 0.0f);

        target.getIconContainer().setLocalTranslation(26.0f, currentY - 25, 0.0f);

        float distance = spaceshipPosition.distance(enemy.getPosition());
        // target.getDistanceLabel().setText(String.format("%.3fm", distance));
        target.getDistanceLabel().setText(String.format("%.3fm", distance));
        target.getDistanceLabel().setLocalTranslation(calculateDistanceLabelPosition(distance), currentY - 12.5f, 0.0f);

        target.getBoxContainer().setLocalTranslation(0, currentY, 0.0f);

        attachTargetElements(target);
        activeTargets.add(target);
    }

    private Label createLabel(String text, float posX, float posY) {
        Label label = new Label(text);
        label.setTextHAlignment(HAlignment.Center);
        label.setTextVAlignment(VAlignment.Center);
        label.setFontSize(15);
        label.setLocalTranslation(posX, posY, 0.0f);
        return label;
    }

    private float calculateDistanceLabelPosition(float distance) {
        int countChar = String.format("%.3fm", distance).length();
        return SIZE / 2 - 25.0f / 2 - 15.0f / 2 * countChar - 4;
    }

    private void attachTargetElements(Target target) {
        targetPanelContainer.attachChild(target.getNameTargetLabel());
        targetPanelContainer.attachChild(target.getBoxContainer());
        targetPanelContainer.attachChild(target.getIconContainer());
        targetPanelContainer.attachChild(target.getDistanceLabel());
    }

    public Container getTargetPanelContainer() {
        return targetPanelContainer;
    }

    class Target {
        private Label nameTargetLabel;
        private Container boxContainer;
        private Spatial iconContainer;
        private Label distanceLabel;

        Target() {
            this.nameTargetLabel = createLabel("", 0, 0);
            this.boxContainer = (Container) targetBoxContainer.clone();
            this.iconContainer = targetIconContainer.clone();
            this.distanceLabel = createLabel("", 0, 0);
        }

        public Label getNameTargetLabel() {
            return nameTargetLabel;
        }

        public Container getBoxContainer() {
            return boxContainer;
        }

        public Spatial getIconContainer() {
            return iconContainer;
        }

        public Label getDistanceLabel() {
            return distanceLabel;
        }
    }
}

