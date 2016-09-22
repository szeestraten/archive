package com.zeestrataca.game;

import com.badlogic.gdx.Game;
import com.zeestrataca.game.screens.Level1;
import com.zeestrataca.game.screens.Level2;
import com.zeestrataca.game.screens.MainMenuScreen;

public enum ScreenEnum {

    MAIN_MENU {
        public AbstractScreen getScreen(Game game) {
            return new MainMenuScreen((MyGdxGame) game);
        }
    },

    LEVEL_1 {
        public AbstractScreen getScreen(Game game) {
            return new Level1((MyGdxGame) game);
        }
    },

    LEVEL_2 {
        public AbstractScreen getScreen(Game game) {
            return new Level2((MyGdxGame) game);
        }
    };

    public abstract AbstractScreen getScreen(Game game);
}
