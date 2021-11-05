package actions;

public abstract class Action {
    private int action_id;
    private String action_type;

    public Action(int action_id, String action_type) {
        this.action_id = action_id;
        this.action_type = action_type;
    }

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public abstract String execute();
}
