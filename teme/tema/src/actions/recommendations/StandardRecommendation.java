package actions.recommendations;

import actions.Action;
import entertainment.Rating;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class StandardRecommendation extends Action {
    private String username;

    public StandardRecommendation(final int actionId, final String actionType) {
        super(actionId, actionType);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String execute() {
        MovieService movieService = new MovieService();
        ShowService showService = new ShowService();
        UserService userService = new UserService();
        User user = userService.findUserByName(username);
        Map<String, Integer> history = user.getHistory();
        List<Rating> videos = new ArrayList<>();
        movieService.getAllMovies().stream()
                .map(s -> new Rating(s.getName(), s.getId()))
                .forEach(videos::add);
        showService.getAllShows().stream()
                .map(s -> new Rating(s.getName(), s.getId()))
                .forEach(videos::add);

        videos.sort(Comparator.comparingDouble(Rating::getScore));

        for (Rating r : videos) {
            if (!history.containsKey(r.getName())) {
                return "StandardRecommendation result: " + r.getName();
            }
        }
        return "StandardRecommendation cannot be applied!";
    }
}
