package dao;

import database.Database;
import entities.Show;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class ShowDao implements Dao<Show> {

    private Database database;

    public ShowDao(final Database database) {
        this.database = database;
    }

    @Override
    public Optional<Show> get(final int id) {
        List<Show> list = database.selectShows();
        Show show = list.get(id);
        return Optional.ofNullable(show);
    }

    @Override
    public List<Show> getAll() {
        return database.selectShows();
    }

    @Override
    public void save(final Show show) {
        AtomicInteger idHolder = database.getIdVideoHolder();
        int id = idHolder.getAndIncrement();
        show.setId(id);
        List<Show> list = getAll();
        list.add(show);
    }

    @Override
    public void delete(final int id) {
        List<Show> list = getAll();
        list.remove(id);
    }
}
