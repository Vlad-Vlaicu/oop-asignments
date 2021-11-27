package dao;

import database.Database;
import entities.Actor;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class ActorDao implements Dao<Actor> {

    private Database database;


    public ActorDao(final Database database) {
        this.database = database;
    }

    @Override
    public Optional<Actor> get(final int id) {
        List<Actor> list = database.selectActors();
        Actor actor = list.get(id);
        return Optional.ofNullable(actor);
    }

    @Override
    public List<Actor> getAll() {
        return database.selectActors();
    }

    @Override
    public void save(final Actor actor) {
        AtomicInteger idHolder = database.getIdActorHolder();
        int id = idHolder.getAndIncrement();
        actor.setId(id);
        List<Actor> list = getAll();
        list.add(actor);
    }

    @Override
    public void delete(final int id) {
        List<Actor> list = getAll();
        list.remove(id);
    }
}
