package actions.queries;

import common.Constants;
import entertainment.Rating;
import entities.User;
import services.UserService;
import utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UserQuery extends Query{
    public UserQuery(int action_id, String action_type) {
        super(action_id, action_type);
    }

    @Override
    public String execute() {

        String criteria = this.getCriteria();
        int limit = this.getLimit();
        String sortType = this.getSortType();

        List<String> users = switch (criteria) {
            case Constants.NUM_RATINGS -> getUsersByRatings();
            default -> new ArrayList<>();
        };

        if (Constants.DESC.equals(sortType)) {
            Collections.reverse(users);
        }

        if (limit != 0 && limit < users.size()) {
            users = users.subList(0, limit - 1);
        }
        return Utils.createQueryResult(users);

    }

    private List<String> getUsersByRatings() {
        UserService userService = new UserService();
        List<String> result = userService.getAllUsers().stream()
                .map(s -> new Rating(s.getUsername(),s.getRatings().size()))
                .sorted(Comparator.comparingDouble(Rating::getScore))
                .map(Rating::getName)
                .collect(Collectors.toList());
        return result;
    }
}
