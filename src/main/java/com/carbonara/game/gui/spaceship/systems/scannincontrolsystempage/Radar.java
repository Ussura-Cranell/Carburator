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


import java.util.Set;
import java.util.logging.Logger;
import com.simsilica.lemur.style.ElementId;
import java.util.ArrayList;
import java.util.List;

public class Radar {
    private static final Logger logger = Logger.getLogger(Radar.class.getName());

    private Container radarContainer;
    private Container targetIconContainer; // иконка какой-то цели
    private Vector2f sizePage;
    private Vector3f centerPoint;
    private List<Target> activeTargets = new ArrayList<>();
    private List<Target> inactiveTargets = new ArrayList<>();

    private float scale = 0.0f;
    private float size = 500.0f;
    private float offsetX = 25; // смещение относительно леаой верхней точки
    private float offsetY = 25 * 1.5f;

    public Radar(Vector2f sizePage) {
        this.sizePage = sizePage;
        this.offsetY = (sizePage.getY() - size) / 2;

        radarContainer = new Container();
        radarContainer.setLocalTranslation(offsetX, -offsetY, 1);

        centerPoint = new Vector3f(size / 2, -size / 2, 0.0f);

        addGrid();
        addBoundaries();
        createTargetIcon();
    }

    private void addBoundaries() {
        float radius = 2.0f;
        Material mat = createMaterial(ColorRGBA.Yellow);

        addBoundary(size / 2, radius, mat, size / 2, 0.0f);           // верхняя граница
        addBoundary(size / 2, radius, mat, size / 2, -size);           // нижняя граница
        addBoundary(radius, size / 2, mat, 0.0f, -size / 2);           // левая граница
        addBoundary(radius, size / 2, mat, size, -size / 2);           // правая граница
        addBoundary(radius, size / 2, mat, size / 2, -size / 2);       // горизонтальная центральная граница
        addBoundary(size / 2, radius, mat, size / 2, -size / 2);       // вертикальная центральная граница

        Material mat2 = createMaterial(ColorRGBA.Red);

        addBoundary(radius * 2, radius * 2, mat2, 0.0f, 0.0f);         // точка слева сверху
        addBoundary(radius * 4, radius * 4, mat2, centerPoint);        // точка по центру
        addBoundary(radius * 2, radius * 2, mat2, size, -size);        // точка снизу справа
    }

    private void addBoundary(float width, float height, Material mat, float posX, float posY) {
        Geometry line = new Geometry("line", new Box(width, height, 1));
        line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);
    }

    private void addBoundary(float width, float height, Material mat, Vector3f pos) {
        Geometry line = new Geometry("line", new Box(width, height, 1));
        line.setLocalTranslation(pos);
        line.setMaterial(mat);
        radarContainer.attachChild(line);
    }

    private void addGrid() {
        int numberGridlines = 12;
        float increments = size / numberGridlines;
        float radius = 1.0f;

        Material mat1 = createMaterial(ColorRGBA.Yellow);
        Material mat2 = createMaterial(ColorRGBA.Yellow);

        for (int i = 0; i < numberGridlines; i++) {
            addGridLine(size / 2, radius, mat1, size / 2, 0.0f - increments * i);      // горизонтальные линии
            addGridLine(radius, size / 2, mat2, 0.0f + increments * i, -size / 2);     // вертикальные линии
        }
    }

    private void addGridLine(float width, float height, Material mat, float posX, float posY) {
        Geometry line = new Geometry("line", new Box(width, height, 1));
        line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);
    }

    private void createTargetIcon() {
        float size = 25.0f;
        float radius = 7.0f;

        targetIconContainer = new Container();
        targetIconContainer.setPreferredSize(new Vector3f(size, size, 1.0f));

        Material mat1 = createMaterial(ColorRGBA.Black);
        Material mat2 = createMaterial(ColorRGBA.Red);

        addRotatedIcon(size / 2, mat1);
        addRotatedIcon(size / 2 - radius / 2, mat2);
    }

    private void addRotatedIcon(float size, Material mat) {
        Geometry line = new Geometry("line", new Box(size, size, 1));
        line.rotate(0, 0, FastMath.DEG_TO_RAD * 45);
        line.setMaterial(mat);
        targetIconContainer.attachChild(line);
    }

    private Material createMaterial(ColorRGBA color) {
        Material mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        return mat;
    }

    public Container getRadarContainer() {
        return radarContainer;
    }

    public void updateTargets(Vector3f spaceshipPosition, Set<AbstractEnemy> enemies, float maxDisplayDistance, float displayScale) {
        clearOldTargets();

        maxDisplayDistance = maxDisplayDistance / displayScale;

        for (AbstractEnemy enemy : enemies) {
            Vector3f relativePosition = enemy.getPosition().subtract(spaceshipPosition);
            float distance = relativePosition.length();

            if (isWithinDisplayRange(relativePosition, maxDisplayDistance)) {
                Vector3f displayPosition = relativePosition.mult(displayScale);
                Target target = getTarget();

                updateTarget(target, displayPosition, distance);
                activeTargets.add(target);
            }
        }
    }

    private void clearOldTargets() {
        for (Target target : activeTargets) {
            radarContainer.detachChild(target.getDistanceLabel());
            radarContainer.detachChild(target.getIconContainer());
            inactiveTargets.add(target);
        }
        activeTargets.clear();
    }

    private boolean isWithinDisplayRange(Vector3f relativePosition, float maxDisplayDistance) {
        return FastMath.abs(relativePosition.getX()) <= maxDisplayDistance &&
                FastMath.abs(relativePosition.getY()) <= maxDisplayDistance &&
                FastMath.abs(relativePosition.getZ()) <= maxDisplayDistance;
    }

    private Target getTarget() {
        if (inactiveTargets.isEmpty()) {
            return new Target();
        } else {
            return inactiveTargets.remove(inactiveTargets.size() - 1);
        }
    }

    private void updateTarget(Target target, Vector3f displayPosition, float distance) {
        float offsetLabelDistance = 20.0f;
        float sizeLabelY = 10;
        float sizeLabelX = sizeLabelY * 4;

        target.getIconContainer().setLocalTranslation(centerPoint.add(displayPosition.getX(), displayPosition.getZ(), 1.0f));

        target.getDistanceLabel().setText(String.format("%.2fm", distance));
        target.getDistanceLabel().setFontSize(17.0f);
        target.getDistanceLabel().setPreferredSize(new Vector3f(sizeLabelX, sizeLabelY, 1.0f));
        target.getDistanceLabel().setLocalTranslation(centerPoint.add(displayPosition.getX(), displayPosition.getZ(), 1.0f).add(offsetLabelDistance, sizeLabelY, 0.0f));
        target.getDistanceLabel().setTextHAlignment(HAlignment.Center);
        target.getDistanceLabel().setTextVAlignment(VAlignment.Center);

        radarContainer.attachChild(target.getIconContainer());
        radarContainer.attachChild(target.getDistanceLabel());
    }

    class Target {
        private Spatial iconContainer;
        private Label distanceLabel;

        Target() {
            this.iconContainer = targetIconContainer.clone();
            this.distanceLabel = new Label("", new ElementId("distanceLabel"), "glass");
        }

        public Spatial getIconContainer() {
            return iconContainer;
        }

        public Label getDistanceLabel() {
            return distanceLabel;
        }

        @Override
        public String toString() {
            return "Target{" +
                    "iconContainer=" + iconContainer +
                    ", distanceLabel=" + distanceLabel +
                    '}';
        }
    }
}
