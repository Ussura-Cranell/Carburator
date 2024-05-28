package com.carbonara.game.object.gameobjects.categories.player.controls;

import com.carbonara.game.managers.NewPauseGameManager;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class PlayerGUIManager implements Observer {

    public static final Logger logger = Logger.getLogger(PlayerGUIManager.class.getName());

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof NewPauseGameManager){
            logger.info("Уведомление о паузе");
        }
    }
}
