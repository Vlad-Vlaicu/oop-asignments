package actions.queries;

import common.Constants;
import entertainment.Genre;
import entertainment.Rating;
import entities.Movie;
import entities.Show;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;
import utils.SortingUtils;
import utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

public class ShowQuery extends Query {
    private int year;
    private Genre genre;

    public ShowQuery(int action_id, String action_type) {
        super(action_id, action_type);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setGenre(String genre) {
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

    private List<String> applyFilters(List<String> videos) {
        ShowService showService = new ShowService();
        List<String> result = new ArrayList<>();
        for(String v : videos){
            Optional<Show> showBox = Optional.ofNullable(showService.getShowByName(v));
            if(showBox.isPresent()){
                Show show = showBox.get();
                boolean isEligible = true;
                if(year != 0){
                    if(show.getYear() != year){
                        isEligible = false;
                    }
                }
                if(genre != null){
                    if(!show.getGenres().contains(genre)){
                        isEligible = false;
                    }
                }
                if(isEligible){
                    result.add(show.getName());
                }
            }
        }
        return result;
    }

    private List<String> getShowsByViews() {
        UserService userService = new UserService();
        ShowService showService = new ShowService();
        HashMap<String, Integer> map = new HashMap<>();
        userService.getAllUsers().stream()
                .map(User::getHistory)
                .flatMap(m -> m.entrySet().stream())
                .forEach(s -> {
                    if (map.containsKey(s.getKey())) {
                        int views = map.get(s.getKey());
                        views += s.getValue();
                        map.replace(s.getKey(), views);
                    } else map.put(s.getKey(), s.getValue());

                });

        List<Rating> shows = map.entrySet().stream()
                .map(s -> new Rating(s.getKey(),s.getValue()))
                .collect(Collectors.toList());

        SortingUtils.videoMostViews(shows,this.getSortType());

        List<String> result = shows.stream().map(Rating::getName).collect(Collectors.toList());

        return result;
    }

    private List<String> getShowsByLength() {
        ShowService showService = new ShowService();
        List<Rating> list = showService.getAllShows().stream()
                .map(s -> new Rating(s.getName(), showService.getShowLength(s)))
                .collect(Collectors.toList());

        SortingUtils.videoLongest(list, this.getSortType());

        List<String> res = list.stream()
                .map(Rating::getName)
                .collect(Collectors.toList());


        return res;
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
                    } else map.put(s, 1);

                });

        List<Rating> shows = map.entrySet().stream()
                .map(s -> new Rating(s.getKey(),s.getValue()))
                .collect(Collectors.toList());

        SortingUtils.videoFavorite(shows,this.getSortType());

        List<String> result = shows.stream().map(Rating::getName).collect(Collectors.toList());

        return result;
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

        if(this.getSortType().equals(Constants.DESC)){
            Collections.reverse(res);
        }

        return res;
    }
}