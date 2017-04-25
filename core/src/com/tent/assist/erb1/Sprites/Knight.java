package com.tent.assist.erb1.Sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Enimalies.Enimaly;
import com.tent.assist.erb1.Sprites.Enimalies.Turtle;
import com.tent.assist.erb1.Sprites.People.Person;

public class Knight extends Sprite {
    public enum State { FALLING, JUMPING, STANDING, RUNNING, GROWING, DEAD, HURT }
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion knightStand;
    private Animation knightRun;
    private Animation knightJump;
    private TextureRegion marioDead;
    //private TextureRegion marioJump;
    private TextureRegion bigMarioStand;
    private TextureRegion bigMarioJump;
    private Animation bigMarioRun;
    private Animation growMario;

    private float stateTimer;
    private boolean runningRight;
    private boolean marioIsBig;
    private boolean runGrowAnimation;
    private boolean isItTimeToDefineMario;
    private boolean isItTimeToRedefineMario;
    private boolean isItTimeToGetMotherfuckingHurt;
    private boolean isMarioDead;
    private boolean isSaved;

    public Knight(PlayScreen screen){
        this.world = screen.getWorld();
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++) {
                frames.add(new TextureRegion(screen.getAtlas().findRegion("runsprite"), i * 64, i * 128, 64, 128));
            }
        knightRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 2; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                frames.add(new TextureRegion(screen.getAtlas().findRegion("talksprite2"), i * 64, i * 64, 64, 64));
            }
        bigMarioRun = new Animation(0.1f, frames);
        frames.clear();

        for (int i = 0; i < 4; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("talksprite2"), 64, 0, 64, 64));
            frames.add(new TextureRegion(screen.getAtlas().findRegion("talksprite2"), 0, 0, 64, 64));
        }
        growMario = new Animation(0.2f, frames);
        frames.clear();

        for(int i = 1; i < 2; i++)
            frames.add(new TextureRegion(screen.getAtlas().findRegion("runsprite"), i * 64, 0, 64, 128));
        knightJump = new Animation(0.1f, frames);
        //marioJump = new TextureRegion(screen.getAtlas().findRegion("runsprite"), 64, 0, 64, 128);
        bigMarioJump = new TextureRegion(screen.getAtlas().findRegion("talksprite2"), 64, 0, 64, 64);

        knightStand = new TextureRegion(screen.getAtlas().findRegion("runsprite"), 0, 0, 64, 128);
        bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("talksprite2"), 0, 0, 64, 64);

        marioDead = new TextureRegion(screen.getAtlas().findRegion("wtf_is_this"), 0, 0, 64, 128);

        definePlayer();
        setBounds(0, 0, 64 / 2 / GdxErb.PPM, 128 / 2 / GdxErb.PPM);
        setRegion(knightStand);

    }

    public boolean isMarioBig() {
        return marioIsBig;
    }

    public void update(float dt){
        if (marioIsBig)
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 - 6 / GdxErb.PPM);
        else
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
        if (isItTimeToDefineMario)
            defineBigMario();
        if (isItTimeToRedefineMario)
            redefineMario();
    }

    public TextureRegion getFrame(float dt){
        currentState = getState();

        TextureRegion region;
        switch(currentState){
            case DEAD:
                region = marioDead;
                break;
            case GROWING:
                region = growMario.getKeyFrame(stateTimer);
                if (growMario.isAnimationFinished(stateTimer))
                    runGrowAnimation = false;
                break;
            case JUMPING:
                region = marioIsBig ? bigMarioJump : knightJump.getKeyFrame(stateTimer);
                //region = marioJump;
                break;
            case RUNNING:
                region = marioIsBig ? bigMarioRun.getKeyFrame(stateTimer, true) : knightRun.getKeyFrame(stateTimer, true);
                break;
            case HURT:
                if (getStateTimer() > 1.5f) isItTimeToGetMotherfuckingHurt = false;
            case FALLING:
            case STANDING:
            default:
                region = marioIsBig ? bigMarioStand : knightStand;
                break;
        }

        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    public State getState(){
        if (isMarioDead)
            return State.DEAD;
        else if (isItTimeToGetMotherfuckingHurt)
            return State.HURT;
        else if (runGrowAnimation)
            return State.GROWING;
        else if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
    }

    public void grow(){
        runGrowAnimation = true;
        marioIsBig = true;
        isItTimeToDefineMario = true;
        setBounds(getX(), getY(), getWidth(), getHeight() / 2);
        GdxErb.manager.get("audio/sounds/powerup.wav", Sound.class).play();
    }

    public boolean isMarioDead() {
        return isMarioDead;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public float getStateTimer() {
        return stateTimer;
    }

    public void jump(){
        if ( currentState != State.JUMPING ) {
            b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
            currentState = State.JUMPING;
        }
    }

    public void hit(Enimaly enimaly){
        if (enimaly instanceof Turtle &&
                ((Turtle)enimaly).getCurrentState() == Turtle.State.STANDING_SHELL) {
            ((Turtle)enimaly).kick(this.getX() <= enimaly.getX()
                    ? Turtle.KICK_RIGHT_SPEED : Turtle.KICK_LEFT_SPEED);
        } else {
            if (!isItTimeToGetMotherfuckingHurt) {
                if (marioIsBig) {
                    marioIsBig = false;
                    isItTimeToRedefineMario = true;
                    setBounds(getX(), getY(), getWidth(), getHeight() * 2);
                    GdxErb.manager.get("audio/sounds/powerdown.wav", Sound.class).play();
                } else {
                    GdxErb.manager.get("audio/music/glad.mp3", Music.class).stop();
                    GdxErb.manager.get("audio/sounds/mariodie.wav", Sound.class).play();
                    isMarioDead = true;
                    Filter filter = new Filter();
                    filter.maskBits = GdxErb.NOTHING_BIT;
                    for (Fixture fixture : b2body.getFixtureList())
                        fixture.setFilterData(filter);
                    b2body.applyLinearImpulse(new Vector2(0, 4f), b2body.getWorldCenter(), true);
                }
            }
        }
        isItTimeToGetMotherfuckingHurt = true;
    }

    public void hit(Person person) {
        isSaved = true;
    }

    public void redefineMario(){
        Vector2 position = b2body.getPosition();
        world.destroyBody(b2body);
        BodyDef bdef = new BodyDef();
        bdef.position.set(position);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape rektShape = new PolygonShape();
        rektShape.setAsBox(64 / 4 / GdxErb.PPM, 128 / 4 / GdxErb.PPM);
        CircleShape shape = new CircleShape();
        shape.setRadius(64 / 2 / GdxErb.PPM);
        fdef.filter.categoryBits = GdxErb.MARIO_BIT;
        fdef.filter.maskBits = GdxErb.GROUND_BIT | GdxErb.BRICK_BIT | GdxErb.COIN_BIT
                | GdxErb.ENIMALY_BIT | GdxErb.OBJECT_BIT | GdxErb.ENIMALY_HEAD_BIT
                | GdxErb.ITEM_BIT | GdxErb.PERSON_BIT;

        fdef.shape = rektShape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        //how to change direction?
        head.set(new Vector2(-2 / GdxErb.PPM, 6 / GdxErb.PPM), new Vector2(36 / GdxErb.PPM, 24 / GdxErb.PPM));
        fdef.filter.categoryBits = GdxErb.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);

        isItTimeToRedefineMario = false;
    }

    public void defineBigMario(){
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        BodyDef bdef = new BodyDef();
        bdef.position.set(currentPosition.add(0, 10 / GdxErb.PPM));
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape rektShape = new PolygonShape();
        rektShape.setAsBox(64 / 4 / GdxErb.PPM, 128 / 8 / GdxErb.PPM);
        CircleShape shape = new CircleShape();
        shape.setRadius(64 / 8 / GdxErb.PPM);
        fdef.filter.categoryBits = GdxErb.MARIO_BIT;
        fdef.filter.maskBits = GdxErb.GROUND_BIT | GdxErb.BRICK_BIT | GdxErb.COIN_BIT
                | GdxErb.ENIMALY_BIT | GdxErb.OBJECT_BIT | GdxErb.ENIMALY_HEAD_BIT
                | GdxErb.ITEM_BIT;

        fdef.shape = rektShape;
        b2body.createFixture(fdef).setUserData(this);
        shape.setPosition(new Vector2(0, -14 / GdxErb.PPM));
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        //how to change direction?
        head.set(new Vector2(-2 / GdxErb.PPM, 6 / GdxErb.PPM), new Vector2(36 / GdxErb.PPM, 24 / GdxErb.PPM));
        fdef.filter.categoryBits = GdxErb.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
        isItTimeToDefineMario = false;
    }

    public void definePlayer(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / GdxErb.PPM, 64 / GdxErb.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape rektShape = new PolygonShape();
        rektShape.setAsBox(64 / 4 / GdxErb.PPM, 128 / 4 / GdxErb.PPM);
        CircleShape shape = new CircleShape();
        shape.setRadius(64 / 2 / GdxErb.PPM);
        fdef.filter.categoryBits = GdxErb.MARIO_BIT;
        fdef.filter.maskBits = GdxErb.GROUND_BIT | GdxErb.BRICK_BIT | GdxErb.COIN_BIT
                | GdxErb.ENIMALY_BIT | GdxErb.OBJECT_BIT | GdxErb.ENIMALY_HEAD_BIT
                | GdxErb.ITEM_BIT;

        fdef.shape = rektShape;
        b2body.createFixture(fdef).setUserData(this);

        EdgeShape head = new EdgeShape();
        //how to change direction?
        head.set(new Vector2(-2 / GdxErb.PPM, 6 / GdxErb.PPM), new Vector2(36 / GdxErb.PPM, 24 / GdxErb.PPM));
        fdef.filter.categoryBits = GdxErb.MARIO_HEAD_BIT;
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData(this);
    }
}
