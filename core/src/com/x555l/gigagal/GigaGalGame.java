package com.x555l.gigagal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.x555l.gigagal.screens.MainMenuScreen;
import com.x555l.gigagal.util.Assets;
import com.x555l.gigagal.util.Configs;

import java.lang.reflect.Method;


public class GigaGalGame extends Game {

    @Override
    public void create() {
        Assets.instance.load();
        Configs.instance.load();
        debug();
        setScreen(new MainMenuScreen(this));
    }

    private void debug() {
        final String TAG = "GGG";
        Gdx.app.log(TAG, "---------- DEBUG CALLED -----------");
        try {
            Class<?> c = Class.forName("com.x555l.gigagal.Demo");
            Method method = c.getDeclaredMethod("print");
            Demo demo = new Demo();
            method.invoke(demo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Gdx.app.log(TAG, "---------- DEBUG ENDED -----------");
    }
}

class Demo {
    static void staticPrint() {
        System.out.println("STATIC");
    }

    void print() {
        System.out.println("METHOD PRINT");
    }

    void print2() {
        print();
        print();
    }
}