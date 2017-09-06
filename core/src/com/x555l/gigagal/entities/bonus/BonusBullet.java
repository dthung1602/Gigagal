package com.x555l.gigagal.entities.bonus;

import com.badlogic.gdx.math.Vector2;
import com.x555l.gigagal.entities.GigaGal;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Constants;


public class BonusBullet extends Bonus {
    public BonusBullet(float x, float y) {
        position = new Vector2(x, y);
        region = Assets.instance.bonus.bullet;
    }

    @Override
    public void performAction(GigaGal gigaGal) {
        if (gigaGal.bullet < Constants.MAX_BULLET)
            gigaGal.bullet += Constants.BONUS_BULLET_AMOUNT;
    }
}
