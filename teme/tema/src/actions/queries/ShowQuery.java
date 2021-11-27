package actions.queries;

import common.Constants;
import entertainment.Genre;
import entertainment.Rating;
import entities.Show;
import entities.User;
import services.ShowService;
import services.UserService;
import utils.SortingUtils;
import utils.Utils;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ShowQuery extends Query {
    private int year;
    private Genre genre;

    public ShowQuery(final int actionId, final String actionType) {
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
            case Constants.RATINGS -> getShowsByRating();
            case Constants.FAVORITE -> getShowsByFavorite();
            case Constants.LONGEST -> getShowsByLength();
            case Constants.MOSTVIEWED -> getShowsByViews();
            default -> new ArrayList<>();
        };

        videos = applyFilters(videos);

        if (limit != 0 && limit < videos.size()) {
            videos = videos.subList(0, limit - 1);
        }

        return Utils.createQueryResult(videos);
    }

    private List<String> applyFilters(final List<String> videos) {
        ShowService showService = new ShowService();
        List<String> result = new ArrayList<>();
        for (String v : videos) {
            Optional<Show> showBox = Optional.ofNullable(showService.getShowByName(v));
            if (showBox.isPresent()) {
                Show show = showBox.get();
                boolean isEligible = true;
                if (year != 0) {
                    if (show.getYear() != year) {
                        isEligible = false;
                    }
                }
                if (genre != null) {
                    if (!show.getGenres().contains(genre)) {
                        isEligible = false;
                    }
                }
                if (isEligible) {
                    result.add(show.getName());
                }
            }
        }
        return result;
    }

    private List<String> getShowsByViews() {
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

        List<Rating> shows = map.entrySet().stream()
                .map(s -> new Rating(s.getKey(), s.getValue()))
                .collect(Collectors.toList());

        SortingUtils.videoMostViews(shows, this.getSortType());

        List<String> result = shows.stream().map(Rating::getName).collect(Collectors.toList());

        return result;
    }

    private List<String> getShowsByLength() {
        ShowService showService = new ShowService();
        List<Rating> list = showService.getAllShows().stream()
                .map(s -> new Rating(s.getName(), showService.getShowLength(s)))
                .collect(Collectors.toList());

        SortingUtils.videoLongest(list, this.getSortType());

        return list.stream()
                .map(Rating::getName)
                .collect(Collectors.toList());
    }

    private List<String> getShowsByFavorite() {
        UserService userService = new UserService();
        ShowService showService = new ShowService();
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

        List<Rating> shows = map.entrySet().stream()
                .map(s -> new Rating(s.getKey(), s.getValue()))
                .collect(Collectors.toList());

        SortingUtils.videoFavorite(shows, this.getSortType());

        shows.removeIf(r -> showService.findShowByTitle(r.getName()) == null);

        return shows.stream().map(Rating::getName).collect(Collectors.toList());
    }

    private List<String> getShowsByRating() {
        ShowService showService = new ShowService();
        List<Rating> list = showService.getAllShows().stream()
                .map(s -> new Rating(s.getName(), showService.getRating(s)))
                .filter(s -> s.getScore() != 0)
                .collect(Collectors.toList());

        List<String> res = list.stream()
                .sorted(Comparator.comparingDouble(Rating::getScore))
                .map(Rating::getName)
                .collect(Collectors.toList());

        if (this.getSortType().equals(Constants.DESC)) {
            Collections.reverse(res);
        }

        return res;
    }
}
