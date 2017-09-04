package com.x555l.gigagal.overlays;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.x555l.gigagal.util.Assets;


public class ConfirmOverlay extends MenuOverlay {
    public Boolean confirm;

    public ConfirmOverlay(String text, Batch batch, Viewport viewport) {
        super(
                batch,
                viewport,
                Assets.instance.backgroundAssets.mainMenu
        );

        confirm = null;

        addWidgets();
    }

    @Override
    void addWidgets() {
        Skin skin = Assets.instance.skin;

        // label to display text
        Label label = new Label("", skin);
        table.add(label).pad(2).row();

        // yes button
        TextButton yes = new TextButton("Yes", skin, "default");
        yes.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                confirm = true;
            }
        });
        table.add(yes).pad(15).prefWidth(50);

        // no button
        TextButton no = new TextButton("No", skin, "default");
        no.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                confirm = false;
            }
        });
        table.add(no).pad(15).prefWidth(50);
    }
}
