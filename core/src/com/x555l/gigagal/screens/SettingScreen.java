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
import com.x555l.gigagal.util.Settings;


class SettingScreen extends MyScreen {

    SettingScreen(Game game) {
        super(
                game,
                Constants.SETTING_WORLD_SIZE,
                Assets.instance.screenBackgroundAssets.setting
        );
    }

    void createWidgets(Table table) {
        Label label;
        CheckBox checkBox;

        // add back to menu button
        Table subTable = createBackButton(table);

        // ------------------- brightness --------------------------------
        label = new Label("Brightness", skin, "default");
        final Slider brightnessSlider = new Slider(0, 1, 0.05f, false, skin);
        brightnessSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // TODO change brightness
                System.out.println(((Slider) actor).getValue());
            }
        });

        subTable.add(label);
        subTable.add(brightnessSlider);
        subTable.row().padTop(5);

        // ------------------- FX sound ----------------------------------
        final Slider fxSoundSlider = new Slider(0, 1, 0.05f, false, skin);
        fxSoundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.instance.setSoundVolume(((Slider) actor).getValue());
            }
        });

        checkBox = new CheckBox("FX sound", skin);
        checkBox.setChecked(true);
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox sound = (CheckBox) actor;
                fxSoundSlider.setDisabled(!sound.isChecked());
                Settings.instance.setSoundEnabled(sound.isChecked());
            }
        });

        subTable.add(checkBox).left();
        subTable.add(fxSoundSlider);
        subTable.row().padTop(5);


        // ------------------ music ------------------------------------
        final Slider musicSlider = new Slider(0, 1, 0.05f, false, skin);
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.instance.setMusicVolume(((Slider) actor).getValue());
            }
        });

        checkBox = new CheckBox("Music", skin);
        checkBox.setChecked(true);
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox music = (CheckBox) actor;
                musicSlider.setDisabled(!music.isChecked());
                Settings.instance.setMusicEnabled(music.isChecked());
            }
        });

        subTable.add(checkBox).left();
        subTable.add(musicSlider);
        subTable.row();
    }
}
