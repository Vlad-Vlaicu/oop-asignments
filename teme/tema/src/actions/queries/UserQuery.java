package actions.queries;

public class UserQuery extends Query{
    public UserQuery(int action_id, String action_type) {
        super(action_id, action_type);
    }

    @Override
    public String execute() {
        return null;
    }
}
