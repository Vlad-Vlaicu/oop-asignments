package actions.recommendations;

import actions.Action;

public class StandardRecommendation extends Action {
    public StandardRecommendation(int action_id, String action_type) {
        super(action_id, action_type);
    }

    @Override
    public String execute() {
        return null;
    }
}
