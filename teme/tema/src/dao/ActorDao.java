package dao;

import database.Database;
import entities.Actor;

import java.util.List;
import java.util.Optional;

public class ActorDao implements Dao<Actor>{

    private Database database;

    public ActorDao(Database database){
        this.database = database;
    }

    @Override
    public Optional<Actor> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Actor> getAll() {
        return null;
    }

    @Override
    public void save(Actor actor) {

    }

    @Override
    public void update(Actor oldObject, Actor newObject) {

    }

    @Override
    public void delete(Actor actor) {

    }
}
