package com.zeestrataca.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zeestrataca.game.MyGdxGame;
import com.zeestrataca.game.objects.PongBall;
import com.zeestrataca.game.objects.PongPadle;

public class Task4 implements Screen {

    private final MyGdxGame game;
    private Stage stage;

    private Label scoreCounter;
    private int playerOneScore, playerTwoScore;
    private String scoreString;

    private PongBall pongBall;
    private PongPadle playerOnePadle, playerTwoPadle;
    private Rectangle leftBound, rightBound, topBound, bottomBound;

    public Task4(final MyGdxGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(MyGdxGame.WIDTH, MyGdxGame.HEIGHT, game.camera));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();

        initScoreCounter();
        initBall();
        initPaddles();
        initWorldBounds();
    }

    private void initWorldBounds() {
        leftBound = new Rectangle(0, 0, 0, MyGdxGame.HEIGHT);
        rightBound = new Rectangle(MyGdxGame.WIDTH, 0, 0, MyGdxGame.HEIGHT);
        topBound = new Rectangle(0, MyGdxGame.HEIGHT, MyGdxGame.WIDTH, 0);
        bottomBound = new Rectangle(0, 0, MyGdxGame.WIDTH, 0);
    }

    private void initPaddles() {
        playerOnePadle = new PongPadle(true);
        playerTwoPadle = new PongPadle(false);
        stage.addActor(playerOnePadle);
        stage.addActor(playerTwoPadle);
    }

    private void initBall() {
        pongBall = new PongBall(MyGdxGame.WIDTH / 2, MyGdxGame.HEIGHT / 2);
        stage.addActor(pongBall);
    }

    private void initScoreCounter() {
        Label.LabelStyle labelstyle = new Label.LabelStyle(game.font, Color.WHITE);
        scoreCounter = new Label(scoreString, labelstyle);
        scoreCounter.setFontScale(2);
        scoreCounter.setPosition((MyGdxGame.WIDTH - scoreCounter.getWidth()) / 2 - 50 , MyGdxGame.HEIGHT - scoreCounter.getHeight() - 10);
        setScore(0, 0);
        stage.addActor(scoreCounter);
    }

    private void setScore(int playerOne, int playerTwo) {
        playerOneScore = playerOne;
        playerTwoScore = playerTwo;
        scoreCounter.setText("P1: " + playerOneScore + " | P2: " + playerTwoScore);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        checkCollision();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    private void checkCollision() {
        // Bounce vertically on paddles
        if (pongBall.getBounds().overlaps(playerOnePadle.getBounds()) || pongBall.getBounds().overlaps(playerTwoPadle.getBounds())) {
            pongBall.bounceHorizontally();
        } else
        // Bounce horizontally on bounds
        if (pongBall.getBounds().overlaps(topBound) || pongBall.getBounds().overlaps(bottomBound)) {
            pongBall.bounceVertically();
        } else
        // Player One Score
        if (pongBall.getBounds().overlaps(rightBound)) {
            setScore(playerOneScore + 1, playerTwoScore);
            pongBall.resetBall();
        } else
        // Player Two Score
        if (pongBall.getBounds().overlaps(leftBound)) {
            setScore(playerOneScore, playerTwoScore + 1);
            pongBall.resetBall();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        game.camera.position.set(game.camera.viewportWidth/2, game.camera.viewportHeight/2, 0);
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
