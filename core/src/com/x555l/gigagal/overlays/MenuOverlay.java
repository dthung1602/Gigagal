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
    public Stage stage;
    Table table;

    ConfirmOverlay confirmOverlay;

    MenuOverlay(Batch batch, Viewport viewport, TextureRegion background) {
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // create table
        table = new Table();
        stage.addActor(table);

        // add background to table
        table.setBackground(new TextureRegionDrawable(background));

        // set overlay to screen center
        table.setBounds(
                Constants.GameWorld.MENU_OVERLAY_PADDING.x,
                Constants.GameWorld.MENU_OVERLAY_PADDING.y,
                viewport.getWorldWidth() - Constants.GameWorld.MENU_OVERLAY_PADDING.x * 2,
                viewport.getWorldHeight() - Constants.GameWorld.MENU_OVERLAY_PADDING.y * 2
        );
    }

    abstract void addWidgets();

    public void render() {
        if (confirmOverlay == null) {
            stage.draw();
        } else {
            if (confirmOverlay.confirm == null)
                confirmOverlay.render();
            else if (confirmOverlay.confirm)
                confirmOverlay.action.yes();
            else
                confirmOverlay.action.no();
        }

    }
}
