package actions.recommendations;

import actions.Action;
import entertainment.Rating;
import entertainment.Subscription;
import entities.Movie;
import entities.Show;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;
import utils.SortingUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class FavoriteRecommendation extends Action {
    private String username;

    public FavoriteRecommendation(final int actionId, final String actionType) {
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
        if (user.getSubscription() == Subscription.BASIC) {
            return "FavoriteRecommendation cannot be applied!";
        }

        Map<String, Integer> history = user.getHistory();

        HashMap<String, Integer> map = new HashMap<>();
        userService.getAllUsers().stream()
                .flatMap(s -> s.getFavouriteList().stream())
                .forEach(s -> {
                    if (map.containsKey(s)) {
                        int value = map.get(s);
                        value++;
                        map.replace(s, value);
                    } else {
                        map.put(s, 1);
                    }
                });

        List<Rating> result = map.entrySet().stream()
                .map(s -> new Rating(s.getKey(), s.getValue()))
                .collect(Collectors.toList());

        for (Rating r : result) {
            Optional<Movie> movieBox = Optional.ofNullable(movieService.
                    findMovieByTitle(r.getName()));
            Optional<Show> showBox = Optional.ofNullable(showService.getShowByName(r.getName()));
            movieBox.ifPresent(movie -> r.setSecondScore(movie.getId()));
            showBox.ifPresent(show -> r.setSecondScore(show.getId()));
        }

        SortingUtils.videoFavRecomm(result);

        for (Rating r : result) {
            if (!history.containsKey(r.getName())) {
                return "FavoriteRecommendation result: " + r.getName();
            }
        }
        return "FavoriteRecommendation cannot be applied!";
    }
}
