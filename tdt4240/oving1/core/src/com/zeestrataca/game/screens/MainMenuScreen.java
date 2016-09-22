package com.zeestrataca.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zeestrataca.game.MyGdxGame;

public class MainMenuScreen implements Screen {

    private final MyGdxGame game;
    private Stage stage;
    private Skin skin;
    private Table menuTable;

    private TextButton task1Button, task2Button, task3Button, task4Button;

    public MainMenuScreen(final MyGdxGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, game.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        initMenuButtons();
    }

    private void initMenuButtons() {
        // Menu table
        menuTable = new Table();
        menuTable.setFillParent(true);
        stage.addActor(menuTable);

        // Skin
        skin = new Skin();
        skin.add("default", game.font);

        // Button texture
        Pixmap pixmap = new Pixmap(150, 75, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("default", new Texture(pixmap));

        // Button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("default", Color.RED);
        textButtonStyle.checked = skin.newDrawable("default", Color.BLUE);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        task1Button = new TextButton("Task 1", textButtonStyle);
        menuTable.add(task1Button).padBottom(100);
        menuTable.row();
        task1Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.task1);
            }
        });

        task2Button = new TextButton("Task 2", textButtonStyle);
        menuTable.add(task2Button).padBottom(100);
        menuTable.row();
        task2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.task2);
            }
        });

        task3Button = new TextButton("Task 3", textButtonStyle);
        menuTable.add(task3Button).padBottom(100);
        menuTable.row();
        task3Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.task3);
            }
        });

        task4Button = new TextButton("Task 4\n(Pong)", textButtonStyle);
        menuTable.add(task4Button).padBottom(100);
        task4Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(game.task4);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

