package com.tent.assist.erb1.Sprites.Enimalies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Knight;

public abstract class Enimaly extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;

    public Enimaly(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        defineEnimaly();
        velocity = new Vector2(1, 0);
        b2body.setActive(false);
    }

    protected abstract void defineEnimaly();

    public abstract void update(float dt);

    public abstract void hitOnHead(Knight knight);

    public abstract void onEnimalyHit(Enimaly enimaly);

    public void reverseVelocity(boolean x, boolean y) {
        if (x) velocity.x = -velocity.x;
        if (y) velocity.y = -velocity.y;
    }
}
