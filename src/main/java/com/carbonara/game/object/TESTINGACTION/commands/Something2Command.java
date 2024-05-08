package com.carbonara.game.object.TESTINGACTION.commands;

import com.carbonara.game.object.TESTINGACTION.UniversalObject;
import com.carbonara.game.logic.interaction.interfaces.IActionCommand;

public class Something2Command implements IActionCommand {
    UniversalObject universalObject;
    public Something2Command(UniversalObject universalObject){
        this.universalObject = universalObject;
    }
    @Override
    public void execute() {
        universalObject.changeSomething2();
    }
}
