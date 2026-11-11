package com.badlogic.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
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

public class GameScreen implements Screen {
    final Main game;

    Texture backgroundTexture;
    Texture mainTexture;
    Texture mapTexture;
    Music music;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    Sprite mainSprite;
    Vector2 touchPos;

    public GameScreen(final Main game) {
        this.game = game;

        backgroundTexture = new Texture("Mapa.png");
        mainTexture = new Texture("L.png");

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.5F);

        mainSprite = new Sprite(mainTexture);
        mainSprite.setSize(2, 2);
        mainSprite.setPosition(2, 1);

        touchPos = new Vector2();
    }

    @Override
    public void show() {
        music.play();
    }

    @Override
    public void render(float delta) {
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
    }

    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float spriteWidth = mainSprite.getWidth();
        float spriteHeight = mainSprite.getHeight();

        mainSprite.setX(MathUtils.clamp(mainSprite.getX(), 0, worldWidth - spriteWidth));
        mainSprite.setY(MathUtils.clamp(mainSprite.getY(), 0, worldHeight - spriteHeight));
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(viewport.getCamera().combined);
        game.batch.begin();

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        mainSprite.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        music.dispose();
        mapTexture.dispose();
    }
}
