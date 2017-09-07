package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;
import com.x555l.gigagal.util.Configs;


class SettingScreen extends MyScreen {

    SettingScreen(Game game) {
        super(
                game,
                Constants.SETTING_WORLD_SIZE,
                Assets.instance.background.setting
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
        brightnessSlider.setValue(Configs.instance.getBrightness());
        brightnessSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Configs.instance.setBrightness(((Slider) actor).getValue());
                // TODO change brightness
                System.out.println(((Slider) actor).getValue());
            }
        });

        subTable.add(label);
        subTable.add(brightnessSlider);
        subTable.row().padTop(5);

        // ------------------- FX sound ----------------------------------
        final Slider fxSoundSlider = new Slider(0, 1, 0.05f, false, skin);
        fxSoundSlider.setValue(Configs.instance.getSoundVolume());
        fxSoundSlider.setDisabled(!Configs.instance.isSoundEnabled());
        fxSoundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Configs.instance.setSoundVolume(((Slider) actor).getValue());
            }
        });

        checkBox = new CheckBox("FX sound", skin);
        checkBox.setChecked(Configs.instance.isSoundEnabled());
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox sound = (CheckBox) actor;
                fxSoundSlider.setDisabled(!sound.isChecked());
                Configs.instance.setSoundEnabled(sound.isChecked());
            }
        });

        subTable.add(checkBox).left();
        subTable.add(fxSoundSlider);
        subTable.row().padTop(5);


        // ------------------ music ------------------------------------
        final Slider musicSlider = new Slider(0, 1, 0.05f, false, skin);
        musicSlider.setValue(Configs.instance.getMusicVolume());
        musicSlider.setDisabled(!Configs.instance.isMusicEnabled());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Configs.instance.setMusicVolume(((Slider) actor).getValue());
            }
        });

        checkBox = new CheckBox("Music", skin);
        checkBox.setChecked(Configs.instance.isMusicEnabled());
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox music = (CheckBox) actor;
                musicSlider.setDisabled(!music.isChecked());
                Configs.instance.setMusicEnabled(music.isChecked());
            }
        });

        subTable.add(checkBox).left();
        subTable.add(musicSlider);
        subTable.row().padTop(15);

        //----------------- reset progress ----------------------
        final TextButton resetButton = new TextButton("Reset progress", skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Configs.instance.setCurrentLevel(1);
            }
        });

        subTable.add(resetButton);
        subTable.row();
    }
}
