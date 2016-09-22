package com.zeestrataca.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.zeestrataca.game.MyGdxGame;

public class PongPadle extends Actor {

    private ShapeRenderer shapeRenderer;
    private Rectangle bounds;
    private boolean player;
    private int paddleHeight = 150;
    private int paddleWidth = 20;

    public PongPadle (boolean player) {
        bounds = new Rectangle(0, 0, paddleWidth, paddleHeight);
        shapeRenderer = new ShapeRenderer();
        this.player = player;

        if (player) {
            bounds.setPosition(0, MyGdxGame.HEIGHT / 2);
        } else {
            bounds.setPosition(MyGdxGame.WIDTH - paddleWidth, MyGdxGame.HEIGHT / 2);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.end();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
        shapeRenderer.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        batch.begin();
    }

    @Override
    public void act(float delta) {
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            // Scale up the touch input for less movement
            touchPos.scl(1, 1.5f, 1);
            MyGdxGame.camera.unproject(touchPos);
            if (player && Gdx.input.getX() < MyGdxGame.WIDTH / 2) {
                movePaddle(touchPos.y);
            } else if (!player && Gdx.input.getX() > MyGdxGame.WIDTH / 2) {
                movePaddle(touchPos.y);
            }
        }
    }

    private void movePaddle(float y) {
        if (y + getHeight() >= MyGdxGame.HEIGHT) {
            bounds.setY(MyGdxGame.HEIGHT - getHeight());
        } else if (y <= 0) {
            bounds.setY(0);
        } else {
            bounds.setY(y);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public float getWidth() {
        return bounds.getWidth();
    }

    @Override
    public float getHeight() {
        return bounds.getHeight();
    }

    @Override
    public float getX() {
        return bounds.getX();
    }

    @Override
    public float getY() {
        return bounds.getY();
    }

}
