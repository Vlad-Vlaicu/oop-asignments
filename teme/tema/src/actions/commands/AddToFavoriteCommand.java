package actions.commands;

import actions.Action;

public class AddToFavoriteCommand extends Action {
    public AddToFavoriteCommand(int action_id, String action_type) {
        super(action_id, action_type);
    }

    @Override
    public String execute() {
        return null;
    }
}
