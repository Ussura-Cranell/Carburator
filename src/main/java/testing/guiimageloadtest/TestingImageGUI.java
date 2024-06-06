package testing.guiimageloadtest;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;
import com.jme3.ui.Picture;
import com.simsilica.lemur.Axis;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.FillMode;
import com.simsilica.lemur.component.QuadBackgroundComponent;
import com.simsilica.lemur.component.SpringGridLayout;

public class TestingImageGUI {
    private SimpleApplication app;
    private Container container;

    public TestingImageGUI(SimpleApplication app){
        this.app = app;
    }

    public void initialize(){
        createContainer();
        addContainerToGUI();
        addPicture();
        addContainer();
    }
    private void createContainer(){

        float sizeX = 700.0f;
        float sizeY = 700.0f;

        SpringGridLayout layout = new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.None);
        // BorderLayout borderLayout = new BorderLayout();

        container = new Container(layout);
        container.setPreferredSize(new Vector3f(sizeX, sizeY, 1.0f));
        container.setLocalTranslation(new Vector3f(sizeX/2, sizeY, 1.0f));
    }
    private void addContainerToGUI(){
        app.getGuiNode().attachChild(container);
    }

    private void addPicture(){
        //Texture texture = app.getAssetManager().loadTexture("Textures/human/375-3751215_knight-armor-png.png");

        float sizeX = 331.0f;
        float sizeY = 351.0f;

        Picture picture = new Picture("human picture");
        picture.setImage(app.getAssetManager(), "Textures/human/375-3751215_knight-armor-png.png", true);
        picture.setWidth(sizeX);
        picture.setHeight(sizeY);
        picture.setLocalTranslation(0.0f, -sizeY, 0.0f);
        // picture.setPosition(100, 100);

        Node myGuiNode = new Node("My GUI Node");
        myGuiNode.attachChild(picture);

        container.attachChild(picture);
    }

    private void addContainer() {
        float sizeX = 331.0f;
        float sizeY = 351.0f;

        Texture imageHumanTexture = app.getAssetManager().loadTexture("Textures/human/375-3751215_knight-armor-png.png");
        QuadBackgroundComponent backgroundComponent = new QuadBackgroundComponent(imageHumanTexture);

        // Использование SpringGridLayout для более точного контроля размеров
        //SpringGridLayout layout = new SpringGridLayout(Axis.Y, Axis.X, FillMode.None, FillMode.None);
        Container imageHuman = new Container(/*layout*/);
        imageHuman.setSize(new Vector3f(sizeX, sizeY, 1.0f));
        imageHuman.setPreferredSize(new Vector3f(sizeX, sizeY, 1.0f));
        //imageHuman.setLocalTranslation(new Vector3f(0.0f, 351.0f, 1.0f));
        //imageHuman.setSize(new Vector3f(sizeX, sizeY, 1.0f));
        // imageHuman.setMinimumSize(new Vector3f(sizeX, sizeY, 1.0f));
        // imageHuman.setMaximumSize(new Vector3f(sizeX, sizeY, 1.0f));
        imageHuman.setBackground(backgroundComponent);
        imageHuman.move(0.0f, sizeY, 1.0f);

        Container nullContainer = new Container();
        nullContainer.setPreferredSize(new Vector3f(sizeX, sizeY, 1.0f));

        container.addChild(nullContainer, 0, 1);
        container.addChild(imageHuman, 1, 0);
    }
}
