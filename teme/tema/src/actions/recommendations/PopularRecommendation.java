package actions.recommendations;

import actions.Action;
import entertainment.Genre;
import entertainment.Rating;
import entertainment.Subscription;
import entities.Movie;
import entities.Show;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;
import utils.Utils;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class PopularRecommendation extends Action {
    private String username;

    public PopularRecommendation(final int actionId, final String actionType) {
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
            return "PopularRecommendation cannot be applied!";
        }

        Map<String, Integer> history = user.getHistory();

        List<Map.Entry> entries = userService.getAllUsers().stream()
                .flatMap(s -> s.getHistory().entrySet().parallelStream())
                .collect(Collectors.toList());

        Map<Genre, Integer> popularGenres = new HashMap<>();

        for (Map.Entry e : entries) {
            Optional<Movie> movieBox = Optional.ofNullable(movieService
                        .findMovieByTitle((String) e.getKey()));
            Optional<Show> showBox = Optional.ofNullable(showService
                        .findShowByTitle((String) e.getKey()));

            if (showBox.isPresent()) {
                Show s = showBox.get();
                s.getGenres().forEach(f -> {
                    if (popularGenres.containsKey(f)) {
                        int value = popularGenres.get(f);
                        value++;
                        popularGenres.replace(f, value);
                    } else {
                        popularGenres.put(f, 1);
                    }
                });
            }

            if (movieBox.isPresent()) {
                Movie m = movieBox.get();
                m.getGenres().forEach(s -> {
                    if (popularGenres.containsKey(s)) {
                        int value = popularGenres.get(s);
                        value++;
                        popularGenres.replace(s, value);
                    } else {
                        popularGenres.put(s, 1);
                    }
                });
            }
        }

        List<Rating> genreList = new ArrayList<>();
        popularGenres.entrySet().stream()
                .map(s -> new Rating(s.getKey().toString(), s.getValue()))
                .sorted(Comparator.comparingDouble(Rating::getScore).reversed())
                .forEach(genreList::add);


        for (Rating g : genreList) {
            Genre videoGenre = Utils.stringToGenre(g.getName());

            List<Rating> videos = new ArrayList<>();
            showService.getAllShows().stream()
                    .filter(s -> s.getGenres().contains(videoGenre))
                    .map(s -> new Rating(s.getName(), s.getId()))
                    .forEach(videos::add);

            movieService.getAllMovies().stream()
                    .filter(s -> s.getGenres().contains(videoGenre))
                    .map(s -> new Rating(s.getName(), s.getId()))
                    .forEach(videos::add);

            videos.sort(Comparator.comparingDouble(Rating::getScore));
            for (Rating video : videos) {
                if (!history.containsKey(video.getName())) {
                    return "PopularRecommendation result: " + video.getName();
                }
            }

        }


        return "PopularRecommendation cannot be applied!";

    }
}
