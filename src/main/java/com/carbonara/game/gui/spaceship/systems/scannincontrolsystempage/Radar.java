package com.carbonara.game.gui.spaceship.systems.scannincontrolsystempage;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.object.other.spaceship.managers.testing.Enemy;
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

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class Radar {
    private static final Logger logger = Logger.getLogger(Radar.class.getName());
    private Container radarContainer;
    private Container targetInconContainer; // иконка какой-то цели
    float scale = 0.0f;
    float size = 500.0f;
    float offsetX = 25; // смещение относительно леаой верхней точки
    float offsetY = 25 * 1.5f;
    Vector2f sizePage;

    public Radar(Vector2f sizePage){
        offsetY = (sizePage.getY() - size) / 2;

        radarContainer = new Container();
        radarContainer.setLocalTranslation(offsetX, -offsetY, 1);
        this.sizePage = sizePage;
        centerPoint = new Vector3f(new Vector3f(size/2, -size/2, 0.0f));
        addGrid();
        addBoundaries();
        createTargetIcon();
    }

    private void addBoundaries(){
        // добавляет границы для радара (квадратная форма)
        Geometry line;
        Material mat;
        mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);

        float radius = 2.0f;

        // верхняя граница
        line = new Geometry("line", new Box(size/2, radius, 1));
        line.setLocalTranslation(size/2, 0.0f, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(size/2, radius, 1));
        line.setLocalTranslation(size/2, -size, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(radius, size/2, 1));
        line.setLocalTranslation(0.0f, -size/2, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(radius, size/2, 1));
        line.setLocalTranslation(size, -size/2, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);

        // горизонтальная центральная граница
        line = new Geometry("line", new Box(radius, size/2, 1));
        line.setLocalTranslation(size/2, -size/2, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);

        // вертикальная центральная граница
        line = new Geometry("line", new Box(size/2, radius, 1));
        line.setLocalTranslation(size/2, -size/2, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);

        Material mat2 = mat.clone();
        mat2.setColor("Color", ColorRGBA.Red);

        // точка слева сверху
        line = new Geometry("line", new Box(radius*2, radius*2, 1));
        line.setLocalTranslation(0.0f, 0.0f, 0.0f);
        line.setMaterial(mat2);
        radarContainer.attachChild(line);

        // точка по центру
        line = new Geometry("line", new Box(radius*4, radius*4, 1));
        line.setLocalTranslation(centerPoint);
        line.setMaterial(mat2);
        radarContainer.attachChild(line);

        // точка снизу справа
        line = new Geometry("line", new Box(radius*2, radius*2, 1));
        line.setLocalTranslation(size, -size, 0.0f);
        line.setMaterial(mat2);
        radarContainer.attachChild(line);
    }

    private void addGrid(){

        int numberGridlines = 12;
        float increments = size / numberGridlines;
        float radius = 1.0f;

        Geometry line;
        Material mat1;
        Material mat2;
        mat1 = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Yellow);
        mat2 = mat1.clone();
        mat1.setColor("Color", ColorRGBA.Yellow);

        for (int i = 0; i < numberGridlines; i++) {
            // горизонтальные линии
            line = new Geometry("line", new Box(size / 2, radius, 1));
            line.setLocalTranslation(size / 2, 0.0f - increments * i, 0.0f);
            line.setMaterial(mat1);
            radarContainer.attachChild(line);

            // вертикальные линии
            line = new Geometry("line", new Box(radius, size / 2, 1));
            line.setLocalTranslation(0.0f + increments * i , -size / 2, 0.0f);
            line.setMaterial(mat2);
            radarContainer.attachChild(line);
        }

    }

    private void createTargetIcon(){
        float size = 25.0f;
        float radius = 7.0f;
        targetInconContainer = new Container();
        targetInconContainer.setPreferredSize(new Vector3f(size, size, 1.0f));

        Geometry line;
        Material mat1, mat2;
        mat1 = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Black);
        mat2 = mat1.clone();
        mat2.setColor("Color", ColorRGBA.Red);

        line = new Geometry("line", new Box(size/2, size/2, 1));
        line.rotate(0, 0, FastMath.DEG_TO_RAD*45);
        line.setMaterial(mat1);
        targetInconContainer.attachChild(line);

        line = new Geometry("line", new Box(size/2-radius/2, size/2-radius/2, 1));
        line.rotate(0, 0, FastMath.DEG_TO_RAD*45);
        line.setMaterial(mat2);
        targetInconContainer.attachChild(line);
    }

    public Container getRadarContainer() {
        return radarContainer;
    }

    class Target {
        Target(Spatial iconContainer, Label distanceLabel){
            this.distanceLabel = distanceLabel;
            this.iconContainer = iconContainer;
        }
        private Spatial iconContainer;
        private Label distanceLabel;

        public Spatial getIconContainer() {
            return iconContainer;
        }

        public void setIconContainer(Container iconContainer) {
            this.iconContainer = iconContainer;
        }

        public Label getDistanceLabel() {
            return distanceLabel;
        }

        public void setDistanceLabel(Label distanceLabel) {
            this.distanceLabel = distanceLabel;
        }
    }
    Vector3f centerPoint;
    Set<Target> targets = new HashSet<>();

    public void updateTargets(Vector3f spaceshipPosition,
                              Set<Enemy> enemies,
                              float maxDisplayDistance,
                              float displayScale) {

        float offsetLabelDistance = 20.0f;
        float sizeLabelY = 10;
        float sizeLabelX = sizeLabelY * 4;

        maxDisplayDistance = maxDisplayDistance / displayScale;

        for (Target target : targets) {
            radarContainer.detachChild(target.getDistanceLabel());
            radarContainer.detachChild(target.getIconContainer());
        }
        targets.clear();

        for (Enemy enemy : enemies) {
            // Получаем вектор положения врага относительно корабля
            Vector3f relativePosition = enemy.getModel().getLocalTranslation().subtract(spaceshipPosition);

            // Вычисляем расстояние до врага
            float distance = relativePosition.length();

            // Проверяем, находится ли враг в пределах максимального расстояния отображения
            //if (distance <= maxDisplayDistance)
                if (FastMath.abs(relativePosition.getX()) <= maxDisplayDistance &&
                        FastMath.abs(relativePosition.getY()) <= maxDisplayDistance &&
                        FastMath.abs(relativePosition.getZ()) <= maxDisplayDistance){
                // Масштабируем позицию для отображения на радаре
                Vector3f displayPosition = relativePosition.mult(displayScale);

                // Создаем иконку для отображения на радаре
                Spatial icon = targetInconContainer.clone();
                icon.setLocalTranslation(
                        centerPoint.add(
                                displayPosition.getX(),
                                displayPosition.getZ(), 1.0f));

                // Создаем метку с расстоянием
                Label label = new Label("%.2fm".formatted(distance));
                label.setFontSize(17.0f);
                label.setPreferredSize(new Vector3f(sizeLabelX, sizeLabelY, 1.0f));
                label.setLocalTranslation(centerPoint.add(
                        displayPosition.getX(),
                        displayPosition.getZ(), 1.0f).add(
                        offsetLabelDistance, sizeLabelY, 0.0f));
                label.setTextHAlignment(HAlignment.Center);
                label.setTextVAlignment(VAlignment.Center);

                // Создаем объект Target и добавляем его на радар
                Target target = new Target(icon, label);

                radarContainer.attachChild(target.getIconContainer());
                radarContainer.attachChild(target.getDistanceLabel());

                targets.add(target);
            }
        }
    }
}
