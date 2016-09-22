package com.zeestrataca.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class ScreenManager {

    // Singleton
    private static ScreenManager instance;

    private Game game;

    private ScreenManager() {
    }

    // Get singleton instance if not created already.
    // This is the lazy initialization method
    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    // Need a reference to the game object
    public void initialize(Game game) {
        this.game = game;
    }

    // Show a new screen
    public void showScreen(ScreenEnum screenEnum) {
        Screen currentScreen = game.getScreen();
        Screen newScreen = screenEnum.getScreen(game);
        game.setScreen(newScreen);

        // Dispose of old screen
        if (currentScreen != null) {
            currentScreen.dispose();
        }

    }
}
