package com.tent.assist.erb1.Sprites.Items;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Knight;

/**
 * Created by Maxs on 29.03.2017.
 */

public class Mushroom extends Item {
    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("gear"), 0, 0, 128, 128);
        velocity = new Vector2(0.7f, 0);
    }

    @Override
    public void defineItem() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape rektShape = new PolygonShape();
        rektShape.setAsBox(32 / 4 / GdxErb.PPM, 32 / 4 / GdxErb.PPM);
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GdxErb.PPM);
        fdef.filter.categoryBits = GdxErb.ITEM_BIT;
        fdef.filter.maskBits = GdxErb.MARIO_BIT | GdxErb.COIN_BIT | GdxErb.OBJECT_BIT |
                GdxErb.GROUND_BIT | GdxErb.BRICK_BIT;

        fdef.shape = shape;
        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Knight knight) {
        knight.grow();
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        velocity.y = body.getLinearVelocity().y;
        body.setLinearVelocity(velocity);
    }
}
