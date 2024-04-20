package com.carbonara.GUI.MainMenu;

import com.carbonara.Settings.MainSettings;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MainMenuPage extends BaseAppState {

    private static final Logger logger = Logger.getLogger(MainMenuPage.class.getName());
    Container myWindow;

    @Override
    protected void initialize(Application application) {

        Container myWindow = getContainer();

        Container window = new Container(new SpringGridLayout(Axis.Y, Axis.X));
        window.setPreferredSize(new Vector3f(0, 25, 0));
        myWindow.addChild(window);

        Label label = new Label("Main Menu");
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(30.0f);

        window.addChild(label);

        window = new Container(new SpringGridLayout(Axis.Y, Axis.X));
        myWindow.addChild(window);

        ArrayList<Button> buttons = new ArrayList<>();

        buttons.add(new Button("Continue"));
        buttons.add(new Button("New Game"));
        buttons.add(new Button("Settings"));
        buttons.add(new Button("Exit"));

        for (Button button : buttons) window.addChild(button);
        for (Button button : buttons) {
            button.setTextHAlignment(HAlignment.Center);
            button.setTextVAlignment(VAlignment.Center);
            button.setFontSize(20.0f);
        }

        Button button = buttons.get(2);
        if (button.getText().equals("Settings")) {
            button.addClickCommands(button1 -> {
                application.getStateManager().getState(SettingsPage.class).setEnabled(true);
                setEnabled(false);
            });
        } else logger.warning("invalid button name");

        button = buttons.get(3);
        if (button.getText().equals("Exit")) {
            button.addClickCommands(button12 -> application.stop());
        } else logger.warning("invalid button name");

        this.myWindow = myWindow;

    }

    private static Container getContainer() {
        float width = MainSettings.getAppSettings().getWidth();
        float height = MainSettings.getAppSettings().getHeight();

        float sizeX = width / 3;
        float sizeY = height / 3 + 50;

        float positionX = width/2 - sizeX/2;
        float positionY = height/2 + sizeY/2;

        Container myWindow = new Container();
        myWindow.setLocalTranslation(
                positionX,
                positionY, 0);

        myWindow.setPreferredSize(new Vector3f(sizeX, sizeY, 0)); // Предпочтительный размер окна
        return myWindow;
    }

    @Override
    protected void cleanup(Application application) {
        logger.info("cleanup");
    }

    @Override
    protected void onEnable() {
        logger.info("onEnable");
        ((SimpleApplication) this.getApplication()).getGuiNode().attachChild(myWindow);
    }

    @Override
    protected void onDisable() {
        logger.info("onDisable");
        ((SimpleApplication) this.getApplication()).getGuiNode().detachChild(myWindow);
    }
}
