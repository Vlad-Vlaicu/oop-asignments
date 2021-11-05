package dao;

import database.Database;
import entities.Show;

import java.util.List;
import java.util.Optional;

public class ShowDao implements Dao<Show>{

    private Database database;

    public ShowDao(Database database){
        this.database = database;
    }

    @Override
    public Optional<Show> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<Show> getAll() {
        return null;
    }

    @Override
    public void save(Show show) {

    }

    @Override
    public void update(Show oldObject, Show newObject) {

    }

    @Override
    public void delete(Show show) {

    }
}
