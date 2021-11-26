package actions;
/** Represents an action that will be made
 * based on the input
 **/
public abstract class Action {
    private int actionId;
    private String actionType;

    /** Creates an Action with the specified id and type
     * @param actionId is the id of the action
     * @param actionType is the type of the action
     * */
    public Action(final int actionId, final String actionType) {
        this.actionId = actionId;
        this.actionType = actionType;
    }

    /** Gets the Action's id
     * @return an integer representing the id of the action
     * */
    public int getActionId() {
        return actionId;
    }

    /** Sets the Action's id
    * @param actionId the new id of the action
    * */
    public void setActionId(final int actionId) {
        this.actionId = actionId;
    }

    /** Gets the Action's type
     * @return a String that represents the action type
     * */
    public String getActionType() {
        return actionType;
    }

    /** Sets the Action's type
     * @param actionType represents the new type of the action
     * */
    public void setActionType(final String actionType) {
        this.actionType = actionType;
    }

    /** An abstract method that will be implemented for each type
     * of action
     * */
    public abstract String execute();
}
