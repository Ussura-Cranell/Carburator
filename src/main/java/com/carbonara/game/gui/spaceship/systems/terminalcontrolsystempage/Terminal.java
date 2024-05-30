package com.carbonara.game.gui.spaceship.systems.terminalcontrolsystempage;

import com.carbonara.game.main.GlobalSimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;
import java.util.ArrayDeque;
import java.util.logging.Logger;

public class Terminal {
    private static final Logger logger = Logger.getLogger(Terminal.class.getName());
    private Container terminalContainer;
    float offsetX = 25; // смещение относительно леаой верхней точки
    float offsetY = 25 * 1.5f;
    Vector2f sizePage;
    float sizeX = 0;
    float sizeY = 0;
    public Terminal(Vector2f sizePage){
        sizeX = sizePage.getX();
        sizeY = sizePage.getY();

        terminalContainer = new Container();
        //terminalContainer.setPreferredSize(new Vector3f(sizePage.getX(), sizePage.getY(), 1.0f));
        terminalContainer.setLocalTranslation(0, 0, 1);
        this.sizePage = sizePage;
        addBoundaries();

        initializeTerminalDimension(15);
        addTextField();
        print("test line 1");
        print("test line 2");
        print("test line 3");
        print("test line 4");
        // update();
    }
    private void addBoundaries(){
        // добавляет границы для радара (квадратная форма)
        Geometry line;
        Material mat;
        mat = new Material(GlobalSimpleApplication.getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Yellow);

        float radius = 2.0f;

        // верхняя граница
        line = new Geometry("line", new Box(sizeX/2, radius, 1));
        line.setLocalTranslation(sizeX/2, -radius, 0.0f);
        line.setMaterial(mat);
        terminalContainer.attachChild(line);

        // нижняя граница
        line = new Geometry("line", new Box(sizeX/2, radius, 1));
        line.setLocalTranslation(sizeX/2, -sizeY+radius, 0.0f);
        line.setMaterial(mat);
        terminalContainer.attachChild(line);

        // левая граница
        line = new Geometry("line", new Box(radius, sizeY/2, 1));
        line.setLocalTranslation(radius, -sizeY/2, 0.0f);
        line.setMaterial(mat);
        terminalContainer.attachChild(line);

        // правая граница
        line = new Geometry("line", new Box(radius, sizeY/2, 1));
        line.setLocalTranslation(sizeX-radius, -sizeY/2, 0.0f);
        line.setMaterial(mat);
        terminalContainer.attachChild(line);
    }
    ArrayDeque<Label> terminalLines = new ArrayDeque<>();
    public Container getTerminalContainer() {
        return terminalContainer;
    }
    private void initializeTerminalDimension(int dimension){
        for (int i = 0; i < dimension; i++){
            terminalLines.addFirst(createLabel("line " + i));
        }
    }
    private void print(String line){
        terminalLines.peekLast().setText(line);
        Label firstLabel = terminalLines.pollFirst();
        firstLabel.setText(""); // невидимая строка
        terminalLines.addLast(firstLabel);

        float offset = 5.0f;
        float stepY = 20;
        float currentY = 0.0f;
        for (Label label : terminalLines){
            label.setLocalTranslation(0.0f + offset, -currentY++*stepY-offset, 1.0f);
        }
    }

    private Label createLabel(String text){
        Label label = new Label(text);
        label.setPreferredSize(new Vector3f(sizeX, 50.0f, 1.0f));
        label.setTextHAlignment(HAlignment.Center);
        label.setTextVAlignment(VAlignment.Center);
        label.setColor(ColorRGBA.Green);
        label.setFontSize(17.0f);

        terminalContainer.addChild(label);

        return label;
    }
    Container textFieldContainer;
    private void addTextField() {
        float offset = 5.0f;

        // Создание контейнера для текстового поля и кнопки
        textFieldContainer = new Container(new SpringGridLayout(Axis.X, Axis.Y));
        textFieldContainer.setPreferredSize(new Vector3f(sizeX, 50.0f, 1.0f));
        textFieldContainer.setLocalTranslation(0.0f + offset, -sizeY + 17.0f + offset*2, 1.0f);

        // Создание текстового поля
        TextField textField = new TextField("command");
        textField.setPreferredSize(new Vector3f(sizeX - 60 - offset, 40.0f, 1.0f));
        textField.setLocalTranslation(0.0f, 2.0f, 0.0f);
        textField.setTextVAlignment(VAlignment.Center);
        textField.setFontSize(17.0f);

        // Создание кнопки
        Button enterButton = new Button("Enter");
        enterButton.setPreferredSize(new Vector3f(50.0f, 50.0f, 1.0f));
        enterButton.setLocalTranslation(sizeX - 50.0f - offset, 0.0f + 2.0f, 0.0f);
        enterButton.setTextVAlignment(VAlignment.Center);
        enterButton.setTextHAlignment(HAlignment.Center);
        enterButton.setFontSize(17.0f);

        enterButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute(Button button) {
                String text = textField.getText();
                textField.setText("command");
                print(text);
            }
        });

        // Добавление текстового поля и кнопки в контейнер
        textFieldContainer.addChild(textField);
        textFieldContainer.addChild(enterButton);

        // Прикрепление контейнера к родительскому узлу
        terminalContainer.addChild(textFieldContainer);
    }
}
