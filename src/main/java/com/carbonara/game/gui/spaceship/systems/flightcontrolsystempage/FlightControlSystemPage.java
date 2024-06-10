package com.carbonara.game.gui.spaceship.systems.flightcontrolsystempage;

import com.carbonara.game.gui.spaceship.systems.AbstractSpaceshipSystemPage;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.object.other.spaceship.abstracts.AbstractSpaceship;
import com.carbonara.game.object.other.spaceship.systems.commands.flight.MoveDirectionCommand;
import com.carbonara.game.object.other.spaceship.systems.commands.flight.StopDirectionCommand;
import com.carbonara.game.scene.OuterSpaceScene;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.*;

import java.util.Arrays;
import java.util.logging.Logger;

public class FlightControlSystemPage extends AbstractSpaceshipSystemPage {
    private static final Logger logger = Logger.getLogger(FlightControlSystemPage.class.getName());
    public FlightControlSystemPage(Node point, float scale) {
        super(point, scale);
    }

    @Override
    public void initialize() {
        sizeX = 0.987689f * mul;
        sizeY = 0.346177f * mul;

        this.screen = new Container();
        this.screen.setPreferredSize(new Vector3f(sizeX, sizeY, 1f));

        Container labelContainer = new Container();
        labelContainer.setPreferredSize(new Vector3f(100, 50, 1));

        Label label = new Label(getClass().getSimpleName());
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(scale * 12);

        labelContainer.addChild(label);
        this.screen.addChild(labelContainer);

        addBigBorders();
        addLeftBorders();
        addRightBorders();

        addRightPanelPosition();
        addRightPanelVector();
        addRightPanelField();
        addRightPanelButtons();
    }

    private Container rightPanel = new Container();
    private Container leftPanel = new Container();

    private void addBigBorders(){
        Geometry line;
        Material mat = createMaterial(ColorRGBA.Yellow);

        // верхняя граница
        line = new Geometry("line", new Box(sizeX/2, 2.0f, 1));
        line.move(sizeX/2, 0, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX/2, 2.0f, 1));
        line.move(sizeX/2, -sizeY, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(2.0f, sizeY/2, 1));
        line.move(0, -sizeY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(2.0f, sizeY/2, 1));
        line.move(sizeX, -sizeY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);
    }
    private void addLeftBorders(){
        Geometry line;
        Material mat = createMaterial(ColorRGBA.Yellow);
        float posX = 0.0f;
        float posy = 0.0f;

        float scaleY = 0.8f;
        float scaleX = 0.6f;

        var value = (sizeY-sizeY*scaleY) / 2;
        posX += value;
        posy -= value;

        // верхняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy-sizeY*scaleY, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX + sizeX*scaleX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);
    }
    private void addRightBorders(){
        Geometry line;
        Material mat = createMaterial(ColorRGBA.Yellow);
        float posX = 0.0f;
        float posy = 0.0f;

        float scaleY = 0.8f;
        float scaleX = 0.3f;

        var value = (sizeY-sizeY*scaleY) / 2;
        posX += sizeX - sizeX * scaleX - value;
        posy -= value;

        // верхняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy-sizeY*scaleY, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX + sizeX*scaleX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);
    }
    /*
    set.
    drop.
    fly.

    */

    Vector3f direction = Vector3f.ZERO.clone();
    Label positionLabel;
    Label directionLabel;
    TextField vectorField;

    private static Vector3f convertStringToVector(String input) {
        // Разделяем строку по запятым
        String[] components = input.split(",");

        System.out.println("string : " + input);
        System.out.println("arrays : " + Arrays.asList(components));

        if (components.length != 3) {
            logger.warning("ZERO");
            return Vector3f.ZERO.clone();
            // throw new IllegalArgumentException("Неверный формат строки. Ожидалось 3 компоненты.");
        }

        try {
            // Преобразуем каждую компоненту во float
            float x = Float.parseFloat(components[0].trim());
            float y = Float.parseFloat(components[1].trim());
            float z = Float.parseFloat(components[2].trim());

            // Создаем новый Vector3f и возвращаем его
            Vector3f newVector = new Vector3f(x, y, z);
            logger.info(newVector.toString());
            return newVector;
        } catch (NumberFormatException e) {
            // throw new IllegalArgumentException("Невозможно преобразовать строку в числа.");
            logger.warning("ZERO");
            return Vector3f.ZERO.clone();
        }
    }

    private void addRightPanelPosition(){

        Geometry line;
        Material mat = createMaterial(ColorRGBA.Yellow);
        float posX = 0.0f;
        float posy = 0.0f;

        float scaleY = 0.15f;
        float scaleX = 0.25f;

        var value = sizeY*scaleY;
        posX += 680 /*- value*/;
        posy -= value;

        Container positionContainer = new Container();
        positionContainer.setSize(new Vector3f(sizeX*scaleX, sizeY*scaleY, 1.0f));
        positionContainer.move(posX, posy, 1.0f);

        positionLabel = new Label("POSITION");
        positionLabel.setTextVAlignment(VAlignment.Center);
        positionLabel.setTextHAlignment(HAlignment.Center);
        positionLabel.setFontSize(15.0f);
        positionContainer.addChild(positionLabel);

        this.screen.attachChild(positionContainer);

        // верхняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy-sizeY*scaleY, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX + sizeX*scaleX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);
    }
    private void addRightPanelVector(){
        Geometry line;
        Material mat = createMaterial(ColorRGBA.Yellow);
        float posX = 0.0f;
        float posy = 0.0f;

        float scaleY = 0.15f;
        float scaleX = 0.25f;

        var value = sizeY*scaleY;
        posX += 680 /*- value*/;
        posy -= value * 2.25f;

        Container positionContainer = new Container();
        positionContainer.setSize(new Vector3f(sizeX*scaleX, sizeY*scaleY, 1.0f));
        positionContainer.move(posX, posy, 1.0f);

        directionLabel = new Label("DIRECTION");
        directionLabel.setTextVAlignment(VAlignment.Center);
        directionLabel.setTextHAlignment(HAlignment.Center);
        directionLabel.setFontSize(15.0f);
        positionContainer.addChild(directionLabel);

        this.screen.attachChild(positionContainer);

        // верхняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy-sizeY*scaleY, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX + sizeX*scaleX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);
    }
    private void addRightPanelField(){
        Geometry line;
        Material mat = createMaterial(ColorRGBA.Yellow);
        float posX = 0.0f;
        float posy = 0.0f;

        float scaleY = 0.15f;
        float scaleX = 0.25f;

        var value = sizeY*scaleY;
        posX += 680 /*- value*/;
        posy -= value * 3.50f;

        Container positionContainer = new Container();
        positionContainer.setSize(new Vector3f(sizeX*scaleX, sizeY*scaleY, 1.0f));
        positionContainer.move(posX, posy, 1.0f);

        vectorField = new TextField("VECTOR3F");
        vectorField.setTextVAlignment(VAlignment.Center);
        vectorField.setTextHAlignment(HAlignment.Center);
        vectorField.setFontSize(15.0f);
        positionContainer.addChild(vectorField);

        this.screen.attachChild(positionContainer);

        // верхняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy-sizeY*scaleY, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX + sizeX*scaleX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);
    }
    private void addRightPanelButtons(){
        Geometry line;
        Material mat = createMaterial(ColorRGBA.Yellow);
        float posX = 0.0f;
        float posy = 0.0f;

        float scaleY = 0.15f;
        float scaleX = 0.25f;

        var value = sizeY*scaleY;
        posX += 680 /*- value*/;
        posy -= value * 4.75f;

        Container positionContainer = new Container();
        positionContainer.setSize(new Vector3f(sizeX*scaleX, sizeY*scaleY, 1.0f));
        positionContainer.move(posX, posy, 1.0f);

        Button button1 = new Button("SET");
        button1.setTextVAlignment(VAlignment.Center);
        button1.setTextHAlignment(HAlignment.Center);
        button1.setFontSize(15.0f);
        button1.move(sizeX*scaleX/3-value, 0, 1.0f);
        button1.addClickCommands(button -> {
            String vectorText = vectorField.getText();
            direction = convertStringToVector(vectorText);

            vectorField.setText("VECTOR3F");

        });
        positionContainer.addChild(button1);

        Button button2 = new Button("DROP");
        button2.setTextVAlignment(VAlignment.Center);
        button2.setTextHAlignment(HAlignment.Center);
        button2.setFontSize(15.0f);
        button2.move(sizeX*scaleX*2/3-value, 0, 1.0f);
        button2.addClickCommands(button -> {
            // прекращение движения
            // сброс вектора направления

            AbstractSpaceship.getAbstractSpaceShip().getMainControlSystem().executeCommand(new MoveDirectionCommand(direction));

        });
        positionContainer.addChild(button2);

        Button button3 = new Button("FLY");
        button3.setTextVAlignment(VAlignment.Center);
        button3.setTextHAlignment(HAlignment.Center);
        button3.setFontSize(15.0f);
        button3.move(sizeX*scaleX*3/3-value, 0, 1.0f);
        button3.addClickCommands(button -> {
            // запуск команды для движения


        });
        positionContainer.addChild(button3);

        this.screen.attachChild(positionContainer);

        // верхняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX*scaleX/2, 2.0f, 1));
        line.move(posX + sizeX*scaleX/2, posy-sizeY*scaleY, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(2.0f, sizeY*scaleY/2, 1));
        line.move(posX + sizeX*scaleX, posy-sizeY*scaleY/2, 0);
        // line.setLocalTranslation(posX, posY, 0.0f);
        line.setMaterial(mat);
        this.screen.attachChild(line);
    }

    private Material createMaterial(ColorRGBA color) {
        Material mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", color);
        return mat;
    }

    @Override
    public void update(float tpf) {

        Spatial spaceship = OuterSpaceScene.getSpaceshipSpatial();
        if (spaceship != null)  positionLabel.setText(String.valueOf(spaceship.getLocalTranslation()));
        else logger.info("null!");
        directionLabel.setText(direction.toString());
    }
}
