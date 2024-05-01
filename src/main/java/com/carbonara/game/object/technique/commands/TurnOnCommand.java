package com.carbonara.game.object.technique.commands;

import com.carbonara.game.object.technique.TechniqueControl;
import com.carbonara.game.logic.interaction.interfaces.IActionCommand;

public class TurnOnCommand implements IActionCommand {
    private TechniqueControl technique;

    public TurnOnCommand(TechniqueControl technique) {
        this.technique = technique;
    }

    @Override
    public void execute() { technique.turnOn(); }
}