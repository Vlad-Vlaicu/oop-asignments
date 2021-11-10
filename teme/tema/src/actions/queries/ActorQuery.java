package actions.queries;

import common.Constants;
import entertainment.ActorsAwards;
import entertainment.Rating;
import entities.Actor;
import services.ActorService;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ActorQuery extends Query{
    private List<String> keywordsFilter;
    private List<ActorsAwards> awardsFilter;

    public ActorQuery(int action_id, String action_type) {
        super(action_id, action_type);
        this.keywordsFilter = new ArrayList<>();
        this.awardsFilter = new ArrayList<>();
    }

    public List<String> getKeywordsFilter() {
        return keywordsFilter;
    }

    public void setKeywordsFilter(List<String> keywordsFilter) {
        this.keywordsFilter = keywordsFilter;
    }

    public List<ActorsAwards> getAwardsFilter() {
        return awardsFilter;
    }

    public void setAwardsFilter(List<String> awardsFilter) {
        this.awardsFilter = awardsFilter.stream()
                .map(Utils::stringToAwards)
                .collect(Collectors.toList());
    }

    @Override
    public String execute() {
        String criteria = this.getCriteria();
        int limit = this.getLimit();
        List<String> actors = switch (criteria) {
            case Constants.AVERAGE -> getActorsByAverage();
            case Constants.AWARDS -> getActorsByAwards();
            case Constants.FILTER_DESCRIPTIONS -> getActorsByDescription();
            default -> new ArrayList<>();
        };

        if(limit != 0 && limit < actors.size()){
            actors = actors.subList(0, limit-1);
        }

        return Utils.createQueryResult(actors);
    }

    private List<String> getActorsByAverage(){
        ActorService actorService = new ActorService();
        List<Actor> actors = actorService.getAllActors();
        List<Rating> ratings = new ArrayList<>();
        for(Actor a : actors){
            double actorRating = actorService.getRating(a);
            Rating r = new Rating(a.getName(),actorRating);
            ratings.add(r);
        }

        return sortActorsByRatings(ratings);
    }

    private List<String> getActorsByAwards(){
        ActorService actorService = new ActorService();
        List<Actor> actors = actorService.getAllActors();
        List<Rating> ratings = new ArrayList<>();
        for(Actor a: actors){
            int relevantAwards = actorService.getActorPrestigeStrict(a, awardsFilter);
            if(relevantAwards != 0){
                Rating rating = new Rating(a.getName(),relevantAwards);
                ratings.add(rating);
            }
        }
        return sortActorsByRatings(ratings);
    }

    private List<String> sortActorsByRatings(List<Rating> ratings) {
        String sortType = this.getSortType();
        if (Constants.DESC.equals(sortType)) {
            ratings.sort(Comparator.comparing(Rating::getScore).reversed());
        } else {
            ratings.sort(Comparator.comparing(Rating::getScore));
        }

        return ratings.stream()
                .map(Rating::getName)
                .collect(Collectors.toList());
    }

    private List<String> sortActorsByName(List<String> names) {
        String sortType = this.getSortType();
        names = names.stream().sorted().collect(Collectors.toList());
        if (Constants.DESC.equals(sortType)) {
           Collections.reverse(names);
        }
        return names;
    }

    private List<String> getActorsByDescription(){
        ActorService actorService = new ActorService();
        List<Actor> actors = actorService.getAllActors();
        List<String> names = actors.stream()
                .map(Actor::getName)
                .collect(Collectors.toList());
        names = sortActorsByName(names);
        return names;
    }
}
