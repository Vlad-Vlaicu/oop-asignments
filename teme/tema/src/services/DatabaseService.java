package services;

import database.Database;
import fileio.*;

import java.util.List;

public class DatabaseService {
    private Database database;
    private ActorService actorService;
    private MovieService movieService;
    private ShowService showService;
    private UserService userService;

    public DatabaseService(){
        database = Database.getInstance();
        actorService = new ActorService(database);
        movieService = new MovieService(database);
        showService = new ShowService(database);
        userService = new UserService(database);
    }

    public void populateDatabase(Input input){
        List<ActorInputData> actorsData = input.getActors();
        List<UserInputData> usersData = input.getUsers();
        List<MovieInputData> moviesData = input.getMovies();
        List<SerialInputData> serialsData = input.getSerials();

        for(ActorInputData actorData : actorsData){
            actorService.createActor(actorData);
        }

        for(MovieInputData movieData : moviesData){
            movieService.createMovie(movieData);
        }

        for(SerialInputData serialData : serialsData){
            showService.createShow(serialData);
        }

        for(UserInputData userData : usersData){
            userService.createUser(userData);
        }
    }

    public void resetDatabase(){
        database.dropDatabase();
    }
}
