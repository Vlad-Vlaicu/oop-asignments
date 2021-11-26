package actions.queries;

import common.Constants;
import entertainment.Genre;
import entertainment.Rating;
import entities.Movie;
import entities.User;
import services.MovieService;
import services.UserService;
import utils.SortingUtils;
import utils.Utils;
import java.util.Collection;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class MovieQuery extends Query {
    private int year;
    private Genre genre;

    public MovieQuery(final int actionId, final String actionType) {
        super(actionId, actionType);
    }

    public int getYear() {
        return year;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(final Genre genre) {
        this.genre = genre;
    }

    public void setGenre(final String genre) {
        this.genre = Utils.stringToGenre(genre);
    }

    @Override
    public String execute() {
        String criteria = this.getCriteria();
        int limit = this.getLimit();
        List<String> videos = switch (criteria) {
            case Constants.RATINGS -> getMoviesByRating();
            case Constants.FAVORITE -> getMoviesByFavorite();
            case Constants.LONGEST -> getMoviesByLength();
            case Constants.MOSTVIEWED -> getMoviesByViews();
            default -> new ArrayList<>();
        };

        videos = applyFilters(videos);

        if (limit != 0 && limit < videos.size()) {
            videos = videos.subList(0, limit);
        }
        return Utils.createQueryResult(videos);
    }

    private List<String> applyFilters(final List<String> videos) {
        MovieService movieService = new MovieService();
        List<String> result = new ArrayList<>();
        for (String v : videos) {
            Optional<Movie> movieBox = Optional.ofNullable(movieService.findMovieByTitle(v));
            if (movieBox.isPresent()) {
                Movie movie = movieBox.get();
                boolean isEligible = true;
                if (year != 0) {
                    if (movie.getYear() != year) {
                        isEligible = false;
                    }
                }
                if (genre != null) {
                    if (!movie.getGenres().contains(genre)) {
                        isEligible = false;
                    }
                }
                if (isEligible) {
                    result.add(movie.getName());
                }
            }
        }
        return result;
    }

    private List<String> getMoviesByViews() {
        UserService userService = new UserService();
        HashMap<String, Integer> map = new HashMap<>();
        userService.getAllUsers().stream()
                .map(User::getHistory)
                .flatMap(m -> m.entrySet().stream())
                .forEach(s -> {
                    if (map.containsKey(s.getKey())) {
                        int views = map.get(s.getKey());
                        views += s.getValue();
                        map.replace(s.getKey(), views);
                    } else {
                        map.put(s.getKey(), s.getValue());
                    }
                });

        List<Rating> movies = map.entrySet().stream()
                .map(s -> new Rating(s.getKey(), s.getValue()))
                .collect(Collectors.toList());

        SortingUtils.videoMostViews(movies, this.getSortType());

        return movies.stream().map(Rating::getName).collect(Collectors.toList());
    }

    private List<String> getMoviesByLength() {
        MovieService movieService = new MovieService();
        List<Rating> list = movieService.getAllMovies().stream()
                .map(s -> new Rating(s.getName(), s.getDuration()))
                .collect(Collectors.toList());

        SortingUtils.videoLongest(list, this.getSortType());

        return list.stream()
                .map(Rating::getName)
                .collect(Collectors.toList());
    }

    private List<String> getMoviesByFavorite() {
        UserService userService = new UserService();
        HashMap<String, Integer> map = new HashMap<>();
        userService.getAllUsers().stream()
                .map(User::getFavouriteList)
                .flatMap(Collection::stream)
                .forEach(s -> {
                    if (map.containsKey(s)) {
                        int views = map.get(s);
                        views++;
                        map.replace(s, views);
                    } else {
                        map.put(s, 1);
                    }
                });

        List<Rating> movies = map.entrySet().stream()
                .map(s -> new Rating(s.getKey(), s.getValue()))
                .collect(Collectors.toList());

        SortingUtils.videoFavorite(movies, this.getSortType());

        return movies.stream().map(Rating::getName).collect(Collectors.toList());
    }

    private List<String> getMoviesByRating() {
        MovieService movieService = new MovieService();
        List<Rating> list = movieService.getAllMovies().stream()
                .map(s -> new Rating(s.getName(), movieService.getRating(s)))
                .filter(s -> s.getScore() != 0)
                .collect(Collectors.toList());

        return list.stream()
                .sorted(Comparator.comparingDouble(Rating::getScore))
                .map(Rating::getName)
                .collect(Collectors.toList());
    }
}
