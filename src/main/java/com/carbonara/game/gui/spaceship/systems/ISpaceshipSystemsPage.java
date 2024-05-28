package com.carbonara.game.gui.spaceship.systems;

import com.simsilica.lemur.Container;

public interface ISpaceshipSystemsPage {
    void initialize();
    void cleanup();
    Container getScreen();
}
