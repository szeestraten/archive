package com.zeestrataca.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MyGdxGame extends Game {

    public static int WIDTH = 480;
    public static int HEIGHT = 800;
    public OrthographicCamera camera;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);

        Gdx.input.setCatchBackKey(true);

        // Initialize screen manager with game object and set first screen to main menu
        ScreenManager.getInstance().initialize(this);
        ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
    }

    public void render() {
        super.render();

        // Handle escape back to main menu
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ScreenManager.getInstance().showScreen(ScreenEnum.MAIN_MENU);
        }
    }

    public void dispose() {
    }
}
