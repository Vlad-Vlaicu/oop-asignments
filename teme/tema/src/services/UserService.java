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

    public UserService(){
        Database database = Database.getInstance();
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

    public User findUserByName(String name){
        List<User> list = getAllUsers();
        for(User u : list){
            String username = u.getUsername();
            if(username.equals(name)){
                return u;
            }
        }
        return null;

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

    public boolean addMovieToFavorite(String title, User user){
        List<String> favoriteMovies = user.getFavouriteList();
        if(favoriteMovies.contains(title)){
            return false;
        }
        favoriteMovies.add(title);
        return true;
    }

    public boolean wasVideoSeen(String title, User user){
        var history = user.getHistory();
        return history.containsKey(title);
    }

    public String watchMovie(String title, User user){
        var history = user.getHistory();
        int views;
        if(history.containsKey(title)){
            views = history.get(title);
            views++;
            history.replace(title,views);
        }else{
            views = 1;
            history.put(title,views);
        }
        return "success -> " + title + " was viewed with total views of " + String.valueOf(views);
    }

    public boolean isMovieRatedByUser(String title, User user){
        var ratings = user.getRatings();
        return ratings.containsKey(title);
    }

    public void rateMovie(String title, double grade, User user){
        var ratings = user.getRatings();
        ratings.put(title,grade);
    }

    public boolean isShowRatedByUser(String title, int currentSeason, User user){
        String key = title + currentSeason;
        var ratings = user.getRatings();
        return ratings.containsKey(key);

    }

    public void rateShow(String title, int season, double grade, User user){
        String key = title + season;
        var ratings = user.getRatings();
        ratings.put(key, grade);

    };


    public List<User> getAllUsers(){
        return userDao.getAll();
    }
}
