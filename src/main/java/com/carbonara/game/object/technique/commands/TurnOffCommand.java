package com.carbonara.game.object.technique.commands;

import com.carbonara.game.object.interfaces.IActionCommand;
import com.carbonara.game.object.technique.TechniqueControl;

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
