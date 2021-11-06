package actions.queries;

import actions.Action;
import entertainment.ActorsAwards;
import entertainment.Genre;

import java.util.ArrayList;
import java.util.List;

public abstract class Query extends Action {
    private String objectType;
    private String sortType;
    private String criteria;
    private int limit;

    public Query(int action_id, String action_type) {
        super(action_id, action_type);
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
