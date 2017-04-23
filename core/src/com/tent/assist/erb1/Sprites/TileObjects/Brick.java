package com.tent.assist.erb1.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Scenes.Hud;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Knight;
import com.tent.assist.erb1.Sprites.TileObjects.InteractiveTileObject;

public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(GdxErb.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Knight knight) {
        if (knight.isMarioBig()) {
            setCategoryFilter(GdxErb.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            GdxErb.manager.get("audio/sounds/breakblock.wav", Sound.class).play();
        }
        GdxErb.manager.get("audio/sounds/bump.wav", Sound.class).play();
    }
}
