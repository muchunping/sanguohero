package com.sanguo.heros;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sanguo.heros.scenes.MenuPage;

/**
 * 程序入口
 */
public class SanguoGame extends Game {
    /**画笔*/
    private SpriteBatch batch;
    private Screen screen;

    @Override
    public void create() {
        batch = new SpriteBatch();
//        screen = new WelcomePage(this);
        screen = new MenuPage(this);
        setScreen(screen);

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        screen.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
