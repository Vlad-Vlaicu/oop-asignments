package dao;

import database.Database;
import entities.Movie;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public final class MovieDao implements Dao<Movie> {

    private Database database;

    public MovieDao(final Database database) {
        this.database = database;
    }

    @Override
    public Optional<Movie> get(final int id) {
        List<Movie> list = database.selectMovies();
        Movie movie = list.get(id);
        return Optional.ofNullable(movie);
    }

    @Override
    public List<Movie> getAll() {
        return database.selectMovies();
    }

    @Override
    public void save(final Movie movie) {
        AtomicInteger idHolder = database.getIdVideoHolder();
        int id = idHolder.getAndIncrement();
        movie.setId(id);
        List<Movie> list = getAll();
        list.add(movie);
    }

    @Override
    public void delete(final int id) {
        List<Movie> list = getAll();
        list.remove(id);
    }
}
