package com.carbonara.game.main;

import com.carbonara.game.gui.menu.managers.MainMenuPageManager;
import com.carbonara.game.tools.video.player.MovieSettings;
import com.carbonara.game.tools.video.player.MovieState;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.BaseAppState;

public class IntroPlayer extends BaseAppState {

    private MovieState movieState;

    @Override
    protected void initialize(Application application) {

        try {
            String path = "src/main/resources/Media/Video/Intro.mp4";

            MovieSettings movie = new MovieSettings(path,
                    application.getCamera().getWidth(),
                    application.getCamera().getHeight(), 1.0f, true);
            movieState = new MovieState(movie);
            application.getStateManager().attach(movieState);

            ((SimpleApplication) application).getFlyByCamera().setEnabled(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void update(float tpf) {
        if (movieState.isInitialized() && movieState.isStopped()){
            this.getApplication().getStateManager().detach(movieState);
            this.getApplication().getStateManager().detach(this);
            this.getApplication().getStateManager().attach(new MainMenuPageManager());
        }
    }
}
