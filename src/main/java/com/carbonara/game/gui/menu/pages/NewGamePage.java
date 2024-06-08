package com.carbonara.game.gui.menu.pages;

import com.carbonara.game.gui.menu.managers.MainMenuPageManager;

import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;

import java.util.ArrayList;
import java.util.logging.Logger;

public class NewGamePage extends BaseAppState {
    private static final Logger logger = Logger.getLogger(NewGamePage.class.getName());
    private Container myWindow;
    private boolean selectDifficulty = false;

    @Override
    protected void initialize(Application application) {

        Container myWindow = getContainer();

        Container window = new Container(new SpringGridLayout(Axis.Y, Axis.X));
        window.setPreferredSize(new Vector3f(0, 25, 0));
        myWindow.addChild(window);

        Label label = new Label("Creating a new game");
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(30.0f);

        window.addChild(label);

        window = new Container(new SpringGridLayout(Axis.Y, Axis.X));
        myWindow.addChild(window);

        ArrayList<Button> buttons = new ArrayList<>();

        Button Bbutton;

        Bbutton = new Button("Something 1");
        buttons.add(Bbutton);
        window.addChild(Bbutton);

        Container container = new Container(new SpringGridLayout(Axis.X, Axis.Y));
        window.addChild(container);
        {
            Bbutton = new Button(GameSettings.Difficulty.EASY.name());
            buttons.add(Bbutton);
            container.addChild(Bbutton);
            // window.addChild(Bbutton);

            Bbutton = new Button(GameSettings.Difficulty.NORMAL.name());
            buttons.add(Bbutton);
            container.addChild(Bbutton);
            // window.addChild(Bbutton);

            Bbutton = new Button(GameSettings.Difficulty.HARD.name());
            buttons.add(Bbutton);
            container.addChild(Bbutton);
            // window.addChild(Bbutton);
        }

        Bbutton = new Button("Create");
        buttons.add(Bbutton);
        window.addChild(Bbutton);

        Bbutton = new Button("Back to menu");
        buttons.add(Bbutton);
        window.addChild(Bbutton);

        // buttons.add(new Button("Something 2"));
        // buttons.add(new Button("Create"));
        // buttons.add(new Button("Back to menu"));

        // for (Button button : buttons) window.addChild(button);
        for (Button button : buttons) {
            button.setTextHAlignment(HAlignment.Center);
            button.setTextVAlignment(VAlignment.Center);
            button.setFontSize(20.0f);
            button.addClickCommands(GUIManager.getclickSoundCommand());
        }

        this.myWindow = myWindow;

        Button button = buttons.get(1);
        if (button.getText().equals(GameSettings.Difficulty.EASY.name())) {
            button.addClickCommands(button1 -> {
                GameSettings.difficulty = GameSettings.Difficulty.EASY;
                selectDifficulty = true;
            });
        } else logger.warning("invalid button name");

        button = buttons.get(2);
        if (button.getText().equals(GameSettings.Difficulty.NORMAL.name())) {
            button.addClickCommands(button1 -> {
                GameSettings.difficulty = GameSettings.Difficulty.NORMAL;
                selectDifficulty = true;
            });
        } else logger.warning("invalid button name");

        button = buttons.get(3);
        if (button.getText().equals(GameSettings.Difficulty.HARD.name())) {
            button.addClickCommands(button1 -> {
                GameSettings.difficulty = GameSettings.Difficulty.HARD;
                selectDifficulty = true;
            });
        } else logger.warning("invalid button name");

        button = buttons.get(4);
        if (button.getText().equals("Create")) {
            button.addClickCommands(button1 -> {
                //logger.warning("GameController is deprecated!");
                // application.getStateManager().attach(new SceneGuardian());
                if (selectDifficulty){
                application.getStateManager().attach(new LoadingPage());
                application.getStateManager().detach(application.getStateManager().getState(MainMenuPageManager.class));}
                else {
                    logger.info("The game difficulty is not selected!");
                }
            });
        } else logger.warning("invalid button name");

        button = buttons.get(5);
        if (button.getText().equals("Back to menu")) {
            button.addClickCommands(button1 -> {
                application.getStateManager().getState(MainMenuPage.class).setEnabled(true);
                setEnabled(false);
            });
        } else logger.warning("invalid button name");

    }

    private static Container getContainer() {
        float width = GameSettings.getAppSettings().getWidth();
        float height = GameSettings.getAppSettings().getHeight();

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
