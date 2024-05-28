package com.carbonara.game.object.gameobjects.states.technique.commands;

import com.carbonara.game.object.gameobjects.states.TESTINGACTION.interaction.interfaces.IActionCommand;
import com.carbonara.game.object.gameobjects.states.technique.TechniqueControl;

public class TurnOffCommand implements IActionCommand {
    private TechniqueControl technique;

    public TurnOffCommand(TechniqueControl technique) {
        this.technique = technique;
    }

    @Override
    public void execute() {
        technique.turnOff();
    }
}
