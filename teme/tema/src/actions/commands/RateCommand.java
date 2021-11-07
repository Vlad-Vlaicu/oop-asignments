package actions.commands;

import actions.Action;
import entities.Movie;
import entities.Show;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;

import java.util.Optional;

public class RateCommand extends Action {
    private String user;
    private String title;
    private double grade;
    private int currentSeason;

    public RateCommand(int action_id, String action_type) {
        super(action_id, action_type);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }

    @Override
    public String execute() {
        if(currentSeason == 0){
            return rateMovie();
        }
        return rateShow();
    }

    private String rateMovie(){
        UserService userService = new UserService();
        MovieService movieService = new MovieService();

        User user = userService.findUserByName(this.user);
        Movie movie = movieService.findMovieByTitle(this.title);

        Optional<User> userBox = Optional.ofNullable(user);
        Optional<Movie> movieBox = Optional.ofNullable(movie);

        if(userBox.isEmpty()){
            return "error -> user not found";
        }

        if(movieBox.isEmpty()){
            return "error -> movie not found";
        }

        boolean isRated = userService.isMovieRatedByUser(this.title,user);
        if(isRated){
            return "error -> movie already rated by user " + user.getUsername();
        }
        userService.rateMovie(this.title, this.grade, user);
        movieService.rateMovie(this.grade, movie);
        return "success -> " + this.title + " was rated with " + this.grade + " by " + this.user;

    }

    private String rateShow(){
        UserService userService = new UserService();
        ShowService showService = new ShowService();

        User user = userService.findUserByName(this.user);
        Show show = showService.findShowByTitle(this.title);

        Optional<User> userBox = Optional.ofNullable(user);
        Optional<Show> showBox = Optional.ofNullable(show);

        if(userBox.isEmpty()){
            return "error -> user not found";
        }

        if(showBox.isEmpty()){
            return "error -> show not found";
        }

        boolean isRated = userService.isShowRatedByUser(this.title, this.currentSeason, user);
        if(isRated){
            return "error -> season of the show already rated by user " + user.getUsername();
        }
        userService.rateShow(this.title, this.currentSeason, this.grade, user);
        showService.rateShow(this.grade, this.currentSeason, show);
        return "success -> " + this.title + " was rated with " + this.grade + " by " + this.user;
    }
}
