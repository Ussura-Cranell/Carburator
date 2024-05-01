package com.carbonara.game.logic.interaction.interfaces;

import com.jme3.app.state.BaseAppState;

public interface IInteraction {
    // возвращает инерфейс взаимодействующего объекта
    BaseAppState getInteraction();
}
