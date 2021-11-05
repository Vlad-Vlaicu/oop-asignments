package actions.recommendations;

import actions.Action;

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
        return null;
    }
}
