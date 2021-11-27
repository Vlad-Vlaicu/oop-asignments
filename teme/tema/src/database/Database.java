package database;

import entities.Actor;
import entities.Movie;
import entities.Show;
import entities.User;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public final class Database {

    private final List<Actor> actors;
    private final List<Movie> movies;
    private final List<Show> shows;
    private final List<User> users;
    private final AtomicInteger idActorHolder;
    private final AtomicInteger idVideoHolder;
    private final AtomicInteger idUserHolder;

    private Database() {
        actors = new LinkedList<>();
        movies = new LinkedList<>();
        shows = new LinkedList<>();
        users = new LinkedList<>();
        idActorHolder = new AtomicInteger(1);
        idVideoHolder = new AtomicInteger(1);
        idUserHolder = new AtomicInteger(1);
    }

    private static final class INSTANCEHOLDER {
        private static final Database INSTANCE = new Database();
    }

    public static Database getInstance() {
        return INSTANCEHOLDER.INSTANCE;
    }

    /** method returns all the objects of class Actor from the database as a list
     * @return the a list of objects Actor
     * */
    public List<Actor> selectActors() {
        return actors;
    }

    /** method returns all the objects of class Movie from the database as a list
     * @return the a list of objects Movie
     * */
    public List<Movie> selectMovies() {
        return movies;
    }

    /** method returns all the objects of class Show from the database as a list
     * @return the a list of objects Show
     * */
    public List<Show> selectShows() {
        return shows;
    }

    /** method returns all the objects of class User from the database as a list
     * @return the a list of objects User
     * */
    public List<User> selectUsers() {
        return users;
    }

    /** method returns the id holder for table Actor
     * @return an AtomicInteger that represents the current unused id
     * */
    public AtomicInteger getIdActorHolder() {
        return idActorHolder;
    }

    /** method returns the id holder for table Video
     * @return an AtomicInteger that represents the current unused id
     * */
    public AtomicInteger getIdVideoHolder() {
        return idVideoHolder;
    }

    /** method returns the id holder for table User
     * @return an AtomicInteger that represents the current unused id
     * */
    public AtomicInteger getIdUserHolder() {
        return idUserHolder;
    }

    /** method resets the Actor table
     * */
    public void dropActors() {
        actors.clear();
    }

    /** method resets the Movie table
     * */
    public void dropMovies() {
        movies.clear();
    }

    /** method resets the Show table
     * */
    public void dropShows() {
        shows.clear();
    }

    /** method resets the User table
     * */
    public void dropUsers() {
        users.clear();
    }

    /** method resets the database
     * */
    public void dropDatabase() {
        dropUsers();
        dropActors();
        dropMovies();
        dropShows();
        idUserHolder.set(1);
        idActorHolder.set(1);
        idVideoHolder.set(1);
    }
}
