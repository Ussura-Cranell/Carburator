package com.carbonara.game.gui.pages;

import com.carbonara.game.settings.GameSettings;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.*;
import com.simsilica.lemur.component.SpringGridLayout;

import java.util.ArrayList;
import java.util.logging.Logger;

public class LoadingPage extends BaseAppState {
    private static final Logger logger = Logger.getLogger(LoadingPage.class.getName());
    private final int  LOADING_BAR_START_VALUE = 0;
    private final int LOADING_BAR_END_VALUE = 100;
    private int loadingBarValue = LOADING_BAR_START_VALUE;

    Container myWindow;
    Spatial element;

    @Override
    protected void initialize(Application application) {
        logger.info("Beginning of resource loading animation");

        myWindow = new Container();
        myWindow.setPreferredSize(new Vector3f(200, 40, 0));
        myWindow.setLocalTranslation(
                (float) GameSettings.getAppSettings().getWidth() /2 - (float) 200 /2,
                40, 0);
        ((SimpleApplication)application).getGuiNode().attachChild(myWindow);

        Label label = new Label("Loading...");
        label.setTextHAlignment(HAlignment.Center);
        label.setTextVAlignment(VAlignment.Center);
        myWindow.addChild(label);
        element = label;
    }

    @Override
    protected void cleanup(Application application) {
        logger.info("End of the resource loading animation");
    }

    @Override
    protected void onEnable() {
        // code
    }

    @Override
    protected void onDisable() {
        // code
    }

    public void setLoadingBarValue(int value){
        this.loadingBarValue = value;
        logger.info("Сhanging the loading level: " + value);
    }
}
