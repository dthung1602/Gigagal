package com.x555l.gigagal.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.util.Constants;


abstract class MenuOverlay {
    private Stage stage;
    Table table;

    MenuOverlay(Batch batch, Viewport viewport, TextureRegion background) {
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // create table
        table = new Table();
        table.debug();
        stage.addActor(table);

        // add background to table
        table.setBackground(new TextureRegionDrawable(background));

        // set overlay to screen center
        table.setBounds(
                Constants.CONFIRM_OVELAY_PADDING.x,
                Constants.CONFIRM_OVELAY_PADDING.y,
                viewport.getWorldWidth() - Constants.CONFIRM_OVELAY_PADDING.x * 2,
                viewport.getWorldHeight() - Constants.CONFIRM_OVELAY_PADDING.y * 2
        );
    }

    abstract void addWidgets();

    public void render() {
        stage.draw();
    }

    public Stage getStage() {
        return stage;
    }
}
