package com.zeestrataca.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.zeestrataca.game.AbstractScreen;
import com.zeestrataca.game.MyGdxGame;
import com.zeestrataca.game.ScreenEnum;
import com.zeestrataca.game.ScreenManager;

public class MainMenuScreen extends AbstractScreen {

    private Skin skin;
    private Table menuTable;
    private TextButton task1Button, task2Button;

    public MainMenuScreen(MyGdxGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        initMenuButtons();
    }

    private void initMenuButtons() {
        // Menu table
        menuTable = new Table();
        menuTable.setFillParent(true);
        super.stage.addActor(menuTable);

        // Skin
        skin = new Skin();
        skin.add("default", new BitmapFont());

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
                ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL_1);
            }
        });

        task2Button = new TextButton("Task 2", textButtonStyle);
        menuTable.add(task2Button).padBottom(100);
        menuTable.row();
        task2Button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ScreenManager.getInstance().showScreen(ScreenEnum.LEVEL_2);
            }
        });
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
