package com.x555l.gigagal.overlays;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.x555l.gigagal.screens.PlayScreen;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;

public class PauseGameOverlay extends MenuOverlay {
    private Game game;

    public PauseGameOverlay(Game game, Batch batch) {
        super(
                batch,
                new ExtendViewport(Constants.WORLD_SIZE, Constants.WORLD_SIZE),
                Assets.instance.backgroundAssets.overlay
        );

        this.game = game;

        addWidgets();
    }

    @Override
    void addWidgets() {
        Skin skin = Assets.instance.skin;

        // title
        Label title = new Label("PAUSE", skin);
        table.add(title).padBottom(15).row();

        // resume button
        TextButton resume = new TextButton("Resume", skin);
        resume.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.getScreen().resume();
            }
        });
        table.add(resume).padBottom(5).row();

        System.out.println(table.getCells());
    }

    @Override
    public void render() {
        super.render();
    }
}
