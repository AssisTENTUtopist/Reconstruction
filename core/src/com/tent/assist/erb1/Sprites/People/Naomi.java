package com.tent.assist.erb1.Sprites.People;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Knight;

/**
 * Created by Maxs on 23.04.2017.
 */

public class Naomi extends Person {

    public Naomi(PlayScreen screen, float x, float y){
        super(screen, x, y);


    }

    @Override
    protected void definePerson() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape rektShape = new PolygonShape();
        rektShape.setAsBox(64 / 4 / GdxErb.PPM, 64 / GdxErb.PPM);
        fdef.filter.categoryBits = GdxErb.PERSON_BIT;
        fdef.filter.maskBits = GdxErb.GROUND_BIT | GdxErb.OBJECT_BIT | GdxErb.MARIO_BIT;

        fdef.shape = rektShape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void hitOnHead(Knight knight) {

    }

    @Override
    public void onPersonHit(Person person) {

    }
}
