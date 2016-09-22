package com.zeestrataca.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zeestrataca.game.MyGdxGame;
import com.zeestrataca.game.objects.AnimatedChopper;

public class Task3 implements Screen {

    private final MyGdxGame game;
    private Stage stage;
    private AnimatedChopper chopper;
    private TextureRegion[] chopperAnimationFrames;
    private Boolean chopperDirectionRight, chopperDirectionUp;
    private Animation chopperAnimation;

    public Task3(final MyGdxGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, game.camera));
        game.camera = new OrthographicCamera();
        game.camera.setToOrtho(false, MyGdxGame.WIDTH, MyGdxGame.HEIGHT);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        addAnimatedChopper();
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
        if (chopperDirectionRight)
            chopper.addAction(Actions.moveBy(5, 0));
        else
            chopper.addAction(Actions.moveBy(-5, 0));

        if (chopper.getX() <= 0) { chopperDirectionRight = true; chopper.flip(); }
        if (chopper.getX() >= MyGdxGame.WIDTH - chopper.getWidth()) { chopperDirectionRight = false; chopper.flip(); }

        if (chopperDirectionUp)
            chopper.addAction(Actions.moveBy(0, 5));
        else
            chopper.addAction(Actions.moveBy(0, -5));

        if (chopper.getY() <= 0) { chopperDirectionUp = true;}
        if (chopper.getY() >= MyGdxGame.HEIGHT - chopper.getHeight()) { chopperDirectionUp = false; }
    }

    private void addAnimatedChopper() {
        chopperDirectionRight = false;
        chopperDirectionUp = false;
        Texture chopperTexture = new Texture(Gdx.files.internal("heli_frames.png"));
        TextureRegion[][] tmpFrames = TextureRegion.split(chopperTexture, 130, 52);
        chopperAnimationFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                chopperAnimationFrames[index++] = tmpFrames[j][i];
            }
        }

        chopperAnimation = new Animation(1f/10f, chopperAnimationFrames);
        chopper = new AnimatedChopper(chopperAnimation);
        chopper.setPosition(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        chopper.setOrigin(chopper.getWidth() / 2, chopper.getHeight() / 2);
        stage.addActor(chopper);
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
