package com.tent.assist.erb1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tent.assist.erb1.Screens.PlayScreen;

/**
 * Created by Maxs on 23.04.2017.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed, gearPressed;
    OrthographicCamera cam;

    public Controller(PlayScreen screen, Batch batch){
        cam = new OrthographicCamera();
        viewport = new FitViewport(800,480,cam);
        stage = new Stage(viewport, batch);

        stage.addListener(new InputListener(){

            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = true;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = true;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = true;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = true;
                        break;
                    case Input.Keys.P:
                        gearPressed = true;
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch(keycode){
                    case Input.Keys.UP:
                        upPressed = false;
                        break;
                    case Input.Keys.DOWN:
                        downPressed = false;
                        break;
                    case Input.Keys.LEFT:
                        leftPressed = false;
                        break;
                    case Input.Keys.RIGHT:
                        rightPressed = false;
                        break;
                    case Input.Keys.P:
                        gearPressed = false;
                        break;
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);

        final Table table = new Table();
        table.left().bottom();
        Image leftImg = new Image(new TextureRegion(screen.getAtlas().findRegion("arrow_left")));
        leftImg.setSize(100, 100);
        leftImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;

            }
        });
        Image rightImg = new Image(new TextureRegion(screen.getAtlas().findRegion("arrow_right")));
        rightImg.setSize(100, 100);
        rightImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;

            }
        });
        table.right().top();
        final Image settingsImg = new Image(new TextureRegion(screen.getAtlas().findRegion("gear")));
        settingsImg.setSize(100, 100);
        settingsImg.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gearPressed= true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                gearPressed = false;

            }
        });

        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add(rightImg).size(rightImg.getWidth(),rightImg.getHeight());
        table.add(settingsImg).size(settingsImg.getWidth(), settingsImg.getHeight());

        stage.addActor(table);
    }
    public void draw(){
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isGearPressed() {
        return gearPressed;
    }

    public void resize(int width, int height){
        viewport.update(width, height);
    }
}
