package dao;

import database.Database;
import entities.User;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {

    private Database database;

    public UserDao(Database database){
        this.database = database;
    }

    @Override
    public Optional<User> get(int id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User oldObject, User newObject) {

    }

    @Override
    public void delete(User user) {

    }
}
