package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class SettingScreen extends MyScreen {
    public SettingScreen(Game game) {
        super(
                game,
                Constants.SETTING_WORLD_SIZE,
                Assets.instance.screenBackgroundAssets.setting
        );
    }


    void createWidgets(Table table) {
        Label label;
        CheckBox checkBox;

        // brightness
        label = new Label("Brightness", skin, "default");
        final Slider brightnessSlider = new Slider(0, 1, 0.05f, false, skin);
        brightnessSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO change brightness
                System.out.println(((Slider) actor).getValue());
            }
        });
        table.add(label);
        table.add(brightnessSlider);
        table.row().padTop(5);

        // FX sound
        checkBox = new CheckBox("FX sound", skin);
        checkBox.setChecked(true);
        final Slider fxSoundSlider = new Slider(0, 1, 0.05f, false, skin);
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO sound
                CheckBox sound = (CheckBox) actor;
                fxSoundSlider.setDisabled(!sound.isChecked());
            }
        });
        table.add(checkBox);
        table.add(fxSoundSlider);
        table.row().padTop(5);

        // music
        checkBox = new CheckBox("Music", skin);
        checkBox.setChecked(true);
        final Slider musicSlider = new Slider(0, 1, 0.05f, false, skin);
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO music
                CheckBox music = (CheckBox) actor;
                musicSlider.setDisabled(!music.isChecked());
            }
        });
        table.add(checkBox);
        table.add(musicSlider);
        table.row();
    }
}
