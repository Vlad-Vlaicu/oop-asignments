package actions.queries;

import common.Constants;
import entertainment.ActorsAwards;
import entertainment.Rating;
import entities.Actor;
import services.ActorService;
import utils.SortingUtils;
import utils.Utils;

import java.util.*;
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
        if(awardsFilter != null)
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
            actors = actors.subList(0, limit);
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
            if(r.getScore() != 0)
            ratings.add(r);
        }
        SortingUtils.avergActor(ratings,getSortType());
        List<String> result = ratings.stream().map(Rating::getName).collect(Collectors.toList());
        return result;
    }

    private List<String> getActorsByAwards(){
        ActorService actorService = new ActorService();
        List<Actor> actors = actorService.getAllActors();
        List<Rating> ratings = new ArrayList<>();
        for(Actor a: actors){
           boolean isEligible = actorService.doesActorHaveAllAwards(a,awardsFilter);
           if(isEligible) {
               Rating r = new Rating(a.getName(), actorService.getCountAwards(a));
               ratings.add(r);
           }
        }
        SortingUtils.awardActor(ratings,getSortType());

        return ratings.stream().map(Rating::getName).collect(Collectors.toList());
    }

    private List<String> sortActorsByName(List<String> names) {
        String sortType = this.getSortType();
        List<String> result = names.stream().sorted().collect(Collectors.toList());
        if (Constants.DESC.equals(sortType)) {
           Collections.reverse(result);
        }
        return result;
    }

    private List<String> getActorsByDescription(){
        ActorService actorService = new ActorService();
        List<Actor> actors = actorService.getAllActors();
        List<String> result = new ArrayList<>();
        for(Actor a : actors){

            /*
            boolean isEligible = true;
            String description = a.getCareer_description().toLowerCase();
            for(String s : keywordsFilter){
                if(!description.contains(s)){
                    isEligible = false;
                }
            }
            if(isEligible){
                result.add(a.getName());
            }
            */
            List<String> descriptionWords = Arrays.asList(a.getCareer_description().replaceAll("[^a-zA-Z ]", " ").toLowerCase().split("\\s+"));
            descriptionWords = descriptionWords.stream().distinct().collect(Collectors.toList());
            Collections.sort(descriptionWords);
            if(descriptionWords.containsAll(keywordsFilter)){
                result.add(a.getName());
            }
        }

        result = sortActorsByName(result);
        return result;
    }
}
