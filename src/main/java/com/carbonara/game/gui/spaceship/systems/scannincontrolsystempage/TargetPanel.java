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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class TargetPanel {
    private static final Logger logger = Logger.getLogger(TargetPanel.class.getName());
    private Container targetPanelContainer;
    private Container targetInconContainer; // иконка какой-то цели
    private Container targetboxContainer;
    float scale = 0.0f;
    float size = 500.0f - 25*2;
    float offsetX = 25; // смещение относительно леаой верхней точки
    float offsetY = 25 * 1.5f;
    Vector2f sizePage;
    public TargetPanel(Vector2f sizePage){
        offsetX = offsetX * 2 + 500.0f;
        offsetY = (sizePage.getY() - size) / 2 - 25.0f;
        targetPanelContainer = new Container();
        targetPanelContainer.setLocalTranslation(offsetX, -offsetY, 1);
        this.sizePage = sizePage;
        addBoundaries();
        createBoxContainer();
        createTargetIcon();
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

    float sizeX = size/2;
    float sizeY = size + 50.0f;

    private void addBoundaries(){
        // добавляет границы для радара (квадратная форма)
        Geometry line;
        Material mat;
        mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);

        float radius = 2.0f;
        sizeX = size/2;
        sizeY = size + 50.0f;

        // верхняя граница
        line = new Geometry("line", new Box(sizeX/2, radius, 1));
        line.setLocalTranslation(sizeX/2, 0.0f, 0.0f);
        line.setMaterial(mat);
        targetPanelContainer.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX/2, radius, 1));
        line.setLocalTranslation(sizeX/2, -sizeY, 0.0f);
        line.setMaterial(mat);
        targetPanelContainer.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(radius, sizeY/2, 1));
        line.setLocalTranslation(0.0f, -sizeY/2, 0.0f);
        line.setMaterial(mat);
        targetPanelContainer.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(radius, sizeY/2, 1));
        line.setLocalTranslation(sizeX, -sizeY/2, 0.0f);
        line.setMaterial(mat);
        targetPanelContainer.attachChild(line);

        /*// горизонтальная центральная граница
        line = new Geometry("line", new Box(radius, size/2, 1));
        line.setLocalTranslation(size/2, -size/2, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);

        // вертикальная центральная граница
        line = new Geometry("line", new Box(size/2, radius, 1));
        line.setLocalTranslation(size/2, -size/2, 0.0f);
        line.setMaterial(mat);
        radarContainer.attachChild(line);*/

        Material mat2 = mat.clone();
        mat2.setColor("Color", ColorRGBA.Red);

        // точка слева сверху
        line = new Geometry("line", new Box(radius*2, radius*2, 1));
        line.setLocalTranslation(0.0f, 0.0f, 0.0f);
        line.setMaterial(mat2);
        targetPanelContainer.attachChild(line);

        // точка снизу справа
        line = new Geometry("line", new Box(radius*2, radius*2, 1));
        line.setLocalTranslation(sizeX, -sizeY, 0.0f);
        line.setMaterial(mat2);
        targetPanelContainer.attachChild(line);
    }

    public Container getTargetPanelContainer() {
        return targetPanelContainer;
    }
    List<Target> targets = new ArrayList<>();
    class Target {
        Target(Label nameTargetLabel, Container boxContainer, Spatial iconContainer, Label distanceLabel){
            this.distanceLabel = distanceLabel;
            this.iconContainer = iconContainer;
            this.boxContainer = boxContainer;
            this.nameTargetLabel = nameTargetLabel;
        }
        private Spatial boxContainer;
        private Spatial iconContainer;
        private Label distanceLabel;
        private Label nameTargetLabel;

        public Spatial getBoxContainer() {
            return boxContainer;
        }

        public void setBoxContainer(Spatial boxContainer) {
            this.boxContainer = boxContainer;
        }

        public Spatial getIconContainer() {
            return iconContainer;
        }

        public void setIconContainer(Spatial iconContainer) {
            this.iconContainer = iconContainer;
        }

        public Label getDistanceLabel() {
            return distanceLabel;
        }

        public void setDistanceLabel(Label distanceLabel) {
            this.distanceLabel = distanceLabel;
        }

        public Label getNameTargetLabel() {
            return nameTargetLabel;
        }

        public void setNameTargetLabel(Label nameTargetLabel) {
            this.nameTargetLabel = nameTargetLabel;
        }
    }

    private void createBoxContainer(){
        targetboxContainer = new Container();
        targetboxContainer.setPreferredSize(new Vector3f(sizeX, 50.0f, 1.0f));

        Geometry line;
        Material mat;
        mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);

        float radius = 1.5f;

        sizeY = 30.0f;

        // верхняя граница
        line = new Geometry("line", new Box(sizeX/2-sizeY/4 + radius, radius, 1));
        line.setLocalTranslation(sizeX/2-sizeY/4 - radius/2, radius, 0.0f);
        line.setMaterial(mat);
        targetboxContainer.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX/2-sizeY/4 + radius, radius, 1));
        line.setLocalTranslation(sizeX/2-sizeY/4 - radius/2, -sizeY-radius*2, 0.0f);
        line.setMaterial(mat);
        targetboxContainer.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(radius, sizeY/2+radius, 1));
        line.setLocalTranslation(-radius/2, -sizeY/2, 0.0f);
        line.setMaterial(mat);
        targetboxContainer.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(radius, sizeY/2+radius, 1));
        line.setLocalTranslation(sizeX-sizeY/2-radius/2, -sizeY/2, 0.0f);
        line.setMaterial(mat);
        targetboxContainer.attachChild(line);

        targetboxContainer.setLocalTranslation(new Vector3f(sizeY/4+radius, -sizeY/4-radius, 1.0f));

    }

    public void updateTargets(Vector3f spaceshipPosition, List<Enemy> enemies) {

        for (Target target : targets) {
            targetPanelContainer.detachChild(target.getDistanceLabel());
            targetPanelContainer.detachChild(target.getIconContainer());
            targetPanelContainer.detachChild(target.getNameTargetLabel());
        }
        targets.clear();


        float stepY = 35.0f + 8.0f; // Высота каждого элемента плюс некоторое расстояние для отступа
        float currentY = 0.0f; // Начальная координата Y
        for (int i = 0; i < enemies.size() && i < 11; i++) {
            Enemy enemy = enemies.get(i);

            Label nameTargetLabel;
            Container boxContainer;
            Spatial iconContainer;
            Label distanceLabel;

            nameTargetLabel = new Label("%s".formatted(enemy.getModel().getName()));
            //nameTargetLabel.setPreferredSize(new Vector3f(sizeX, sizeY, 1.0f));
            nameTargetLabel.setTextHAlignment(HAlignment.Center);
            nameTargetLabel.setTextVAlignment(VAlignment.Center);

            nameTargetLabel.setFontSize(15);
            //nameTargetLabel.setLocalTranslation(50.0f, -sizeY/4-6, 0.0f);

            iconContainer = targetInconContainer.clone();
            //iconContainer.move(25, -25, 0.0f);

            boxContainer = (Container) targetboxContainer.clone();

            // iconContainer.setLocalTranslation(offsetX, offsetX, 0.0f);
            float distance = spaceshipPosition.distance(enemy.getModel().getLocalTranslation());
            int countChar = "%.3fm".formatted(distance).length();
            distanceLabel = new Label("%.3fm".formatted(distance));
            distanceLabel.setPreferredSize(new Vector3f(sizeX, sizeY, 1.0f));
            distanceLabel.setTextVAlignment(VAlignment.Center);
            distanceLabel.setTextHAlignment(HAlignment.Center);
            distanceLabel.setFontSize(15);
            //distanceLabel.setLocalTranslation( sizeX - 25.0f/2 - 15.0f/2 * countChar - 4, -sizeY/4.0f-6, 0.0f);

            // Установка локальной трансляции для каждого элемента с учетом шага
            nameTargetLabel.setLocalTranslation(50.0f, currentY - sizeY/4 - 6, 0.0f);
            boxContainer.move(-1.0f, currentY, 0.0f);
            iconContainer.setLocalTranslation(25+1.0f, currentY - 25, 0.0f);
            distanceLabel.setLocalTranslation(sizeX - 25.0f/2 - 15.0f/2 * countChar - 4, currentY - sizeY/4.0f - 6, 0.0f);

            // Создание и добавление нового целевого объекта
            Target target = new Target(nameTargetLabel, boxContainer, iconContainer, distanceLabel);

            // Прикрепление элементов к контейнеру
            targetPanelContainer.attachChild(target.getNameTargetLabel());
            targetPanelContainer.attachChild(target.getBoxContainer());
            targetPanelContainer.attachChild(target.getIconContainer());
            targetPanelContainer.attachChild(target.getDistanceLabel());

            // Добавление целевого объекта в список
            targets.add(target);

            // Обновление координаты Y для следующего элемента
            currentY -= stepY;
        }
    }
}
