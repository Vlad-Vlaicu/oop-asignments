package dao;

import database.Database;
import entities.Movie;

import java.util.List;
import java.util.Optional;

public class MovieDao implements Dao<Movie>{

    private Database database;
    public MovieDao(Database database){
        this.database = database;
    }

    @Override
    public Optional<Movie> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Movie> getAll() {
        return null;
    }

    @Override
    public void save(Movie movie) {

    }

    @Override
    public void update(Movie oldObject, Movie newObject) {

    }

    @Override
    public void delete(Movie movie) {

    }
}
