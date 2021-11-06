package dao;

import database.Database;
import entities.Actor;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ActorDao implements Dao<Actor>{

    private Database database;


    public ActorDao(Database database){
        this.database = database;
    }

    @Override
    public Optional<Actor> get(int id) {
        List<Actor> list = database.selectActors();
        Actor actor = list.get(id);
        return Optional.ofNullable(actor);
    }

    @Override
    public List<Actor> getAll() {
        return database.selectActors();
    }

    @Override
    public void save(Actor actor) {
        AtomicInteger idHolder = database.getIdActorHolder();
        int id = idHolder.getAndIncrement();
        actor.setId(id);
        List<Actor> list = getAll();
        list.add(actor);
    }

    @Override
    public void delete(int id) {
        List<Actor> list = getAll();
        list.remove(id);
    }
}
