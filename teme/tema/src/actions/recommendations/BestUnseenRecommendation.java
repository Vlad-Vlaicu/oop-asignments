package actions.recommendations;

import actions.Action;
import entertainment.Rating;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;
import utils.SortingUtils;
import java.util.*;

public class BestUnseenRecommendation extends Action {
    private String username;

    public BestUnseenRecommendation(int action_id, String action_type) {
        super(action_id, action_type);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String execute() {
        MovieService movieService = new MovieService();
        ShowService showService = new ShowService();
        UserService userService = new UserService();
        User user = userService.findUserByName(username);

        List<Rating> ratings = new ArrayList<>();

        showService.getAllShows().stream()
                .map( s -> new Rating(s.getName(), showService.getRating(s),s.getId()))
                .forEach(ratings::add);

        movieService.getAllMovies().stream()
                .map(s -> new Rating(s.getName(), movieService.getRating(s),s.getId()))
                .forEach(ratings::add);

        SortingUtils.bestRecommSort(ratings);

        Map<String,Integer> history = user.getHistory();

        for( Rating r : ratings){
            if(!history.containsKey(r.getName())){
                return "BestRatedUnseenRecommendation result: " + r.getName();
            }
        }
        return "BestRatedUnseenRecommendation cannot be applied!";
    }
}
