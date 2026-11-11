package com.badlogic.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Input;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    Texture backgroundTexture;
    Texture mainTexture;
    Texture mapTexture;
    Music music;
    SpriteBatch spriteBatch;
    FitViewport viewport;

    Sprite mainSprite;

    Vector2 touchPos;
    @Override
    public void create() {
        backgroundTexture = new Texture("Mapa.png");
        mainTexture = new Texture("L.png");
        // mapTexture = new Texture("Bill's House.png");

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(13, 10);

        mainSprite = new Sprite(mainTexture);
        mainSprite.setSize(1.5f, 1.5f);
        mainSprite.setPosition(6, 4.5f);

        touchPos = new Vector2();
    }

    @Override
    public void resize(int width, int height) {
        if(width <= 0 || height <= 0) return;
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = 4f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            mainSprite.translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            mainSprite.translateX(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            mainSprite.translateY(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            mainSprite.translateY(-speed * delta);
        }
        /*
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);
            mainSprite.setCenterX(touchPos.x);
        }
        */
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float spriteWidth = mainSprite.getWidth();
        float spriteHeight = mainSprite.getHeight();

        mainSprite.setX(MathUtils.clamp(mainSprite.getX(), 1.2f, worldWidth - spriteWidth - 1));
        mainSprite.setY(MathUtils.clamp(mainSprite.getY(), 1.2f, worldHeight - spriteHeight - 1.2f));
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float larguraboneco = mainSprite.getWidth();
        float alturaBoneco = mainSprite.getHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        // spriteBatch.draw(mapTexture, 2, 1, 9, 8);
        mainSprite.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {

    }
}
