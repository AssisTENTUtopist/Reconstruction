package com.tent.assist.erb1.Sprites.People;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Knight;


public abstract class Person extends Sprite {
    protected World world;
    protected PlayScreen screen;
    public Body b2body;

    public Person(PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition(x, y);
        definePerson();
        b2body.setActive(false);
    }

    protected abstract void definePerson();

    public abstract void update(float dt);

    public abstract void hitOnHead(Knight knight);

    public abstract void onPersonHit(Person person);

}
