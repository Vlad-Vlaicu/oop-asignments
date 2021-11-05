package actions.commands;

import actions.Action;

public class AddToSeenCommand extends Action {
    public AddToSeenCommand(int action_id, String action_type) {
        super(action_id, action_type);
    }

    @Override
    public String execute() {
        return null;
    }
}
