package com.zeestrataca.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.zeestrataca.game.AbstractScreen;
import com.zeestrataca.game.MyGdxGame;

public class Level1 extends AbstractScreen {

    private Image chopper;
    private TextureRegion chopperTextureRegion;
    private Boolean chopperDirectionRight, chopperDirectionUp;

    public Level1(MyGdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        addChopper();
    }

    private void addChopper() {
        chopperDirectionRight = true;
        chopperDirectionUp = true;
        Texture chopperTexture = new Texture(Gdx.files.internal("heli1_east.png"));
        chopperTextureRegion = new TextureRegion(chopperTexture, chopperTexture.getWidth(), chopperTexture.getHeight());
        chopper = new Image(chopperTextureRegion);
        chopper.setPosition(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        chopper.setOrigin(chopper.getWidth() / 2, chopper.getHeight() / 2);
        stage.addActor(chopper);
    }

    @Override
    public void render(float delta) {
        super.render(Gdx.graphics.getDeltaTime());
        updateChopper();
    }

    private void updateChopper() {
        // Horizontal direction
        if (chopperDirectionRight) {
            chopper.addAction(Actions.moveBy(5, 0));
        } else {
            chopper.addAction(Actions.moveBy(-5, 0));
        }

        if (chopper.getX() <= 0) {
            chopperTextureRegion.flip(true, false);
            chopperDirectionRight = true;
        } else if (chopper.getX() >= MyGdxGame.WIDTH - chopper.getWidth()) {
            chopperTextureRegion.flip(true, false);
            chopperDirectionRight = false;
        }

        // Vertical direction
        if (chopperDirectionUp) {
            chopper.addAction(Actions.moveBy(0, 5));
        } else {
            chopper.addAction(Actions.moveBy(0, -5));
        }

        if (chopper.getY() <= 0) {
            chopperDirectionUp = true;
        } else if (chopper.getY() >= MyGdxGame.HEIGHT - chopper.getHeight()) {
            chopperDirectionUp = false;
        }
    }
}
