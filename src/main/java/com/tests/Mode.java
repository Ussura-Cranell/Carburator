package com.tests;


import com.simsilica.lemur.Container;

public enum Mode {
    NONE,
    CREATE_GAME,
    MAIN_MENU,
    SETTING;
    Mode (){
        guiPage = new Container();
    }

    private Container guiPage;

    public Container getGuiPage() {
        return guiPage;
    }

    public void setGuiPage(Container guiPage) {
        this.guiPage = guiPage;
    }

    @Override
    public String toString() {
        return "Mode{" +
                "guiPage=" + guiPage +
                '}';
    }
}
