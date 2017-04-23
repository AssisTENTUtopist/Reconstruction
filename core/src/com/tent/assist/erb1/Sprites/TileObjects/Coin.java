package com.tent.assist.erb1.Sprites.TileObjects;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Scenes.Hud;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Items.ItemDef;
import com.tent.assist.erb1.Sprites.Items.Mushroom;
import com.tent.assist.erb1.Sprites.Knight;
import com.tent.assist.erb1.Sprites.TileObjects.InteractiveTileObject;

public class Coin extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 27 + 1;
    public Coin(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(GdxErb.COIN_BIT);
    }

    @Override
    public void onHeadHit(Knight knight) {
        if (getCell().getTile().getId() == BLANK_COIN)
            GdxErb.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else {
            if (object.getProperties().containsKey("Mushroom")) {
                screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,
                        body.getPosition().y + 16 / GdxErb.PPM), Mushroom.class));
                GdxErb.manager.get("audio/sounds/powerup_spawn.wav", Sound.class).play();
            }
            else GdxErb.manager.get("audio/sounds/coin.wav", Sound.class).play();
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }
}
