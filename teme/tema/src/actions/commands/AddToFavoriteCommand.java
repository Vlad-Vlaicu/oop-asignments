package actions.commands;

import actions.Action;

public class AddToFavoriteCommand extends Action {
    private String user;
    private String title;

    public AddToFavoriteCommand(int action_id, String action_type) {
        super(action_id, action_type);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String execute() {
        return null;
    }
}
