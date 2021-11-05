package actions.queries;

import actions.Action;
import entertainment.ActorsAwards;
import entertainment.Genre;

import java.util.ArrayList;
import java.util.List;

public class Query extends Action {

    private String objectType;
    private int limit;
    private int yearFilter;
    private Genre genreFilter;
    private List<String> keywordsFilter;
    private List<ActorsAwards> awardsFilter;
    private String sortType;
    private String criteria;

    public Query(int action_id, String action_type) {
        super(action_id, action_type);
        keywordsFilter = new ArrayList<>();
        awardsFilter = new ArrayList<>();
    }

    public Query(int action_id, String action_type, QueryBuilder builder) {
        super(action_id, action_type);
        this.objectType = builder.objectType;
        this.limit = builder.limit;
        this.yearFilter = builder.yearFilter;
        this.genreFilter = builder.genreFilter;
        this.keywordsFilter = builder.keywordsFilter;
        this.awardsFilter = builder.awardsFilter;
        this.sortType = builder.sortType;
        this.criteria = builder.criteria;
    }

    public static class QueryBuilder{

        private String objectType;
        private int limit;
        private int yearFilter;
        private Genre genreFilter;
        private List<String> keywordsFilter = new ArrayList<>();
        private List<ActorsAwards> awardsFilter = new ArrayList<>();
        private String sortType;
        private String criteria;



        public QueryBuilder setObjectType(String objectType){
            this.objectType = objectType;
            return this;
        }

        public QueryBuilder setLimit(int limit){
            this.limit = limit;
            return this;
        }

        public QueryBuilder setYearFilter(int yearFilter){
            this.yearFilter = yearFilter;
            return this;
        }

        public QueryBuilder setGenreFilter(Genre genre){
            this.genreFilter = genre;
            return this;
        }

        public QueryBuilder setKeywordsFilter(List<String> keywords){
            this.keywordsFilter = keywords;
            return this;
        }

        public QueryBuilder setAwardsFilter(List<ActorsAwards> awards){
            this.awardsFilter = awards;
            return this;
        }

        public QueryBuilder setSortType(String sort){
            this.sortType = sort;
            return this;
        }

        public QueryBuilder setCriteria(String criteria){
            this.criteria = criteria;
            return this;
        }

        public Query build(int actionId,String action_type){
            return new Query(actionId, action_type, this);
        }
    }

    @Override
    public String execute() {
        //TODO: queries
        return null;
    }
}
