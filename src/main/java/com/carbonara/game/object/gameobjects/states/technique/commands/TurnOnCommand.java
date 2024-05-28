package com.carbonara.game.object.gameobjects.states.technique.commands;

import com.carbonara.game.object.gameobjects.states.technique.TechniqueControl;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.interaction.interfaces.IActionCommand;

public class TurnOnCommand implements IActionCommand {
    private TechniqueControl technique;

    public TurnOnCommand(TechniqueControl technique) {
        this.technique = technique;
    }

    @Override
    public void execute() { technique.turnOn(); }
}