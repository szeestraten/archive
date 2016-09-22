package com.zeestrataca.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.zeestrataca.game.AbstractScreen;
import com.zeestrataca.game.MyGdxGame;

public class Level2 extends AbstractScreen {

    private Image chopper;
    private TextureRegion chopperTextureRegion;
    private Label position;

    public Level2(MyGdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        addChopper();
        addCounters();
    }

    private void addCounters() {
        Label.LabelStyle labelstyle = new Label.LabelStyle(new BitmapFont(), Color.BLACK);
        position = new Label("X: 0, Y: 0", labelstyle);
        position.setPosition(10 , MyGdxGame.HEIGHT - 30);
        stage.addActor(position);
    }

    private void addChopper() {
        Texture chopperTexture = new Texture(Gdx.files.internal("heli1_east.png"));
        chopperTextureRegion = new TextureRegion(chopperTexture, chopperTexture.getWidth(), chopperTexture.getHeight());
        chopper = new Image(chopperTextureRegion);
        chopper.setPosition(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        chopper.setOrigin(chopper.getWidth() / 2, chopper.getHeight() / 2);
        stage.addActor(chopper);
    }

    private void updateChopper() {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.camera.unproject(touchPos);
            chopper.setX(touchPos.x - chopper.getWidth() / 2);
            chopper.setY(touchPos.y - chopper.getWidth() / 2);
        }

        if (chopper.getX() <= 0) chopper.setX(0);
        if (chopper.getX() >= MyGdxGame.WIDTH - chopper.getWidth()) chopper.setX(MyGdxGame.WIDTH - chopper.getWidth());
        if (chopper.getY() <= 0) chopper.setY(0);
        if (chopper.getY() >= MyGdxGame.HEIGHT - chopper.getHeight()) chopper.setY(MyGdxGame.HEIGHT - chopper.getHeight());
    }

    @Override
    public void render(float delta) {
        super.render(Gdx.graphics.getDeltaTime());
        updateChopper();
        position.setText("X: " + chopper.getX() + " Y: " + chopper.getY());
    }
}
