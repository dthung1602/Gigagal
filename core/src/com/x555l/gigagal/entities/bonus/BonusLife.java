package com.x555l.gigagal.entities.bonus;

import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class BonusLife extends Bonus {
    public BonusLife(float x, float y) {
        position = new Vector2(x, y);
        region = Assets.instance.bonus.life;
    }

    @Override
    public void performAction(GigaGal gigaGal) {
        if (gigaGal.life < Constants.Gigagal.MAX_LIFE)
            gigaGal.life++;
    }
}
