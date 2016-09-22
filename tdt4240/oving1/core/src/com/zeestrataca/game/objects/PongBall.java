package com.zeestrataca.game.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PongBall extends Actor {

    private ShapeRenderer shapeRenderer;
    private Rectangle bounds;
    private float initPosX, initPosY;
    private float dx = 4, dy = 4;
    private float size = 12;

    public PongBall (int x, int y) {
        initPosX = x; initPosY = y;
        bounds = new Rectangle(initPosX, initPosY, size, size);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.end();
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(getX(), getY(), size);
        shapeRenderer.end();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        batch.begin();
    }

    @Override
    public void act(float delta) {
        bounds.setPosition(getX() + dx, getY() + dy);
    }

    public void resetBall() {
        bounds.setPosition(initPosX, initPosY);
    }

    public void bounceHorizontally() {
        dx = -dx;
    }

    public void bounceVertically() {
        dy = -dy;
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
