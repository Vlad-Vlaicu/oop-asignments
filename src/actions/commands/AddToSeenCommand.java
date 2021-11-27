package actions.commands;

import actions.Action;
import entities.User;
import services.UserService;

import java.util.Optional;

public final class AddToSeenCommand extends Action {
    private String user;
    private String title;

    public AddToSeenCommand(final int actionId, final String actionType) {
        super(actionId, actionType);
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public String execute() {
        UserService service = new UserService();
        User user = service.findUserByName(this.user);

        Optional<User> optional = Optional.ofNullable(user);
        if (optional.isEmpty()) {
            return "error -> no user found";
        }

        return service.watchVideo(title, user);
    }
}
