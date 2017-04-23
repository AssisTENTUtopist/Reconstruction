package com.tent.assist.erb1.Sprites.Enimalies;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Knight;

/**
 * Created by Maxs on 27.03.2017.
 */

public class Kentauros extends Enimaly {
    private float stateTime;
    private Animation walkAnimation;
    private Array<TextureRegion> frames;
    private boolean isSetToDestroy;
    private boolean isDestroyed;

    public Kentauros(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 1; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("kentauros_fine"), i * 128, 0, 128, 128));
        }
        walkAnimation = new Animation(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 128 / 2 / GdxErb.PPM, 128 / 2 / GdxErb.PPM);
        isSetToDestroy = false;
        isDestroyed = false;
    }

    public void update(float dt) {
        stateTime += dt;
        if (isSetToDestroy && !isDestroyed) {
            world.destroyBody(b2body);
            isDestroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("kentauros_uprt"), 0, 0, 102, 102));
            stateTime = 0;
        }
        else if (!isDestroyed) {
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2,
                    b2body.getPosition().y - getHeight() / 2);
            setRegion(walkAnimation.getKeyFrame(stateTime, true));
        }
    }

    @Override
    protected void defineEnimaly() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape rektShape = new PolygonShape();
        rektShape.setAsBox(128 / 4 / GdxErb.PPM, 128 / 4 / GdxErb.PPM);
        fdef.filter.categoryBits = GdxErb.ENIMALY_BIT;
        fdef.filter.maskBits = GdxErb.GROUND_BIT | GdxErb.BRICK_BIT | GdxErb.COIN_BIT
                | GdxErb.ENIMALY_BIT | GdxErb.OBJECT_BIT | GdxErb.MARIO_BIT;

        fdef.shape = rektShape;
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-20, 36).scl(1 / GdxErb.PPM);
        vertice[1] = new Vector2(20, 36).scl(1 / GdxErb.PPM);
        vertice[2] = new Vector2(-10, 23).scl(1 / GdxErb.PPM);
        vertice[3] = new Vector2(10, 23).scl(1 / GdxErb.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = GdxErb.ENIMALY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public void draw(Batch batch) {
        if (!isDestroyed || stateTime < 1)
            super.draw(batch);
    }

    @Override
    public void hitOnHead(Knight knight) {
        isSetToDestroy = true;
        GdxErb.manager.get("audio/sounds/stomp.wav", Sound.class).play();
    }

    @Override
    public void onEnimalyHit(Enimaly enimaly) {
        if (enimaly instanceof Turtle &&
                ((Turtle)enimaly).currentState == Turtle.State.MOVING_SHELL) {
            isSetToDestroy = true;
        }
        else reverseVelocity(true, false);
    }
}
