package com.carbonara.game.object.gameobjects.states.TESTINGACTION.commands;

import com.carbonara.game.object.gameobjects.states.TESTINGACTION.UniversalObject;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.interaction.interfaces.IActionCommand;

public class Something3Command implements IActionCommand {
    UniversalObject universalObject;
    public Something3Command(UniversalObject universalObject){
        this.universalObject = universalObject;
    }
    @Override
    public void execute() {
        universalObject.changeSomething3();
    }
}
