package com.x555l.gigagal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;
import com.x555l.gigagal.util.Constants;


class SettingScreen extends AbstractScreen {

    SettingScreen(Game game) {
        super(
                game,
                Constants.MainMenu.SETTING_WORLD_SIZE
        );
    }

    @Override
    public void hide() {
        Configs.instance.save();
    }

    @Override
    void createLayers(Stack stack) {
        stack.add(createBackgroundLayer());
        stack.add(createBackButtonLayer());
        stack.add(createButtonLayer());
    }

    private Image createBackgroundLayer() {
        return new Image(Assets.instance.background.setting);
    }

    private Table createButtonLayer() {
        Table table = new Table();

        addBrightness(table);
        addSound(table);
        addMusic(table);
        addDebug(table);
        addResetProgress(table);

        return table;
    }

    private void addBrightness(Table table) {
        final Label label = new Label("Brightness", skin, "default");

        final Slider brightnessSlider = new Slider(0, 1, 0.05f, false, skin);
        brightnessSlider.setValue(Configs.instance.getBrightness());
        brightnessSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Configs.instance.setBrightness(((Slider) actor).getValue());
                // TODO change brightness
            }
        });

        table.add(label);
        table.add(brightnessSlider);
        table.row().padTop(5);
    }

    private void addSound(Table table) {
        final Slider fxSoundSlider = new Slider(0, 1, 0.05f, false, skin);
        fxSoundSlider.setValue(Configs.instance.getSoundVolume());
        fxSoundSlider.setDisabled(!Configs.instance.isSoundEnabled());
        fxSoundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Configs.instance.setSoundVolume(((Slider) actor).getValue());
            }
        });

        final CheckBox checkBox = new CheckBox("FX sound", skin);
        checkBox.setChecked(Configs.instance.isSoundEnabled());
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox sound = (CheckBox) actor;
                fxSoundSlider.setDisabled(!sound.isChecked());
                Configs.instance.setSoundEnabled(sound.isChecked());
            }
        });

        table.add(checkBox).left();
        table.add(fxSoundSlider);
        table.row().padTop(5);
    }

    private void addMusic(Table table) {
        final Slider musicSlider = new Slider(0, 1, 0.05f, false, skin);
        musicSlider.setValue(Configs.instance.getMusicVolume());
        musicSlider.setDisabled(!Configs.instance.isMusicEnabled());
        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Configs.instance.setMusicVolume(((Slider) actor).getValue());
            }
        });

        final CheckBox checkBox = new CheckBox("Music", skin);
        checkBox.setChecked(Configs.instance.isMusicEnabled());
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox music = (CheckBox) actor;
                musicSlider.setDisabled(!music.isChecked());
                Configs.instance.setMusicEnabled(music.isChecked());
            }
        });

        table.add(checkBox).left();
        table.add(musicSlider);
        table.row().padTop(15).padBottom(20);
    }

    private void addDebug(Table table) {
        // Label
        Label label = new Label("Debug", skin);
        label.setColor(Color.BLUE);
        table.add(label).left().padBottom(10).row();

        // Screen layout
        CheckBox checkBox = new CheckBox("Screen layout", skin);
        checkBox.setChecked(Configs.instance.isDebugScreenLayoutEnabled());
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox dbScreenLayout = (CheckBox) actor;
                Configs.instance.setDebugScreenLayoutEnabled(dbScreenLayout.isChecked());
            }
        });
        table.add(checkBox).left().padLeft(20).row();

        // FPS
        checkBox = new CheckBox("FPS in play screen", skin);
        checkBox.setChecked(Configs.instance.isDebugFPSEnabled());
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox dbFPS = (CheckBox) actor;
                Configs.instance.setDebugFPSEnabled(dbFPS.isChecked());
            }
        });
        table.add(checkBox).left().padLeft(20).row();

        // On screen control
        checkBox = new CheckBox("On screen control", skin);
        checkBox.setChecked(Configs.instance.isDebugOnScreenControlEnabled());
        checkBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox dbOnScreenCtrl = (CheckBox) actor;
                Configs.instance.setDebugOnScreenControl(dbOnScreenCtrl.isChecked());
            }
        });
        table.add(checkBox).left().padLeft(20).row();
    }

    private void addResetProgress(Table table) {
        final TextButton resetButton = new TextButton("Reset progress", skin);
        resetButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Configs.instance.setCurrentLevel(1);
            }
        });

        table.add(resetButton).padTop(15);
        table.row();
    }
}
