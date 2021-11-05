package actions.recommendations;

import actions.Action;
import entertainment.Genre;

public class SearchRecommendation extends Action {
    private String username;
    private Genre genre;

    public SearchRecommendation(int action_id, String action_type) {
        super(action_id, action_type);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String execute() {
        return null;
    }
}
