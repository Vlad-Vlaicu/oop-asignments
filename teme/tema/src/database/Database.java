package database;

import entities.Actor;
import entities.Movie;
import entities.Show;
import entities.User;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Database {

    private List<Actor> actors;
    private List<Movie> movies;
    private List<Show> shows;
    private List<User> users;
    private AtomicInteger idActorHolder;
    private AtomicInteger idMovieHolder;
    private AtomicInteger idShowHolder;
    private AtomicInteger idUserHolder;

    private Database(){
        actors = new LinkedList<>();
        movies = new LinkedList<>();
        shows = new LinkedList<>();
        users = new LinkedList<>();
        idActorHolder = new AtomicInteger(1);
        idMovieHolder = new AtomicInteger(1);
        idShowHolder = new AtomicInteger(1);
        idUserHolder = new AtomicInteger(1);
    };

    private static final class INSTANCE_HOLDER{
        private static final Database INSTANCE = new Database();
    }

    public static Database getInstance(){
        return INSTANCE_HOLDER.INSTANCE;
    }

    public List<Actor> selectActors() {
        return actors;
    }

    public List<Movie> selectMovies() {
        return movies;
    }

    public List<Show> selectShows() {
        return shows;
    }

    public List<User> selectUsers() {
        return users;
    }

    public AtomicInteger getIdActorHolder() {
        return idActorHolder;
    }

    public AtomicInteger getIdMovieHolder() {
        return idMovieHolder;
    }

    public AtomicInteger getIdShowHolder() {
        return idShowHolder;
    }

    public AtomicInteger getIdUserHolder() {
        return idUserHolder;
    }

    public void dropActors(){
        actors.clear();
    }

    public void dropMovies(){
        movies.clear();
    }

    public void dropShows(){
        shows.clear();
    }

    public void dropUsers(){
        users.clear();
    }

    public void dropDatabase(){
        dropUsers();
        dropActors();
        dropMovies();
        dropShows();
        idUserHolder.set(1);
        idActorHolder.set(1);
        idShowHolder.set(1);
        idMovieHolder.set(1);
    }
}
