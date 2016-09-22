package com.zeestrataca.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zeestrataca.game.MyGdxGame;

public class Task1 implements Screen {

    private final MyGdxGame game;
    private Stage stage;
    private Image chopper;
    private TextureRegion chopperTextureRegion;
    private Boolean chopperDirection;

    public Task1(final MyGdxGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, game.camera));
        game.camera = new OrthographicCamera();
        game.camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        addChopper();
    }

    private void addChopper() {
        chopperDirection = true;
        Texture chopperTexture = new Texture(Gdx.files.internal("heli1_east.png"));
        chopperTextureRegion = new TextureRegion(chopperTexture, chopperTexture.getWidth(), chopperTexture.getHeight());
        chopper = new Image(chopperTextureRegion);
        chopper.setPosition(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        chopper.setOrigin(chopper.getWidth() / 2, chopper.getHeight() / 2);
        stage.addActor(chopper);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateChopper();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void updateChopper() {
        if (chopperDirection) {
            chopper.addAction(Actions.moveBy(10, 0));
        } else {
            chopper.addAction(Actions.moveBy(-10, 0));
        }

        if (chopper.getX() <= 0) {
            chopperTextureRegion.flip(true, false);
            chopperDirection = true;
        }
        if (chopper.getX() >= MyGdxGame.WIDTH - chopper.getWidth()) {
            chopperTextureRegion.flip(true, false);
            chopperDirection = false;
        }
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
    }
}
