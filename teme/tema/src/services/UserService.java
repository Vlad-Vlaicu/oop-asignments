package services;

import common.Constants;
import dao.UserDao;
import database.Database;
import entertainment.Subscription;
import entities.User;
import fileio.UserInputData;
import utils.Utils;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public UserService(Database database){
        userDao = new UserDao(database);
    }

    public boolean createUser(UserInputData data){
        User user = new User();
        String subscriptionType = data.getSubscriptionType();
        Subscription subscription = Utils.stringToSubscription(subscriptionType);

        user.setSubscription(subscription);
        user.setUsername(data.getUsername());
        user.setHistory(data.getHistory());
        user.setFavouriteList(data.getFavoriteMovies());

        return createUser(user);

    }

    public boolean createUser(User user){
        if(!checkIfUserExists(user)){
            userDao.save(user);
            return true;
        }
        return false;
    }

    public int findUser(User user){
        List<User> list = getAllUsers();
        for(User u : list){
            if(u.equals(user)){
                return u.getId();
            }
        }
        return Constants.NOT_FOUND;
    }

    public boolean checkIfUserExists(User user){
        int id = findUser(user);
        return id != Constants.NOT_FOUND;
    }

    public boolean removeUser(User user){
        int id = findUser(user);
        if(id == Constants.NOT_FOUND){
            return false;
        }
        userDao.delete(id);
        return true;
    }

    public List<User> getAllUsers(){
        return userDao.getAll();
    }
}
