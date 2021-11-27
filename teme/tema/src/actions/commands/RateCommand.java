package actions.commands;

import actions.Action;
import entities.Movie;
import entities.Show;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;

import java.util.Optional;

public final class RateCommand extends Action {
    private String user;
    private String title;
    private double grade;
    private int currentSeason;

    public RateCommand(final int actionId, final String actionType) {
        super(actionId, actionType);
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(final double grade) {
        this.grade = grade;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(final int currentSeason) {
        this.currentSeason = currentSeason;
    }

    @Override
    public String execute() {
        UserService userService = new UserService();
        User user = userService.findUserByName(this.user);
        Optional<User> userBox = Optional.ofNullable(user);
        if (userBox.isEmpty()) {
            return "error -> user not found";
        }
        if (currentSeason == 0) {
            return rateMovie(user);
        }
        return rateShow(user);
    }

    private String rateMovie(final User user) {
        UserService userService = new UserService();
        MovieService movieService = new MovieService();

        Movie movie = movieService.findMovieByTitle(this.title);
        Optional<Movie> movieBox = Optional.ofNullable(movie);

        if (movieBox.isEmpty()) {
            return "error -> movie not found";
        }
        boolean isMovieSeen = userService.wasVideoSeen(title, user);
        if (!isMovieSeen) {
            return "error -> " + title + " is not seen";
        }

        boolean isRated = userService.isMovieRatedByUser(this.title, user);
        if (isRated) {
            return "error -> " + title + " has been already rated";
        }
        userService.rateMovie(this.title, this.grade, user);
        movieService.rateMovie(this.grade, movie);
        return "success -> " + this.title + " was rated with " + this.grade + " by " + this.user;

    }

    private String rateShow(final User user) {
        UserService userService = new UserService();
        ShowService showService = new ShowService();

        Show show = showService.findShowByTitle(this.title);
        Optional<Show> showBox = Optional.ofNullable(show);

        if (showBox.isEmpty()) {
            return "error -> show not found";
        }

        boolean isShowSeen = userService.wasVideoSeen(title, user);
        if (!isShowSeen) {
            return "error -> " + title + " is not seen";
        }

        boolean isRated = userService.isShowRatedByUser(this.title, this.currentSeason, user);
        if (isRated) {
            return "error -> " + title + " has been already rated";
        }
        userService.rateShow(this.title, this.currentSeason, this.grade, user);
        showService.rateShow(this.grade, this.currentSeason, show);
        return "success -> " + this.title + " was rated with " + this.grade + " by " + this.user;
    }
}
