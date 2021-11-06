package actions.queries;

import entertainment.ActorsAwards;
import utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ActorQuery extends Query{
    private List<String> keywordsFilter;
    private List<ActorsAwards> awardsFilter;

    public ActorQuery(int action_id, String action_type) {
        super(action_id, action_type);
        this.keywordsFilter = new ArrayList<>();
        this.awardsFilter = new ArrayList<>();
    }

    public List<String> getKeywordsFilter() {
        return keywordsFilter;
    }

    public void setKeywordsFilter(List<String> keywordsFilter) {
        this.keywordsFilter = keywordsFilter;
    }

    public List<ActorsAwards> getAwardsFilter() {
        return awardsFilter;
    }

    public void setAwardsFilter(List<String> awardsFilter) {
        this.awardsFilter = awardsFilter.stream()
                .map(Utils::stringToAwards)
                .collect(Collectors.toList());
    }

    @Override
    public String execute() {
        return null;
    }
}
