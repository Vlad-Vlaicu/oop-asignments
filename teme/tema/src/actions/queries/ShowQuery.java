package actions.queries;

import common.Constants;
import entertainment.Genre;
import entertainment.Rating;
import entities.Show;
import entities.User;
import services.ShowService;
import services.UserService;
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
            case Constants.RATING -> getShowsByRating();
            case Constants.FAVORITE -> getShowsByFavorite();
            case Constants.LONGEST -> getShowsByLength();
            case Constants.MOSTVIEWED -> getShowsByViews();
            default -> new ArrayList<>();
        };

        String sortType = this.getSortType();
        if (Constants.DESC.equals(sortType)) {
            Collections.reverse(videos);
        }

        videos = applyFilters(videos);

        if (limit != 0 && limit < videos.size()) {
            videos = videos.subList(0, limit - 1);
        }

        return Utils.createQueryResult(videos);
    }

    private List<String> applyFilters(List<String> videos) {
        ShowService showService = new ShowService();
        return videos.stream()
                .map(showService::getShowByName)
                .filter(s -> s.getGenres().contains(genre))
                .filter(s -> s.getYear() == year)
                .map(Show::getName)
                .collect(Collectors.toList());

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

        List<String> res = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (String s : res){
            Optional<Show> optionalShow = Optional.ofNullable(showService.getShowByName(s));
            if(optionalShow.isEmpty()){
                res.remove(s);
            }
        }

        return res;
    }

    private List<String> getShowsByLength() {
        ShowService showService = new ShowService();
        List<Rating> list = showService.getAllShows().stream()
                .map(s -> new Rating(s.getName(), showService.getShowLength(s)))
                .collect(Collectors.toList());

        List<String> res = list.stream()
                .sorted(Comparator.comparingDouble(Rating::getScore))
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

        List<String> res = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        for (String s : res){
            Optional<Show> optionalShow = Optional.ofNullable(showService.getShowByName(s));
            if(optionalShow.isEmpty()){
                res.remove(s);
            }
        }

        return res;
    }

    private List<String> getShowsByRating() {
        ShowService showService = new ShowService();
        List<Rating> list = showService.getAllShows().stream()
                .map(s -> new Rating(s.getName(), showService.getRating(s)))
                .collect(Collectors.toList());

        List<String> res = list.stream()
                .sorted(Comparator.comparingDouble(Rating::getScore))
                .map(Rating::getName)
                .collect(Collectors.toList());

        return res;
    }
}