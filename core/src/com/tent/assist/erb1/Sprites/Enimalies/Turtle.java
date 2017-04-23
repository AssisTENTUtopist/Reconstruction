package com.tent.assist.erb1.Sprites.Enimalies;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Knight;

/**
 * Created by Maxs on 31.03.2017.
 */

public class Turtle extends Enimaly {
    public static final int KICK_LEFT_SPEED = -2;
    public static final int KICK_RIGHT_SPEED = 2;
    public enum State { WALKING, STANDING_SHELL, MOVING_SHELL, DEAD }
    public State currentState;
    public State previousState;
    private float stateTime;
    private Array<TextureRegion> frames;
    private TextureRegion shell, walk;
    private float deadRotationDegrees;
    private boolean isSetToDestroy;
    private boolean isDestroyed;

    public Turtle(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        walk = new TextureRegion(screen.getAtlas().findRegion("griffin"), 0, 0, 128, 128);
        shell = new TextureRegion(screen.getAtlas().findRegion("griffin_uprt"), 0, 0, 128, 128);
        currentState = previousState = State.WALKING;
        deadRotationDegrees = 0;

        setBounds(getX(), getY(), 16 / GdxErb.PPM,  24 / GdxErb.PPM);
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
        fdef.restitution = 1.5f;
        fdef.filter.categoryBits = GdxErb.ENIMALY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    public TextureRegion getFrame(float dt) {
        TextureRegion region;
        switch (currentState) {
            case MOVING_SHELL:
            case STANDING_SHELL:
                region = shell;
                break;
            case WALKING:
            default:
                region = walk;
                break;
        }
        if (velocity.x > 0 && !region.isFlipX()) {
            region.flip(true, false);
        }
        if (velocity.x < 0 && region.isFlipX()) {
            region.flip(true, false);
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }

    @Override
    public void update(float dt) {
        setRegion(getFrame(dt));
        if (currentState == State.STANDING_SHELL && stateTime > 5) {
            currentState = State.WALKING;
            velocity.x = 1;
        }
        setPosition(b2body.getPosition().x - getWidth() / 2,
                b2body.getPosition().y - 8 / GdxErb.PPM);
        if (currentState == State.DEAD) {
            deadRotationDegrees += 3;
            rotate(deadRotationDegrees);
            if (stateTime > 5 && !isDestroyed) {
                world.destroyBody(b2body);
                isDestroyed = true;
            }
        }
        else b2body.setLinearVelocity(velocity);
    }

    @Override
    public void hitOnHead(Knight knight) {
        if (currentState != State.STANDING_SHELL) {
            currentState = State.STANDING_SHELL;
            velocity.x = 0;
        } else {
            kick(knight.getX() <= this.getX() ? KICK_RIGHT_SPEED :KICK_LEFT_SPEED);
        }
    }

    @Override
    public void onEnimalyHit(Enimaly enimaly) {
        if (enimaly instanceof Turtle) {
            if (((Turtle)enimaly).currentState == State.MOVING_SHELL
                    && currentState != State.MOVING_SHELL)
                getKilled();
            else if (currentState == State.MOVING_SHELL &&
                    ((Turtle) enimaly).currentState == State.WALKING)
                return;
            else reverseVelocity(true, false);
        } else if (currentState != State.MOVING_SHELL)
            reverseVelocity(true, false);
    }

    /*
    public void draw(Batch batch) {
        if (!isDestroyed)
            super.draw(batch);
    }
    */

    public void kick(int speed) {
        velocity.x = speed;
        currentState = State.MOVING_SHELL;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void getKilled() {
        currentState = State.DEAD;
        Filter filter = new Filter();
        filter.maskBits = GdxErb.NOTHING_BIT;
        for (Fixture fixture : b2body.getFixtureList())
            fixture.setFilterData(filter);
        b2body.applyLinearImpulse(new Vector2(0, 5f), b2body.getWorldCenter(), true);
    }
}
