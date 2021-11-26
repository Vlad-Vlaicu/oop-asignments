package actions;

import actions.commands.AddToFavoriteCommand;
import actions.commands.AddToSeenCommand;
import actions.commands.RateCommand;
import actions.queries.ActorQuery;
import actions.queries.MovieQuery;
import actions.queries.ShowQuery;
import actions.queries.UserQuery;
import actions.recommendations.BestUnseenRecommendation;
import actions.recommendations.PopularRecommendation;
import actions.recommendations.StandardRecommendation;
import actions.recommendations.SearchRecommendation;
import actions.recommendations.FavoriteRecommendation;
import common.Constants;
import entertainment.Genre;
import fileio.ActionInputData;
import utils.Utils;
import java.util.ArrayList;
import java.util.List;

public final class ActionFactory {

    private ActionFactory() { }

    /** ActionFactory receives a list of data and creates the specified action classes
     * @param data is the data parsed into the action type classes
     * */
    public static List<Action> createActionList(final List<ActionInputData> data) {
        List<Action> result = new ArrayList<>();
        for (var d : data) {
            Action action = createAction(d);
            result.add(action);
        }
        return result;
    }

    private static Action createAction(final ActionInputData data) {
        String actionType = data.getActionType();
        return switch (actionType) {
            case Constants.COMMAND -> createCommand(data);
            case Constants.QUERY -> createQuery(data);
            case Constants.RECOMMENDATION -> createRecommendation(data);
            default -> null;
        };
    }

    private static Action createCommand(final ActionInputData data) {
        String commandType = data.getType();
        return switch (commandType) {
            case Constants.FAVORITE -> createFavoriteCommand(data);
            case Constants.VIEW -> createSeenCommand(data);
            case Constants.RATING -> createRatingCommand(data);
            default -> null;
        };
    }

    private static Action createQuery(final ActionInputData data) {
        String objectType = data.getObjectType();
        return switch (objectType) {
            case Constants.ACTORS -> createActorQuery(data);
            case Constants.MOVIES -> createMovieQuery(data);
            case Constants.SHOWS -> createShowQuery(data);
            case Constants.USERS -> createUserQuery(data);
            default -> null;
        };
    }

    private static Action createRecommendation(final ActionInputData data) {
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

    private static Action createFavoriteCommand(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();
        AddToFavoriteCommand command = new AddToFavoriteCommand(id, actionType);
        command.setUser(data.getUsername());
        command.setTitle(data.getTitle());
        return command;
    }

    private static Action createSeenCommand(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();
        AddToSeenCommand command = new AddToSeenCommand(id, actionType);
        command.setUser(data.getUsername());
        command.setTitle(data.getTitle());
        return command;
    }

    private static Action createRatingCommand(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();
        RateCommand command = new RateCommand(id, actionType);
        command.setUser(data.getUsername());
        command.setTitle(data.getTitle());
        command.setGrade(data.getGrade());
        command.setCurrentSeason(data.getSeasonNumber());
        return command;
    }

    private static Action createActorQuery(final ActionInputData data) {
        int id = data.getActionId();
        int keywordsIndex = 2;
        int awardsIndex = keywordsIndex + 1;
        String actionType = data.getActionType();
        List<String> keywords = data.getFilters().get(keywordsIndex);
        List<String> awards = data.getFilters().get(awardsIndex);

        ActorQuery query = new ActorQuery(id, actionType);
        query.setObjectType(data.getObjectType());
        query.setSortType(data.getSortType());
        query.setCriteria(data.getCriteria());
        query.setLimit(data.getNumber());
        query.setKeywordsFilter(keywords);
        query.setAwardsFilter(awards);

        return query;
    }

    private static Action createMovieQuery(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();
        List<String> yearFilter = data.getFilters().get(0);
        List<String> genreFilter = data.getFilters().get(1);

        MovieQuery query = new MovieQuery(id, actionType);
        query.setObjectType(data.getObjectType());
        query.setSortType(data.getSortType());
        query.setCriteria(data.getCriteria());
        query.setLimit(data.getNumber());

        if (yearFilter.get(0) != null) {
            int year = Integer.parseInt(yearFilter.get(0));
            query.setYear(year);
        }
        if (genreFilter.get(0) != null) {
            Genre genre = Utils.stringToGenre(genreFilter.get(0));
            query.setGenre(genre);
        }
        return query;
    }

    private static Action createShowQuery(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();
        List<String> yearFilter = data.getFilters().get(0);
        List<String> genreFilter = data.getFilters().get(1);

        ShowQuery query = new ShowQuery(id, actionType);
        query.setObjectType(data.getObjectType());
        query.setSortType(data.getSortType());
        query.setCriteria(data.getCriteria());
        query.setLimit(data.getNumber());

        if (yearFilter.get(0) != null) {
            int year = Integer.parseInt(yearFilter.get(0));
            query.setYear(year);
        }
        if (genreFilter.get(0) != null) {
            Genre genre = Utils.stringToGenre(genreFilter.get(0));
            query.setGenre(genre);
        }
        return query;
    }

    private static Action createUserQuery(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();

        UserQuery query = new UserQuery(id, actionType);
        query.setObjectType(data.getObjectType());
        query.setSortType(data.getSortType());
        query.setCriteria(data.getCriteria());
        query.setLimit(data.getNumber());
        return query;
    }

    private static Action createStandardRecomm(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();

        StandardRecommendation recomm = new StandardRecommendation(id, actionType);
        recomm.setUsername(data.getUsername());
        return recomm;
    }

    private static Action createBestUnseenRecomm(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();

        BestUnseenRecommendation recomm = new BestUnseenRecommendation(id, actionType);
        recomm.setUsername(data.getUsername());
        return recomm;
    }

    private static Action createPopularRecomm(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();

        PopularRecommendation recomm = new PopularRecommendation(id, actionType);
        recomm.setUsername(data.getUsername());
        return recomm;
    }

    private static Action createFavoriteRecomm(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();

        FavoriteRecommendation recomm = new FavoriteRecommendation(id, actionType);
        recomm.setUsername(data.getUsername());
        return recomm;
    }

    private static Action createSearchRecomm(final ActionInputData data) {
        int id = data.getActionId();
        String actionType = data.getActionType();
        SearchRecommendation recomm = new SearchRecommendation(id, actionType);
        Genre genre = Utils.stringToGenre(data.getGenre());
        recomm.setGenre(genre);
        recomm.setUsername(data.getUsername());

        return recomm;
    }

}
