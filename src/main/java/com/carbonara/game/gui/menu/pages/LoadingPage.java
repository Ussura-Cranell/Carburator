package com.carbonara.game.gui.menu.pages;

import com.carbonara.game.gui.pause.managers.PauseGameManager;
import com.carbonara.game.logic.SceneGuardian;
import com.carbonara.game.main.GameLauncher;
import com.carbonara.game.managers.CameraManager;
import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.simsilica.lemur.*;

import java.util.logging.Logger;

public class LoadingPage extends BaseAppState {
    private static final Logger logger = Logger.getLogger(LoadingPage.class.getName());
    private final int  LOADING_BAR_START_VALUE = 0;
    private final int LOADING_BAR_END_VALUE = 100;
    private int loadingBarValue = LOADING_BAR_START_VALUE;

    private boolean loading = false;

    Container myPage;
    Container myWindow;
    Label loadingLabel;
    Button continueButton;

    @Override
    protected void initialize(Application application) {

        // блокируем перемещаение камеры и отображаем курсор,
        // на случай если в другой части программы другие настройки
        CameraManager.cameraUnlock(false);
        GUIManager.setCursorVisible(true);

        logger.info("Beginning of resource loading animation");

        // включение анимации загрузки

        myPage = new Container();
        myPage.setPreferredSize(new Vector3f(GameSettings.getAppSettings().getWidth(),
                GameSettings.getAppSettings().getHeight(), 1));
        myPage.setLocalTranslation(0, GameSettings.getAppSettings().getHeight(), 1);

        myWindow = new Container();
        // myWindow.setPreferredSize();
        myWindow.setSize(new Vector3f(200, 40, 0));
        myWindow.setPreferredSize(new Vector3f(200, 40, 0));
        myWindow.setLocalTranslation(
                (float) GameSettings.getAppSettings().getWidth() /2 - (float) 200 /2,
                40, 0);

        myPage.addChild(myWindow);
        ((SimpleApplication)application).getGuiNode().attachChild(myPage);

        // текст для загрузки
        loadingLabel = new Label("Loading...");
        loadingLabel.setTextHAlignment(HAlignment.Center);
        loadingLabel.setTextVAlignment(VAlignment.Center);
        myWindow.addChild(loadingLabel);

        // кнопка для продолжения
        continueButton = new Button("Continue");
        continueButton.setTextHAlignment(HAlignment.Center);
        continueButton.setTextVAlignment(VAlignment.Center);

        // при нажатии закрывается страница загрузки и выключается взаимодействие пользователя
        continueButton.addClickCommands(new Action() {
            @Override
            public void execute(Button button) {
                application.getStateManager().detach(
                        application.getStateManager().getState(LoadingPage.class));
                CameraManager.cameraUnlock(true);
                application.getInputManager().setCursorVisible(false);

            }
        });

        // начало загрузки сцены!!!
        application.getStateManager().attach(new SceneGuardian());
    }

    @Override
    protected void cleanup(Application application) {
        logger.info("End of the resource loading animation");

        // после закрытия страницы загрузки, можно будет вертеть камерой, а курсор будет скрыт
        CameraManager.cameraUnlock(true);
        GUIManager.setCursorVisible(false);

        // открепляем экран загрузки при выходе
        GameLauncher.getApp().getGuiNode().detachChild(myPage);

        // включение обработчика паузы
        application.getStateManager().attach(new PauseGameManager());
    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {

    }

    public void setLoadingBarValue(int value){
        this.loadingBarValue = value;
        logger.info("Changing the loading level: " + value);
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public void changeStatus(){
        // метод для изменения состояния загрузки на "продолжить"

        myWindow.detachChild(loadingLabel);
        myWindow.addChild(continueButton);

    }
}
