package com.carbonara.game.object.gameobjects.states.TESTINGACTION.commands;

import com.carbonara.game.object.gameobjects.states.TESTINGACTION.UniversalObject;
import com.carbonara.game.object.gameobjects.states.TESTINGACTION.interaction.interfaces.IActionCommand;

public class Something5Command implements IActionCommand {
    UniversalObject universalObject;
    public Something5Command(UniversalObject universalObject){
        this.universalObject = universalObject;
    }
    @Override
    public void execute() {
        universalObject.changeSomething5();
    }
}
