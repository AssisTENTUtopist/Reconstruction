package com.tent.assist.erb1.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tent.assist.erb1.GdxErb;

//https://bitbucket.org/dermetfan/blackpoint2/src/2bc5237d43a8e8090d188a1ce19398a8307b3e05/src/net/dermetfan/blackpoint2/screens/Settings.java?at=default&fileviewer=file-view-default

public class Pause implements Disposable{
    public Stage stage;
    private Viewport viewport;

    private Window window;

    private boolean isPaused;
    private Drawable background, butt;
    public Pause(SpriteBatch sb, TextureAtlas atlas) {
        viewport = new FitViewport(GdxErb.V_WIDTH, GdxErb.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);
        background = new TextureRegionDrawable(atlas.findRegion("field"));
        butt = new TextureRegionDrawable(atlas.findRegion("gear"));

        window = new Window("PAUSE", new Window.WindowStyle(new BitmapFont(), Color.BLUE, background));
        TextButton cuntButton = new TextButton("Continue", new TextButton.TextButtonStyle(butt, butt, butt, new BitmapFont()));
        cuntButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setPaused(false);
            }
        });
        window.add(cuntButton);
        window.setSize(stage.getWidth() / 1.5f, stage.getHeight() / 1.5f);
        window.setPosition(stage.getWidth() / 2 - window.getWidth() / 2, stage.getHeight() / 2 - window.getHeight() / 2);
        //window.setMovable(false);
        stage.addActor(window);
    }

    public void update(float dt) {
        if (!isPaused()) window.setVisible(false);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
