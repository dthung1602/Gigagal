package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;
import com.x555l.gigagal.util.Constants;


class LevelSelectScreen extends AbstractScreen {
    LevelSelectScreen(Game game) {
        super(
                game,
                Constants.MainMenu.SELECT_LEVEL_WORLD_SIZE
        );
    }

    @Override
    void createLayers(Stack stack) {
        stack.add(createBackgroundLayer());
        stack.add(createBackButtonLayer());
        stack.add(createButtonLayer());
    }

    private Image createBackgroundLayer() {
        return new Image(Assets.instance.background.selectLevel);
    }

    private Table createButtonLayer() {
        Table table = new Table();
        table.padTop(22);

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
            table.add(button)
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
                table.row();
        }

        return table;
    }
}

