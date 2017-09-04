package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.x555l.gigagal.overlays.ConfirmOverlay;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


class LevelSelectScreen extends MyScreen {
    LevelSelectScreen(Game game) {
        super(
                game,
                Constants.SELECT_LEVEL_WORLD_SIZE,
                Assets.instance.screenBackgroundAssets.selectLevel
        );
    }

    @Override
    void createWidgets(Table table) {

        // TODO consider this vs background in MyScreen
//        TextureRegionDrawable texture = new TextureRegionDrawable(Assets.instance.screenBackgroundAssets.setting);
//        table.setBackground(texture);

        Table subTable = createBackButton(table);

        for (int i = 0; i < Constants.MAX_LEVEL; i++) {
            // new button
            TextButton button = new TextButton((i + 1) + "", skin, "default");

            // format the button
            subTable.add(button)
                    .pad(5, 5, 5, 5)
                    .prefSize(Constants.SELECT_LEVEL_SIZE.x,
                            Constants.SELECT_LEVEL_SIZE.y);

            // add action to button
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int levelNum = Integer.parseInt(((TextButton) actor).getText().toString());
                    game.setScreen(new PlayScreen(game, levelNum));
                }
            });

            // end row
            if ((i + 1) % Constants.SELECT_LEVEL_NUMBER_OF_COLUMN == 0)
                subTable.row();
        }

        overlay = new ConfirmOverlay("Are you sure?",
                game, stage.getBatch(), stage.getViewport());
    }

    ConfirmOverlay overlay;

    @Override
    // DEBUG
    public void render(float delta) {
        super.render(delta);
        overlay.render();
    }
}
