package actions.recommendations;

import actions.Action;
import entertainment.Genre;
import entertainment.Rating;
import entertainment.Subscription;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;
import utils.SortingUtils;
import utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public final class SearchRecommendation extends Action {
    private String username;
    private Genre genre;

    public SearchRecommendation(final int actionId, final String actionType) {
        super(actionId, actionType);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    @Override
    public String execute() {
        MovieService movieService = new MovieService();
        ShowService showService = new ShowService();
        UserService userService = new UserService();
        User user = userService.findUserByName(username);
        if (user.getSubscription().equals(Subscription.BASIC)) {
            return "SearchRecommendation cannot be applied!";
        }
        List<Rating> ratings = new ArrayList<>();

        showService.getAllShows().stream()
                .filter(s -> s.getGenres().contains(genre))
                .map(s -> new Rating(s.getName(), showService.getRating(s)))
                .forEach(ratings::add);

        movieService.getAllMovies().stream()
                .filter(s -> s.getGenres().contains(genre))
                .map(s -> new Rating(s.getName(), movieService.getRating(s)))
                .forEach(ratings::add);

        SortingUtils.videoSearch(ratings);
        Map<String, Integer> history = user.getHistory();
        List<String> result = new ArrayList<>();
        for (Rating r : ratings) {
            if (!history.containsKey(r.getName())) {
                result.add(r.getName());
            }
        }
        if (result.size() != 0) {
            return Utils.createSearchRecommResult(result);
        }


        return "SearchRecommendation cannot be applied!";
    }
}
