package actions.commands;

import actions.Action;
import entities.User;
import services.UserService;

import java.util.Optional;

public final class AddToFavoriteCommand extends Action {
    private String user;
    private String title;

    public AddToFavoriteCommand(final int actionId, final String actionType) {
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

        boolean wasSeen = service.wasVideoSeen(title, user);
        if (!wasSeen) {
            return "error -> " + title + " is not seen";
        }

        boolean result = service.addVideoToFavorite(title, user);
        if (result) {
            return "success -> " + title + " was added as favourite";
        }

        return "error -> " + title + " is already in favourite list";
    }
}
