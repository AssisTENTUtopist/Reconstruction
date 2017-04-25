package com.tent.assist.erb1.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.tent.assist.erb1.GdxErb;
import com.tent.assist.erb1.Screens.PlayScreen;
import com.tent.assist.erb1.Sprites.Enimalies.Enimaly;
import com.tent.assist.erb1.Sprites.Enimalies.Turtle;
import com.tent.assist.erb1.Sprites.People.Naomi;
import com.tent.assist.erb1.Sprites.People.Person;
import com.tent.assist.erb1.Sprites.TileObjects.Brick;
import com.tent.assist.erb1.Sprites.TileObjects.Coin;
import com.tent.assist.erb1.Sprites.Enimalies.Kentauros;


public class B2WorldCreator {
    private Array<Kentauros> kentauroses;
    private Array<Turtle> turtles;
    private Naomi naomi;

    public B2WorldCreator(PlayScreen screen){
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GdxErb.PPM, (rect.getY() + rect.getHeight() / 2) / GdxErb.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GdxErb.PPM, rect.getHeight() / 2 / GdxErb.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }

        //create pipe bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GdxErb.PPM, (rect.getY() + rect.getHeight() / 2) / GdxErb.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GdxErb.PPM, rect.getHeight() / 2 / GdxErb.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = GdxErb.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){
            new Brick(screen, object);
            /*
            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / GdxErb.PPM, (rect.getY() + rect.getHeight() / 2) / GdxErb.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / GdxErb.PPM, rect.getHeight() / 2 / GdxErb.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
            */
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            new Coin(screen, object);
        }

        //create kentauroses
        kentauroses = new Array<Kentauros>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            kentauroses.add(new Kentauros(screen, rect.getX() / GdxErb.PPM, rect.getY() / GdxErb.PPM));
        }

        turtles = new Array<Turtle>();
        for(MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            turtles.add(new Turtle(screen, rect.getX() / GdxErb.PPM, rect.getY() / GdxErb.PPM));
        }

        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            naomi = new Naomi(screen, rect.getX() / GdxErb.PPM, rect.getY() / GdxErb.PPM);
        }
    }

    /*
    public void removeTurtle(Turtle turtle) {
        turtles.removeValue(turtle, true);
    }
    */

    public Array<Enimaly> getEnimalies() {
        Array<Enimaly> enimalies = new Array<Enimaly>();
        enimalies.addAll(kentauroses);
        enimalies.addAll(turtles);
        return enimalies;
    }

    public Array<Person> getPeople() {
        Array<Person> people = new Array<Person>();
        people.add(naomi);
        return people;
    }
}
