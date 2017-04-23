package com.tent.assist.erb1.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Sprites.Enimalies.Enimaly;
import com.tent.assist.erb1.Sprites.Items.Item;
import com.tent.assist.erb1.Sprites.Knight;
import com.tent.assist.erb1.Sprites.People.Person;
import com.tent.assist.erb1.Sprites.TileObjects.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case GdxErb.MARIO_HEAD_BIT | GdxErb.BRICK_BIT:
            case GdxErb.MARIO_HEAD_BIT | GdxErb.COIN_BIT:
                if (fixA.getFilterData().categoryBits == GdxErb.MARIO_HEAD_BIT)
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Knight)fixA.getUserData());
                else ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Knight)fixB.getUserData());
                break;
            case GdxErb.ENIMALY_HEAD_BIT | GdxErb.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == GdxErb.ENIMALY_HEAD_BIT)
                    ((Enimaly)fixA.getUserData()).hitOnHead((Knight)fixB.getUserData());
                else ((Enimaly)fixB.getUserData()).hitOnHead((Knight)fixA.getUserData());
                break;
            case GdxErb.ENIMALY_BIT | GdxErb.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == GdxErb.ENIMALY_BIT)
                    ((Enimaly)fixA.getUserData()).reverseVelocity(true, false);
                else ((Enimaly)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case GdxErb.MARIO_BIT | GdxErb.ENIMALY_BIT:
                if (fixA.getFilterData().categoryBits == GdxErb.MARIO_BIT)
                    ((Knight)fixA.getUserData()).hit((Enimaly)fixB.getUserData());
                else ((Knight)fixB.getUserData()).hit((Enimaly)fixA.getUserData());
                break;
            case GdxErb.ENIMALY_BIT | GdxErb.ENIMALY_BIT:
                ((Enimaly)fixA.getUserData()).onEnimalyHit((Enimaly)fixB.getUserData());
                ((Enimaly)fixB.getUserData()).onEnimalyHit((Enimaly)fixA.getUserData());
                break;
            case GdxErb.ITEM_BIT | GdxErb.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == GdxErb.ITEM_BIT)
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                else ((Item)fixB.getUserData()).reverseVelocity(true, false);
                break;
            case GdxErb.ITEM_BIT | GdxErb.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == GdxErb.ITEM_BIT)
                    ((Item)fixA.getUserData()).use((Knight)fixB.getUserData());
                else ((Item)fixB.getUserData()).use((Knight)fixA.getUserData());
                break;
            case GdxErb.PERSON_BIT | GdxErb.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == GdxErb.PERSON_BIT)
                    ((Person)fixA.getUserData()).hitOnHead((Knight)fixB.getUserData());
                else ((Person)fixB.getUserData()).hitOnHead((Knight)fixA.getUserData());
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
