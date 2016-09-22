package com.zeestrataca.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zeestrataca.game.screens.MainMenuScreen;
import com.zeestrataca.game.screens.Task1;
import com.zeestrataca.game.screens.Task2;
import com.zeestrataca.game.screens.Task3;
import com.zeestrataca.game.screens.Task4;

public class MyGdxGame extends Game {

    public static int WIDTH = 480;
    public static int HEIGHT = 800;

    public static OrthographicCamera camera;
    public SpriteBatch batch;
    public BitmapFont font;
    public AssetManager assets;

    public MainMenuScreen mainMenuScreen;
    public Task1 task1;
    public Task2 task2;
    public Task3 task3;
    public Task4 task4;

    @Override
    public void create() {
        assets = new AssetManager();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        batch = new SpriteBatch();
        font = new BitmapFont();

        mainMenuScreen = new MainMenuScreen(this);
        task1 = new Task1(this);
        task2 = new Task2(this);
        task3 = new Task3(this);
        task4 = new Task4(this);

        Gdx.input.setCatchBackKey(true);

        this.setScreen(mainMenuScreen);
    }

    public void render() {
        super.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (this.getScreen() == mainMenuScreen) {
                Gdx.app.exit();
            } else {
                this.setScreen(mainMenuScreen);
            }
        }
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        assets.dispose();
        mainMenuScreen.dispose();
        task1.dispose();
        task2.dispose();
        task3.dispose();
        task4.dispose();
    }
}
