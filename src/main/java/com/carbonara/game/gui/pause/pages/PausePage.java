package com.carbonara.game.gui.pause.pages;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

import java.util.logging.Logger;

public class PausePage extends BaseAppState {

    Logger logger = Logger.getLogger(PausePage.class.getName());

    @Override
    protected void initialize(Application application) {
        logger.info("страница меню паузы");
    }

    @Override
    protected void cleanup(Application application) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
