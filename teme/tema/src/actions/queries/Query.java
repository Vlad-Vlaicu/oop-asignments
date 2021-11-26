package actions.queries;

import actions.Action;

public abstract class Query extends Action {
    private String objectType;
    private String sortType;
    private String criteria;
    private int limit;

    public Query(final int actionId, final String actionType) {
        super(actionId, actionType);
    }

    /** method returns the type of object targeted by the query
     * @return objectType is the type of the objected adressed by the query
     * */
    public String getObjectType() {
        return objectType;
    }

    /** method sets the type of object targeted by the query
     * @param objectType is the new object type
     * */
    public void setObjectType(final String objectType) {
        this.objectType = objectType;
    }

    /** method returns the type of sorting required by the query
     * @return is either "asc" or "desc"
     * */
    public String getSortType() {
        return sortType;
    }

    /** method sets the type of sorting required
     * @param sortType is either "asc" or "desc
     * */
    public void setSortType(final String sortType) {
        this.sortType = sortType;
    }

    /** method returns the criteria after which the query is guided
     * @return the criteria for the query
     * */
    public String getCriteria() {
        return criteria;
    }

    /** method sets the criteria to the query
     * @param criteria is the new criteria
     * */
    public void setCriteria(final String criteria) {
        this.criteria = criteria;
    }

    /** method returns the number of results of the query
     * @return an integer representing the maximum number of the returned
     * results of the query
     * */
    public int getLimit() {
        return limit;
    }

    /** method sets the maximum number of results of the query
     * @param limit is the integer representing the new limit
     * */
    public void setLimit(final int limit) {
        this.limit = limit;
    }
}
