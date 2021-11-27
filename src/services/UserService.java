package services;

import common.Constants;
import dao.UserDao;
import database.Database;
import entertainment.Subscription;
import entities.User;
import fileio.UserInputData;
import utils.Utils;
import java.util.List;

public final class UserService {
    private final UserDao userDao;

    public UserService() {
        Database database = Database.getInstance();
        userDao = new UserDao(database);
    }

    /** method creates a User object and attempts to insert it into the database
     * @param data is raw data used to create a User object
     **/
    public void createUser(final UserInputData data) {
        User user = new User();
        String subscriptionType = data.getSubscriptionType();
        Subscription subscription = Utils.stringToSubscription(subscriptionType);

        user.setSubscription(subscription);
        user.setUsername(data.getUsername());
        user.setHistory(data.getHistory());
        user.setFavouriteList(data.getFavoriteMovies());

        createUser(user);

    }
    /** method atempts to insert a User object into the database
     * @param user is the object to be inserted into database */
    public void createUser(final User user) {
        if (!checkIfUserExists(user)) {
            userDao.save(user);
        }
    }
    /** method searches the database for a User instance given as parameter
     * @param user is the object searched into database
     * @return the id of the object if the object is found in the database,
     * or not_found constants in case of failure
     * */
    public int findUser(final User user) {
        List<User> list = getAllUsers();
        for (User u : list) {
            if (u.equals(user)) {
                return u.getId();
            }
        }
        return Constants.NOT_FOUND;
    }

    /** method searches the database for a User instance having its name
     * given as parameter
     * @param name is the String used to search in the database
     * @return an instance of class User if the object was found and a null
     * reference otherwise
     * */
    public User findUserByName(final String name) {
        List<User> list = getAllUsers();
        for (User u : list) {
            String username = u.getUsername();
            if (username.equals(name)) {
                return u;
            }
        }
        return null;

    }

    /** method checks if the object given as parameter is persisted in the database
     * @param user is the object the method looks after in the database
     * @return true if the object is found in the database and false in case of failure
     * */
    public boolean checkIfUserExists(final User user) {
        int id = findUser(user);
        return id != Constants.NOT_FOUND;
    }

    /** method attempts to remove the object given as parameter from the database
     * @param user is the object to be removed
     * @return is true if the object was removed from the database and false otherwise
     * */
    public boolean removeUser(final User user) {
        int id = findUser(user);
        if (id == Constants.NOT_FOUND) {
            return false;
        }
        userDao.delete(id);
        return true;
    }

    /** method atempts to add a video in the user's favourite videos list
     * @param title is the title of the video to be added in the favourite list
     * @param user is the user whose favourite list of videos is updated with
     * the new video
     * @return true if the video was added in the favourite list successfully
     * and false otherwise
     * */
    public boolean addVideoToFavorite(final String title, final User user) {
        List<String> favoriteMovies = user.getFavouriteList();
        if (favoriteMovies.contains(title)) {
            return false;
        }
        favoriteMovies.add(title);
        return true;
    }

    /** method checks if the video was seen by the user
     * @param title is the title of the video
     * @param user is the user whose history is checked
     * @return true if the video was seen by the user and false
     * otherwise
     * */
    public boolean wasVideoSeen(final String title, final User user) {
        var history = user.getHistory();
        return history.containsKey(title);
    }

    /** method adds the video in the history of the user
     * @param title is the title of the movie to be watched
     * @param user is the user that watches the movie
     * @return a String that contains the title of the video and
     * the number of views made by the user
     * */
    public String watchVideo(final String title, final User user) {
        var history = user.getHistory();
        int views;
        if (history.containsKey(title)) {
            views = history.get(title);
            views++;
            history.replace(title, views);
        } else {
            views = 1;
            history.put(title, views);
        }
        return "success -> " + title + " was viewed with total views of " + views;
    }

    /** method checks if the movie was rated by the user
     * @param title is the title of the movie
     * @param user is the user that did the rating
     * @return true if the user already rated the movie and false otherwise
     * */
    public boolean isMovieRatedByUser(final String title, final User user) {
        var ratings = user.getRatings();
        return ratings.containsKey(title);
    }

    /** method rates the movie with the grade given by the user
     * @param user is the user that does the rating
     * @param title is the title of the rated movie
     * @param grade is a double that represents the grade given by the user
     * */
    public void rateMovie(final String title, final double grade, final User user) {
        var ratings = user.getRatings();
        ratings.put(title, grade);
    }

    /** method checks if the show is rated by the user
     * @param title is the title of the show
     * @param currentSeason is an integer that represents the season number
     * @param user is the user that did the rating
     * @return true if the season of the show was already rated by the user and
     * false otherise
     * */
    public boolean isShowRatedByUser(final String title, final int currentSeason, final User user) {
        String key = title + currentSeason;
        var ratings = user.getRatings();
        return ratings.containsKey(key);

    }

    /** method rates the season of the given show with the grade given by the user
     * @param user is the user that does the grading
     * @param title is the title of the show to be rated
     * @param season is an integer that represents the number of the season to be rated
     * @param grade is a double that represents the grade given by the user to the season
     * */
    public void rateShow(final String title, final int season,
                         final double grade, final User user) {
        String key = title + season;
        var ratings = user.getRatings();
        ratings.put(key, grade);
    }

    /** method returns a list of all users from the database
     * @return a list of instances of User class
     **/
    public List<User> getAllUsers() {
        return userDao.getAll();
    }
}
