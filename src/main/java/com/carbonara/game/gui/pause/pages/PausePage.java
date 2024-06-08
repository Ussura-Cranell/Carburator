package com.carbonara.game.gui.pause.pages;

import com.carbonara.game.gui.menu.managers.MainMenuPageManager;
import com.carbonara.game.logic.NewSceneGuardian;
import com.carbonara.game.main.GlobalSimpleApplication;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.managers.GameSavesManager;
import com.carbonara.game.object.gameobjects.categories.player.controls.PlayerStateManager;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PausePage extends BaseAppState {

    private static final Logger logger = Logger.getLogger(PausePage.class.getName());
    Container myWindow;

    @Override
    protected void initialize(Application application) {

        Container myWindow = getContainer();

        Container window = new Container(new SpringGridLayout(Axis.Y, Axis.X));
        window.setPreferredSize(new Vector3f(0, 25, 0));
        myWindow.addChild(window);

        Label label = new Label("Pause Menu");
        label.setTextVAlignment(VAlignment.Center);
        label.setTextHAlignment(HAlignment.Center);
        label.setFontSize(30.0f);

        window.addChild(label);

        window = new Container(new SpringGridLayout(Axis.Y, Axis.X));
        myWindow.addChild(window);

        ArrayList<Button> buttons = new ArrayList<>();

        buttons.add(new Button("Continue"));
        buttons.add(new Button("Save Game"));
        buttons.add(new Button("Settings"));
        buttons.add(new Button("Exit to Main Menu"));

        for (Button button : buttons) window.addChild(button);
        for (Button button : buttons) {
            button.setTextHAlignment(HAlignment.Center);
            button.setTextVAlignment(VAlignment.Center);
            button.setFontSize(20.0f);
            button.addClickCommands(GUIManager.getclickSoundCommand());
        }
        Button button;

        button = buttons.get(1);
        if (button.getText().equals("Save Game")) {
            button.addClickCommands(button1 -> {
                Node data = new Node();

                PlayerStateManager playerStateManager = GlobalSimpleApplication.getApp().getStateManager().getState(PlayerStateManager.class);
                Camera camera = GlobalSimpleApplication.getApp().getCamera();

                Vector3f physicLocation = playerStateManager.getPlayerCharacter().getPlayerCharacterControl().getPhysicsLocation();
                Vector3f direction = camera.getDirection();

                data.setUserData("physicLocation", playerStateManager.getPlayerCharacter().getPlayerCharacterControl().getPhysicsLocation());
                data.setUserData("direction",direction);

                try {
                    GameSavesManager.save(data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } else logger.warning("invalid button name");

        button = buttons.get(3);
        if (button.getText().equals("Exit to Main Menu")) {
            button.addClickCommands(button1 -> {
                // освобождаем все загруженные ресурсы сцены
                application.getStateManager().detach(application.getStateManager().getState(NewSceneGuardian.class));
                // отображаем главное меню
                application.getStateManager().attach(new MainMenuPageManager());
            });
        } else logger.warning("invalid button name");

        this.myWindow = myWindow;

    }

    private static Container getContainer() {
        float width = GameSettings.getAppSettings().getWidth();
        float height = GameSettings.getAppSettings().getHeight();

        float sizeX = width / 3;
        float sizeY = height / 3 + 50;

        float positionX = width / 2 - sizeX / 2;
        float positionY = height / 2 + sizeY / 2;

        Container myWindow = new Container();
        myWindow.setLocalTranslation(
                positionX,
                positionY, 0);

        myWindow.setPreferredSize(new Vector3f(sizeX, sizeY, 0)); // Предпочтительный размер окна
        return myWindow;
    }

    @Override
    protected void cleanup(Application application) {
        logger.info("Удаление меню паузы");
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