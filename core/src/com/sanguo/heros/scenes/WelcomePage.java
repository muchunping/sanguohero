package com.sanguo.heros.scenes;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanguo.heros.SanguoGame;

/**
 * 欢迎页
 * !Created by muchunping on 2017/10/23.
 */

public class WelcomePage implements Screen, InputProcessor {
    private Runnable runnable;
    private SanguoGame game;

    private Stage stage;

    private TiledMap map;
    private MapRenderer mapRenderer;
    private OrthographicCamera camera;

    int x = 0, y = 0;

    public WelcomePage(SanguoGame game) {
        this.game = game;

        camera = new OrthographicCamera(1600, 980);
        camera.setToOrtho(false, 1600, 980);

        Viewport viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.getBatch());

        map = new TmxMapLoader().load("first.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        Actor actor = new Actor();
        MoveToAction action = new MoveToAction();
        action.setPosition(100, 100);
        actor.addAction(action);
        stage.addActor(actor);
    }

    @Override
    public void show() {
        runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
    }

    @Override
    public void render(float delta) {
        camera.update();
        stage.act();
        stage.draw();
        mapRenderer.setView(camera);
        mapRenderer.render();
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
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
                x--;
                break;
            case Input.Keys.D:
                x++;
                break;
            case Input.Keys.W:
                y--;
                break;
            case Input.Keys.S:
                y++;
                break;
            default:
                return false;
        }
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        camera.translate(x, y);
        return true;
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
}
