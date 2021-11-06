package dao;

import database.Database;
import entities.User;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDao implements Dao<User> {

    private Database database;

    public UserDao(Database database){
        this.database = database;
    }

    @Override
    public Optional<User> get(int id) {
        List<User> list = database.selectUsers();
        User user = list.get(id);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        return database.selectUsers();
    }

    @Override
    public void save(User user) {
        AtomicInteger idHolder = database.getIdUserHolder();
        int id = idHolder.getAndIncrement();
        user.setId(id);
        List<User> list = getAll();
        list.add(user);
    }

    @Override
    public void delete(int id) {
        List<User> list = getAll();
        list.remove(id);
    }
}
