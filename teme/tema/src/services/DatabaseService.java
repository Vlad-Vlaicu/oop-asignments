package services;

import database.Database;
import fileio.ActorInputData;
import fileio.Input;
import fileio.MovieInputData;
import fileio.UserInputData;
import fileio.SerialInputData;
import java.util.List;


public final class DatabaseService {
    private final Database database;
    private final ActorService actorService;
    private final MovieService movieService;
    private final ShowService showService;
    private final UserService userService;

    public DatabaseService() {
        database = Database.getInstance();
        actorService = new ActorService();
        movieService = new MovieService();
        showService = new ShowService();
        userService = new UserService();
    }

    /** method populates the database with the given input
     * @param input is the data will be stored in the database
     * */
    public void populateDatabase(final Input input) {
        List<ActorInputData> actorsData = input.getActors();
        List<UserInputData> usersData = input.getUsers();
        List<MovieInputData> moviesData = input.getMovies();
        List<SerialInputData> serialsData = input.getSerials();

        for (ActorInputData actorData : actorsData) {
            actorService.createActor(actorData);
        }

        for (MovieInputData movieData : moviesData) {
            movieService.createMovie(movieData);
        }

        for (SerialInputData serialData : serialsData) {
            showService.createShow(serialData);
        }

        for (UserInputData userData : usersData) {
            userService.createUser(userData);
        }
    }

    /** method resets the database
     * */
    public void resetDatabase() {
        database.dropDatabase();
    }
}
