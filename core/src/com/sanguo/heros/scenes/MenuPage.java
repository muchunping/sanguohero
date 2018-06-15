package com.sanguo.heros.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanguo.heros.SanguoGame;

/**
 * !Created by muchunping on 2017/10/31.
 */

public class MenuPage implements Screen, InputProcessor {
    private static final String TAG = "MenuPage";
    private final SanguoGame game;
    private Stage stage;

    private Actor actor0 = new AaActor(0);
    private Actor actor1 = new AaActor(1);
    private Actor actor2 = new AaActor(2);
    private Actor actor3 = new AaActor(3);
    private Actor actor4 = new AaActor(4);
    private Actor actor5 = new AaActor(5);
    private Actor actor6 = new AaActor(6);
    private Actor actor7 = new AaActor(7);
    private Actor actor8 = new AaActor(8);

    public MenuPage(SanguoGame sanguoGame){
        this.game = sanguoGame;

        Viewport viewport = new StretchViewport(800, 480);
        stage = new Stage(viewport);
        stage.addActor(actor0);
        stage.addActor(actor1);
        stage.addActor(actor2);
        stage.addActor(actor3);
        stage.addActor(actor4);
        stage.addActor(actor5);
        stage.addActor(actor6);
        stage.addActor(actor7);
        stage.addActor(actor8);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.app.log(TAG, String.valueOf(delta));
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors){
            if(screenX > actor.getX() && screenX < actor.getRight()){
                ((AaActor)actor).setSelected(false);
            }
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private class AaActor extends Actor{
        private Texture texture;
        private Texture texture2;
        final int index;
        private boolean isSelected = true;
        AaActor(int index){
            super();
            this.index = index;
            setSize(100, 100);

            texture = new Texture("ic_launcher.png");
            texture2 = new Texture("badlogic.jpg");
            texture.bind();

            int row = index / 3;
            int column = index % 3;
            setPosition(column * 100, row * 100);
            setOrigin(Align.center);
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(isSelected ?texture: texture2, getX(), getY());
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
