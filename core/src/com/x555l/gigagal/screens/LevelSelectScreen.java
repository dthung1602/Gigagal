package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;
import com.x555l.gigagal.util.Constants;


class LevelSelectScreen extends ScreenAdapter {
    LevelSelectScreen(Game game) {
        super(
                game,
                Constants.MainMenu.SELECT_LEVEL_WORLD_SIZE,
                Assets.instance.background.selectLevel
        );
    }

    @Override
    void createWidgets(Table table) {

        Table subTable = createBackButton(table);

        for (int i = 0; i < Constants.Level.MAX_LEVEL; i++) {
            // new button
            TextButton button;

            if (i + 1 <= Configs.instance.getCurrentLevel()) {
                // unlocked levels
                button = new TextButton((i + 1) + "", skin, "default");
            } else {
                // locked levels
                // TODO add new style name other than "default" for disabled levels
                button = new TextButton((i + 1) + "", skin, "default");
                button.setDisabled(true);
            }

            // format the button
            subTable.add(button)
                    .pad(5, 5, 5, 5)
                    .prefSize(Constants.Level.SELECT_LEVEL_BUTTON_SIZE.x,
                            Constants.Level.SELECT_LEVEL_BUTTON_SIZE.y);

            // add action to button
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    int levelNum = Integer.parseInt(((TextButton) actor).getText().toString());
                    game.setScreen(new PlayScreen(game, levelNum));
                }
            });

            // end row
            if ((i + 1) % Constants.Level.SELECT_LEVEL_NUMBER_OF_COLUMN == 0)
                subTable.row();
        }
    }
}
