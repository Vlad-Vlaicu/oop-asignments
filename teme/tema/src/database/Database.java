package database;

import entities.Actor;
import entities.Movie;
import entities.Show;
import entities.User;
import java.util.HashSet;
import java.util.Set;

public class Database {

    private Set<Actor> actors;
    private Set<Movie> movies;
    private Set<Show> shows;
    private Set<User> users;

    private Database(){
        actors = new HashSet<>();
        movies = new HashSet<>();
        shows = new HashSet<>();
        users = new HashSet<>();
    };

    private static final class INSTANCE_HOLDER{
        private static final Database INSTANCE = new Database();
    }

    public Database getInstance(){
        return INSTANCE_HOLDER.INSTANCE;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public Set<Show> getShows() {
        return shows;
    }

    public Set<User> getUsers() {
        return users;
    }
}
