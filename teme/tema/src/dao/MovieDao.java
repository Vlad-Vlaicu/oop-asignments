package dao;

import database.Database;
import entities.Movie;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class MovieDao implements Dao<Movie>{

    private Database database;
    public MovieDao(Database database){
        this.database = database;
    }

    @Override
    public Optional<Movie> get(int id) {
        List<Movie> list = database.selectMovies();
        Movie movie = list.get(id);
        return Optional.ofNullable(movie);
    }

    @Override
    public List<Movie> getAll() {
        return database.selectMovies();
    }

    @Override
    public void save(Movie movie) {
        AtomicInteger idHolder = database.getIdMovieHolder();
        int id = idHolder.getAndIncrement();
        movie.setId(id);
        List<Movie> list = getAll();
        list.add(movie);
    }

    @Override
    public void delete(int id) {
        List<Movie> list = getAll();
        list.remove(id);
    }
}
