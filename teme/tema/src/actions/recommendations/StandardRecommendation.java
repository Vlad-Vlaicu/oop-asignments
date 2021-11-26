package actions.recommendations;

import actions.Action;
import entities.Movie;
import entities.Show;
import entities.User;
import services.MovieService;
import services.ShowService;
import services.UserService;

import java.util.List;
import java.util.Map;

public class StandardRecommendation extends Action {
    private String username;

    public StandardRecommendation(int action_id, String action_type) {
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
        Map<String,Integer> history = user.getHistory();

        List<Movie> movies = movieService.getAllMovies();
        List<Show> shows = showService.getAllShows();

        for(Movie m : movies){
            if(!history.containsKey(m.getName())){
                return "StandardRecommendation result: " + m.getName();
            }
        }

        for(Show s : shows){
            if(!history.containsKey(s.getName())){
                return "StandardRecommendation result: " + s.getName();
            }
        }

        return "StandardRecommendation cannot be applied!";

    }
}
