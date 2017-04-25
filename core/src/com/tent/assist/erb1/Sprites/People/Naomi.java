package com.tent.assist.erb1.Sprites.People;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Knight;


public class Naomi extends Person {
    private float stateTime;
    private Animation stayAnimation;
    private Array<TextureRegion> frames;

    public Naomi(PlayScreen screen, float x, float y){
        super(screen, x, y);

        frames = new Array<TextureRegion>();
        for (int i = 0; i < 1; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("naomi_overworld_body"), i * 64, 0, 64, 128));
        }
        stayAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 64 / 2 / GdxErb.PPM, 128 / 2 / GdxErb.PPM);
    }

    @Override
    protected void definePerson() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape rektShape = new PolygonShape();
        rektShape.setAsBox(64 / 4 / GdxErb.PPM, 128 / 4 / GdxErb.PPM);
        fdef.filter.categoryBits = GdxErb.PERSON_BIT;
        fdef.filter.maskBits = GdxErb.GROUND_BIT | GdxErb.OBJECT_BIT | GdxErb.MARIO_BIT;

        fdef.shape = rektShape;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2,
                b2body.getPosition().y - getHeight() / 2);
        setRegion(stayAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    public void hitOnHead(Knight knight) {

    }

    @Override
    public void onPersonHit(Person person) {

    }
}
