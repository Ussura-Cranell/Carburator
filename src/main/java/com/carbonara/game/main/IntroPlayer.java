package com.carbonara.game.main;

import com.carbonara.game.managers.GUIManager;
import com.carbonara.game.gui.menu.managers.MainMenuPageManager;
import com.carbonara.game.tools.video.player.MovieSettings;
import com.carbonara.game.tools.video.player.MovieState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;

import java.util.logging.Logger;

public class IntroPlayer extends BaseAppState {

    private MovieState movieState;


    @Override
    protected void initialize(Application application) {

        GUIManager.cameraUnlock(false);
        GUIManager.cursorVisible(false);

        try {
            String path = "src/main/resources/Media/Video/Intro.mp4";

            MovieSettings movie = new MovieSettings(path,
                    application.getCamera().getWidth(),
                    application.getCamera().getHeight(), 1.0f, true);
            movieState = new MovieState(movie);
            application.getStateManager().attach(movieState);

            ((SimpleApplication) application).getFlyByCamera().setEnabled(false);
            application.getInputManager().setCursorVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void cleanup(Application application) {
        // application.getStateManager().attach(new MainMenu(app));
        // logger.log(Level.INFO, "cleanup");
    }

    @Override
    protected void onEnable() {
        //logger.log(Level.INFO, "onEnable");
    }

    @Override
    protected void onDisable() {
        //logger.log(Level.INFO, "onDisable");


    }

    @Override
    public void update(float tpf) {
        if (movieState.isInitialized() && movieState.isStopped()){
            this.getApplication().getStateManager().detach(movieState);
            this.getApplication().getStateManager().detach(this);
            this.getApplication().getStateManager().attach(new MainMenuPageManager());
        }
    }
}
