package actions;

import actions.commands.AddToFavoriteCommand;
import actions.commands.AddToSeenCommand;
import actions.commands.RateCommand;
import actions.queries.ActorQuery;
import actions.queries.UserQuery;
import actions.queries.VideoQuery;
import actions.recommendations.*;
import common.Constants;
import entertainment.Genre;
import fileio.ActionInputData;
import utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class ActionFactory {
    public static List<Action> createActionList(List<ActionInputData> data) {
        List<Action> result = new ArrayList<>();
        for (var d : data) {
            Action action = createAction(d);
            result.add(action);
        }
        return result;
    }

    private static Action createAction(ActionInputData data) {
        String actionType = data.getActionType();
        return switch (actionType) {
            case Constants.COMMAND -> createCommand(data);
            case Constants.QUERY -> createQuery(data);
            case Constants.RECOMMENDATION -> createRecommendation(data);
            default -> null;
        };
    }

    private static Action createCommand(ActionInputData data) {
        String commandType = data.getType();
        return switch (commandType) {
            case Constants.FAVORITE -> createFavoriteCommand(data);
            case Constants.VIEW -> createSeenCommand(data);
            case Constants.RATING -> createRatingCommand(data);
            default -> null;
        };
    }

    private static Action createQuery(ActionInputData data) {
        String objectType = data.getObjectType();
        return switch (objectType){
            case Constants.ACTORS -> createActorQuery(data);
            case Constants.MOVIES -> createVideoQuery(data);
            case Constants.USERS -> createUserQuery(data);
            default -> null;
        };
    }

    private static Action createRecommendation(ActionInputData data) {
        String recommendationType = data.getType();
        return switch (recommendationType) {
            case Constants.STANDARD -> createStandardRecomm(data);
            case Constants.BEST_UNSEEN -> createBestUnseenRecomm(data);
            case Constants.POPULAR -> createPopularRecomm(data);
            case Constants.FAVORITE -> createFavoriteRecomm(data);
            case Constants.SEARCH -> createSearchRecomm(data);
            default -> null;
        };
    }

    private static Action createFavoriteCommand(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();
        AddToFavoriteCommand command = new AddToFavoriteCommand(id,actionType);
        command.setUser(data.getUsername());
        command.setTitle(data.getTitle());
        return command;
    }

    private static Action createSeenCommand(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();
        AddToSeenCommand command = new AddToSeenCommand(id,actionType);
        command.setUser(data.getUsername());
        command.setTitle(data.getTitle());
        return command;
    }

    private static Action createRatingCommand(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();
        RateCommand command = new RateCommand(id,actionType);
        command.setUser(data.getUsername());
        command.setTitle(data.getTitle());
        command.setGrade(data.getGrade());
        command.setCurrentSeason(data.getSeasonNumber());
        return command;
    }

    private static Action createActorQuery(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();
        List<String> keywords = data.getFilters().get(2);
        List<String> awards = data.getFilters().get(3);

        ActorQuery query = new ActorQuery(id,actionType);
        query.setObjectType(data.getObjectType());
        query.setSortType(data.getSortType());
        query.setCriteria(data.getCriteria());
        query.setLimit(data.getNumber());
        query.setKeywordsFilter(keywords);
        query.setAwardsFilter(awards);

        return query;
    }

    private static Action createVideoQuery(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();
        List<String> yearFilter = data.getFilters().get(0);
        List<String> genreFilter = data.getFilters().get(1);

        VideoQuery query = new VideoQuery(id,actionType);
        query.setObjectType(data.getObjectType());
        query.setSortType(data.getSortType());
        query.setCriteria(data.getCriteria());
        query.setLimit(data.getNumber());

        if(!yearFilter.isEmpty()){
            int year = Integer.parseInt(yearFilter.get(0));
            query.setYear(year);
        }
        if(!genreFilter.isEmpty()){
            Genre genre = Utils.stringToGenre(genreFilter.get(0));
            query.setGenre(genre);
        }
        return query;
    }

    private static Action createUserQuery(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();

        UserQuery query = new UserQuery(id, actionType);
        query.setObjectType(data.getObjectType());
        query.setSortType(data.getSortType());
        query.setCriteria(data.getCriteria());
        query.setLimit(data.getNumber());
        return query;
    }

    private static Action createStandardRecomm(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();

        StandardRecommendation recomm = new StandardRecommendation(id,actionType);
        recomm.setUsername(data.getUsername());
        return recomm;
    }

    private static Action createBestUnseenRecomm(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();

        BestUnseenRecommendation recomm = new BestUnseenRecommendation(id,actionType);
        recomm.setUsername(data.getUsername());
        return recomm;
    }

    private static Action createPopularRecomm(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();

        PopularRecommendation recomm = new PopularRecommendation(id, actionType);
        recomm.setUsername(data.getUsername());
        return recomm;
    }

    private static Action createFavoriteRecomm(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();

        FavoriteRecommendation recomm = new FavoriteRecommendation(id, actionType);
        recomm.setUsername(data.getUsername());
        return recomm;
    }

    private static Action createSearchRecomm(ActionInputData data){
        int id = data.getActionId();
        String actionType = data.getActionType();
        List<String> genres = data.getFilters().get(1);
        Genre genre = Utils.stringToGenre(genres.get(0));

        SearchRecommendation recomm = new SearchRecommendation(id,actionType);
        recomm.setUsername(data.getUsername());
        recomm.setGenre(genre);
        return recomm;
    }

}
