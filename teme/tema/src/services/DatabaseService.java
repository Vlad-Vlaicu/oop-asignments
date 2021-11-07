package services;

import database.Database;
import fileio.*;

import java.util.List;

public class DatabaseService {
    private final Database database;
    private final ActorService actorService;
    private final MovieService movieService;
    private final ShowService showService;
    private final UserService userService;

    public DatabaseService(){
        database = Database.getInstance();
        actorService = new ActorService();
        movieService = new MovieService();
        showService = new ShowService();
        userService = new UserService();
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
