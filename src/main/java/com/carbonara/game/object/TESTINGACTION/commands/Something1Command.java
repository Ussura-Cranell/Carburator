package com.carbonara.game.object.TESTINGACTION.commands;

import com.carbonara.game.object.TESTINGACTION.UniversalObject;
import com.carbonara.game.logic.interaction.interfaces.IActionCommand;

public class Something1Command implements IActionCommand {
    UniversalObject universalObject;
    public Something1Command(UniversalObject universalObject){
        this.universalObject = universalObject;
    }
    @Override
    public void execute() {
        universalObject.changeSomething1();
    }
}
