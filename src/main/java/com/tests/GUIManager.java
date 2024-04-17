package com.tests;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.Label;

import java.util.ArrayList;


public class GUIManager {

    private static Node guiNode;
    private static Mode guiPage;
    GUIManager(Node guiNode){
        GUIManager.guiNode = guiNode;

        Mode.NONE.setGuiPage(createNonePage());
        Mode.CREATE_GAME.setGuiPage(createCreateGamePage());
        Mode.MAIN_MENU.setGuiPage(createMainMenuPage());
        Mode.SETTING.setGuiPage(createSettingPage());
    }

    public void setGUIPage(Mode mode){
        if (guiPage != null) guiNode.detachChild(guiPage.getGuiPage());
        guiPage = mode;
        guiNode.attachChild(guiPage.getGuiPage());
    }

    private Container createNonePage(){
        Container NonePage = new Container();
        return NonePage;
    }
    private Container createCreateGamePage(){
        Container CreateGamePage = new Container();

        CreateGamePage.setLocalTranslation(Main.RESOLUTION_WIDTH/2, Main.RESOLUTION_HEIGHT/2, 0);

        ArrayList<Spatial> elements = new ArrayList<>();

        Label selectDifficulty = new Label("Select the difficulty:");
        Button EasyDifficultyButton = new Button("EASY");
        Button MediumDifficultyButton = new Button("MEDIUM");
        Button HardDifficultyButton = new Button("HARD");
        Button backButton = new Button("BACK");

        elements.add(selectDifficulty);
        elements.add(EasyDifficultyButton);
        elements.add(MediumDifficultyButton);
        elements.add(HardDifficultyButton);
        elements.add(backButton);

        float offset_Y = 50.0f;
        float step_offset_Y = 50.0f;
        float offset_X = -120.0f;

        for (Spatial element : elements){
            element.setLocalTranslation(offset_X, offset_Y, 0.0f);
            element.setLocalScale(3.0f);
            offset_Y -= step_offset_Y;
            CreateGamePage.attachChild(element);
        }

        EasyDifficultyButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("УСТАНОВЛЕН ЛЕГКИЙ УРОВЕНЬ СЛОЖНОСТИ...");
            }
        });
        MediumDifficultyButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("УСТАНОВЛЕН СРЕДНИЙ УРОВЕНЬ СЛОЖНОСТИ...");
            }
        });
        HardDifficultyButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("УСТАНОВЛЕН СЛОЖНЫЙ УРОВЕНЬ СЛОЖНОСТИ...");
            }
        });
        backButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                setGUIPage(Mode.MAIN_MENU);
            }
        });

        return CreateGamePage;
    }
    private Container createMainMenuPage(){
        Container MainMenuPage = new Container();

        MainMenuPage.setLocalTranslation(Main.RESOLUTION_WIDTH/2, Main.RESOLUTION_HEIGHT/2, 0);

        ArrayList<Spatial> elements = new ArrayList<>();

        Button continueButton = new Button("CONTINUE");
        Button createGameButton = new Button("CREATE GAME");
        Button settingButton = new Button("SETTING");
        Button exitButton = new Button("EXIT");

        elements.add(continueButton);
        elements.add(createGameButton);
        elements.add(settingButton);
        elements.add(exitButton);

        float offset_Y = 50.0f;
        float step_offset_Y = 50.0f;
        float offset_X = -120.0f;

        for (Spatial element : elements){
            element.setLocalTranslation(offset_X, offset_Y, 0.0f);
            element.setLocalScale(3.0f);
            offset_Y -= step_offset_Y;
            MainMenuPage.attachChild(element);
        }
        continueButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("ЗАГРУЗКА СОХРАНЕНИЯ...");
                System.exit(-1);
            }
        });

        createGameButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                setGUIPage(Mode.CREATE_GAME);
                System.out.println("Переход в меню \"НОВАЯ ИГРА\'");
            }
        });

        settingButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                setGUIPage(Mode.SETTING);
                System.out.println("Переход в меню \"НАСТРОЙКИ\'");
            }
        });

        exitButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("ВЫХОД ИЗ ПРОГРАММЫ...");
                System.exit(-1);
            }
        });

        return MainMenuPage;
    }
    private Container createSettingPage(){
        Container SettingPage = new Container();

        SettingPage.setLocalTranslation(Main.RESOLUTION_WIDTH/2, Main.RESOLUTION_HEIGHT/2, 0);

        ArrayList<Spatial> elements = new ArrayList<>();

        Button clickMeButton = new Button("CLICK ME");
        Button backButton = new Button("BACK");

        elements.add(clickMeButton);
        elements.add(backButton);

        float offset_Y = 50.0f;
        float step_offset_Y = 50.0f;
        float offset_X = -120.0f;

        for (Spatial element : elements){
            element.setLocalTranslation(offset_X, offset_Y, 0.0f);
            element.setLocalScale(3.0f);
            offset_Y -= step_offset_Y;
            SettingPage.attachChild(element);
        }

        clickMeButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                System.out.println("ИГРА НАСТРОЕНА!");
            }
        });

        backButton.addClickCommands(new Command<Button>() {
            @Override
            public void execute( Button source ) {
                setGUIPage(Mode.MAIN_MENU);
            }
        });

        return SettingPage;
    }
    public Mode getGuiPage() {
        return guiPage;
    }
}
