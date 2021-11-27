package actions.queries;

import common.Constants;
import entertainment.Rating;
import services.UserService;
import utils.SortingUtils;
import utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class UserQuery extends Query {
    public UserQuery(final int actionId, final String actionType) {
        super(actionId, actionType);
    }

    @Override
    public String execute() {

        String criteria = this.getCriteria();
        int limit = this.getLimit();

        List<String> users = switch (criteria) {
            case Constants.NUM_RATINGS -> getUsersByRatings();
            default -> new ArrayList<>();
        };

        if (limit != 0 && limit < users.size()) {
            users = users.subList(0, limit);
        }
        return Utils.createQueryResult(users);

    }

    private List<String> getUsersByRatings() {
        UserService userService = new UserService();
        List<Rating> result = userService.getAllUsers().stream()
                .map(s -> new Rating(s.getUsername(), s.getRatings().size()))
                .filter(rating -> rating.getScore() != 0)
                .collect(Collectors.toList());
        SortingUtils.userQuery(result, getSortType());

        return result.stream().map(Rating::getName).collect(Collectors.toList());
    }
}

